package com.myapplicationdev.android.p04_revisionnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

	//TODO Define the Database properties
	private static final String DATABASE_NAME = "note.db";
	private static final int DATABASE_VERSION = 1;

	//creating tasks table
	private static final String TABLE_NOTE = "note";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_CONTENT = "content";
	private static final String COLUMN_STARS = "stars";


	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableSql = "CREATE TABLE " + TABLE_NOTE + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_CONTENT + " TEXT, "
				+ COLUMN_STARS + " TEXT )";
		db.execSQL(createTableSql);
		Log.i("info", "created tables");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
		onCreate(db);
	}

	public boolean insertNote(String noteContent, int stars) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_CONTENT, noteContent);
		cv.put(COLUMN_STARS, stars);
		long results = db.insert(TABLE_NOTE, null, cv);
		db.close();

		if(results == -1){
			return false;
		}else{
			return true;
		}
	}

	public ArrayList<Note> getAllNotes() {
		ArrayList<Note> tasks = new ArrayList<Note>();
		String selectQuery = "SELECT " + COLUMN_ID + ", "
				+ COLUMN_CONTENT + ", "
				+ COLUMN_STARS + " FROM " + TABLE_NOTE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()){
			do{
				int id = cursor.getInt(0);
				String content = cursor.getString(1);
				String stars = cursor.getString(2);
				Note note = new Note(id, content, Integer.parseInt(stars));
				tasks.add(note);

			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return  tasks;
	}

    public ArrayList<String> getNoteContent() {
        //TODO return records in Strings

		// Create an ArrayList that holds String objects
        ArrayList<String> notes = new ArrayList<String>();
        // Select all the notes' content
        String selectQuery = "SELECT * FROM " + TABLE_NOTE;

        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);
        // moveToFirst() moves to first row
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row and returns true;
            // moveToNext() returns false when no more next row to move to
            do {
				notes.add(cursor.getString(0));
				notes.add(cursor.getString(1));


			} while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return notes;
    }
}
