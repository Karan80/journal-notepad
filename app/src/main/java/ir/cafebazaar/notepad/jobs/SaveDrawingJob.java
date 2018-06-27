package ir.cafebazaar.notepad.jobs;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.github.gcacace.signaturepad.views.SignaturePad;

import org.greenrobot.eventbus.EventBus;

import ir.cafebazaar.notepad.database.NotesDAO;
import ir.cafebazaar.notepad.events.NoteEditedEvent;
import ir.cafebazaar.notepad.models.Note;
import ir.cafebazaar.notepad.utils.Utils;

/**
 * Created by MohMah on 8/21/2016.
 */
public class SaveDrawingJob extends Job{
	private static final String TAG = "SaveDrawingJob";
	private final SignaturePad signaturePad;
	private final int noteId;

	public SaveDrawingJob(SignaturePad signaturePad, int noteId){
		super(new Params(1));
		this.signaturePad = signaturePad;
		this.noteId = noteId;
	}

	@Override public void onAdded(){
		Log.e(TAG, "onAdded() called with: " + "");
	}

	@Override public void onRun() throws Throwable{
		Log.e(TAG, "onRun() called with: " + "");

		Bitmap bitmapTrimmed = signaturePad.getTransparentSignatureBitmap(true);
		byte[] byteBlobTrimmed = Utils.getBytes(bitmapTrimmed);
		Note note = NotesDAO.getNote(noteId);
		note.setDrawingTrimmed(byteBlobTrimmed);
		NotesDAO.save(note);

		Bitmap bitmap = signaturePad.getTransparentSignatureBitmap(false);
		byte[] byteBlob = Utils.getBytes(bitmap);
		note.setDrawing(byteBlob);
		NotesDAO.save(note);

		EventBus.getDefault().post(new NoteEditedEvent(note.getId()));
	}

	@Override protected void onCancel(int cancelReason, @Nullable Throwable throwable){
		Log.e(TAG, "onCancel() called with: " + "cancelReason = [" + cancelReason + "], throwable = [" + throwable + "]");
	}

	@Override protected RetryConstraint shouldReRunOnThrowable(
			@NonNull Throwable throwable, int runCount, int maxRunCount
	){
		Log.e(TAG, "shouldReRunOnThrowable() called with: "
				+ "throwable = ["
				+ throwable
				+ "], runCount = ["
				+ runCount
				+ "], maxRunCount = ["
				+ maxRunCount
				+ "]");
		return RetryConstraint.CANCEL;
	}
}
