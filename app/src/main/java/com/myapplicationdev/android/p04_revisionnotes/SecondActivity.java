package com.myapplicationdev.android.p04_revisionnotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
	ListView lvNotes;
	ArrayList<Note> alNotes;
	RevisionNotesArrayAdapter caNotes;
	DBHelper myDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		lvNotes = findViewById(R.id.lv);
		alNotes = new ArrayList<>();
		myDB = new DBHelper(this);
		alNotes = myDB.getAllNotes();
		myDB.close();
		caNotes = new RevisionNotesArrayAdapter(SecondActivity.this, R.layout.row, alNotes);
		lvNotes.setAdapter(caNotes);
		caNotes.notifyDataSetChanged();

	}


}
