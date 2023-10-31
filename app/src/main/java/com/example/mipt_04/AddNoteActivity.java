package com.example.mipt_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private EditText editTextTitle;
    private Button btnSave;
    private String PREFS_LOCATION;
    int nextNoteID;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_addStart));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextNote = findViewById(R.id.editTextNote);
        btnSave = findViewById(R.id.btnSave);

        PREFS_LOCATION = String.valueOf(R.string.PREFS_LOCATION);
        sharedPreferences = getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }
    public void returnMain(View view){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_addReturnMainStart));
        finish();
    }
    public void saveNote(){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_addSaveNoteStart));
        String noteName = editTextTitle.getText().toString();
        String noteContent = editTextNote.getText().toString();

        if (noteName.isEmpty() || noteContent.isEmpty()) {
            Log.w(getResources().getString(R.string.app_name), getResources().getString(R.string.log_inputError));
            Toast.makeText(this, R.string.toast_error, Toast.LENGTH_SHORT).show();
        }
        else {
            int noteCount = sharedPreferences.getInt("noteCount", 0);
            nextNoteID = noteCount + 1;
            String noteString = noteName + "\n" + noteContent;

            editor.putString("note_" + nextNoteID, noteString);
            editor.putInt("noteCount", nextNoteID);

            editor.apply();


            Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.toast_success));
            Toast.makeText(this, R.string.toast_success, Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}