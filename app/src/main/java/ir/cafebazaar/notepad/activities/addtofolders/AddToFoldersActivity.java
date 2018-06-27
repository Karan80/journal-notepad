package ir.cafebazaar.notepad.activities.addtofolders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.events.NoteFoldersUpdatedEvent;
import org.greenrobot.eventbus.EventBus;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

/**
 * Created by MohMah on 8/21/2016.
 */
@IntentBuilder
public class AddToFoldersActivity extends AppCompatActivity{
	private static final String TAG = "AddToFoldersActivity";

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
	Adapter adapter;
	@Extra Integer noteId;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_folders);
		AddToFoldersActivityIntentBuilder.inject(getIntent(), this);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		mToolbar.setTitle("Add to folders");
		mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				onBackPressed();
			}
		});
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(llm);
		adapter = new Adapter(noteId);
		mRecyclerView.setAdapter(adapter);
		adapter.loadFromDatabase();
	}

	@Override protected void onStart(){
		super.onStart();
		adapter.registerEventBus();
	}

	@Override protected void onStop(){
		super.onStop();
		adapter.unregisterEventBus();
		EventBus.getDefault().post(new NoteFoldersUpdatedEvent(noteId));
	}
}
