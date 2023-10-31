package com.example.mipt_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private EditText editTextTitle;
    private Button btnSave;
    private Button btnDelete;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String PREFS_LOCATION;
    private int notePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editStart));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextNote = findViewById(R.id.editTextNote);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        PREFS_LOCATION = String.valueOf(R.string.PREFS_LOCATION);
        sharedPreferences = getSharedPreferences(PREFS_LOCATION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        notePosition = getIntent().getIntExtra("notePosition", -1);
        if(notePosition == -1){
            Log.w(getResources().getString(R.string.app_name), getResources().getString(R.string.toast_invalid));
            Toast.makeText(this, R.string.toast_invalid, Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            notePosition = notePosition + 1;
            String note = getNote(notePosition);
            editTextTitle.setText(getNoteTitle(note));
            editTextNote.setText(getNoteContent(note));
        }

    }

    private String getNote(int position){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editGetNoteStart));
        String noteKey = "note_" + position;

        return sharedPreferences.getString(noteKey, "");
    }
    private String getNoteTitle(String note){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editGetNoteTitleStart));
        return note.split("\n")[0];
    }
    private String getNoteContent(String note){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editGetNoteContentStart));
        return note.split("\n")[1];
    }
    public void returnMain(View view){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editReturnMainStart));
        finish();
    }
    public void saveUpdatedNote(View view){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editSaveUpdatedNoteStart));
        String noteName = editTextTitle.getText().toString();
        String noteContent = editTextNote.getText().toString();

        if (noteName.isEmpty() || noteContent.isEmpty()) {
            Log.w(getResources().getString(R.string.app_name), getResources().getString(R.string.log_inputError));
            Toast.makeText(this, R.string.toast_error, Toast.LENGTH_SHORT).show();
        }
        else {
            String noteKey = "note_" + notePosition;
            editor.putString(noteKey, noteName + "\n" + noteContent);

            editor.apply();
            Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editSaveUpdatedNoteEnd));
            Toast.makeText(this, R.string.toast_success, Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    public void deleteNote(View view){
        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editDeleteNoteStart));
        String noteKey = "note_" + notePosition;

        int noteCount = sharedPreferences.getInt("noteCount", 0);
        editor.remove(noteKey);

        for (int i = notePosition + 1; i <= noteCount; i++){
            String currentNoteKey = "note_" + i;
            String nextNoteKey = "note_" + (i - 1);

            String noteContent = sharedPreferences.getString(currentNoteKey, "");
            editor.putString(nextNoteKey, noteContent);
            editor.remove(currentNoteKey);
        }

        editor.putInt("noteCount", noteCount - 1);
        editor.apply();

        Log.i(getResources().getString(R.string.app_name), getResources().getString(R.string.log_editDeleteNoteEnd));
        Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show();
        finish();
    }

}