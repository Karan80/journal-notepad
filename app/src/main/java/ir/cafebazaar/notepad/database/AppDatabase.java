package ir.cafebazaar.notepad.database;

import java.util.Date;
import java.util.Random;

import ir.cafebazaar.notepad.models.Folder;
import ir.cafebazaar.notepad.models.Note;

/**
 * Created by MohMah on 8/17/2016.
 */
public class AppDatabase{
	public static final String NAME = "AppDatabase";
	public static final int VERSION = 1;

	public static class Utils{
		public static void createSomeFolders(int count){
			for (int i = 0; i < count; i++){
				Folder folder = new Folder();
				folder.setCreatedAt(new Date());
				folder.setId(i);
				folder.setName("Folder " + (i + 1));
				FoldersDAO.save(folder);
			}
		}

		public static void createSomeNotes(int count){
			for (int i = 0; i < count; i++){
				Note note = new Note();
				String ithNote = "Note " + (i + 1);
				String noteBody = "";
				Random random = new Random();
				int r = random.nextInt(20);
				for (int j = 0; j < r; j++){
					noteBody += "Some text in this line\n";
				}

				note.setBody(noteBody.trim());
				note.setCreatedAt(new Date());
				note.setId(i);
				note.setTitle(ithNote + "'s Title");
				NotesDAO.save(note);
			}
		}

		public static void deleteAllFolders(){
//			SQLite.delete().from(Folder.class).execute();
			//TODO delete all folders
		}

		public static void deleteAllNotes(){
//			SQLite.delete().from(Note.class).execute();
			//TODO delete all notes
		}
	}
}
