package com.myapplicationdev.android.p04_revisionnotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etNotes;
    Button btnInsert, btnShowList;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNotes = findViewById(R.id.editTextNote);
        btnInsert = findViewById(R.id.buttonInsertNote);
        btnShowList = findViewById(R.id.buttonShowList);

        myDB = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = findViewById(R.id.radioGroupStars);
                int selectedButtonId = rg.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedButtonId);
                RadioButton rb1 = findViewById(R.id.radio1);
                String notes = etNotes.getText().toString().trim();

                if(etNotes == null){
                    Toast.makeText(MainActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                }

                ArrayList<String>data = myDB.getNoteContent();
                boolean repetition = false;
                for (int i = 0; i < data.size(); i++){
                    if(data.get(1).equals(notes)){
                        repetition = true;

                    }
                }
                if (repetition == false){
                    boolean inserted = myDB.insertNote(notes, Integer.parseInt(rb.getText().toString()));
                    if(inserted == true){
                        Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You have already entered this in your notes", Toast.LENGTH_SHORT).show();
                }

                etNotes.setText("");
                rb1.setChecked(true);


            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
