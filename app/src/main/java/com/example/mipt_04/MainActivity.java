package com.example.mipt_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView lvNoteList;
    private ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    public List<String> notesList;
    private String PREFS_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_mainStart));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PREFS_LOCATION = String.valueOf(R.string.PREFS_LOCATION);
        sharedPreferences = getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        lvNoteList = findViewById(R.id.lvNoteList);
        readNotes();


        lvNoteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_mainListenerStart));

                Intent editNoteIntent = new Intent(MainActivity.this, EditNoteActivity.class);
                editNoteIntent.putExtra("notePosition", position);
                startActivity(editNoteIntent);
                return true;
            }
        });

    }
    @Override
    public void onResume(){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_mainResumed));
        super.onResume();
        adapter.notifyDataSetChanged();
        readNotes();
    }

    public void readNotes(){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_mainReadNotesStart));
        notesList = new ArrayList<>();
        int noteCount = sharedPreferences.getInt("noteCount", 0);
        for(int i = 1; i <= noteCount; i++){
            String noteKey = "note_" + i;
            String note = sharedPreferences.getString(noteKey, "");
            notesList.add(note);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        lvNoteList.setAdapter(adapter);
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_mainReadNotesEnd));
    }


    public void addNote(View view) {
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_mainAddNoteStart));
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }



}