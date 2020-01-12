package com.example.noteapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notesList = new ArrayList<>();
    ListView listView;
    static ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        try {
            Intent intent = new Intent(this, NotesActivity.class);
            intent.putExtra("note", -1);
            startActivity(intent);
            return true;
        }catch (Exception e) {
            return false;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences =  this.getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);

        notesList.clear();

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("Notes", null);

        if(set != null){
            notesList = new ArrayList<>(set);
        } else {
            notesList.add("Sample Note");
        }



        if (notesList.size() == 0) {
            notesList.add("Sample Note");
        }

        listView = findViewById(R.id.listView);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, notesList);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.single_line_list_item, notesList);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                intent.putExtra("note", position);
                startActivity(intent);
            }

        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.alert_dark_frame)
                        .setTitle("DELETE!")
                        .setMessage("Do you want to delete the item?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notesList.remove(position);
                                arrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<>(MainActivity.notesList);

                                sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);
                                sharedPreferences.edit().putStringSet("Notes", set).apply();
                            }
                        })
                        .show();

                return true;

            }
        });



    }




}
