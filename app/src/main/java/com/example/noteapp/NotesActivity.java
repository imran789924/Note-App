package com.example.noteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class NotesActivity extends AppCompatActivity {

    EditText editText;
    int notePosition;

    public void DONE(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        notePosition = intent.getIntExtra("note", -1);

        if(notePosition > -1) {
            editText.setText(MainActivity.notesList.get(notePosition));
        }else{
            int size = MainActivity.notesList.size();
            MainActivity.notesList.add("");
            notePosition = size;
            editText.setText("");
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notesList.set(notePosition, s.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();

                HashSet<String> set = new HashSet<>(MainActivity.notesList);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);
                sharedPreferences.edit().putStringSet("Notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
