package com.thinkful.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by haowei on 8/9/15.
 */
public class NoteDAO {
    private Context context;

    public NoteDAO(Context context){
        this.context = context;
    }

    public void save(NoteListItem note){
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT, note.getText());
        values.put(NotesDBContract.Note.COLUMN_NAME_STATUS, note.getStatus());
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_DATE, (note.getDate().getTimeInMillis()/1000));

        db.insert(NotesDBContract.Note.TABLE_NAME, null, values);
        Log.i("Database insert",  "text: " + note.getText());
    }

    public void update(NoteListItem note){
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT, note.getText());
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_DATE, (Calendar.getInstance().getTimeInMillis()/1000));
        String selection = NotesDBContract.Note.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(note.getId()) };

        db.update(NotesDBContract.Note.TABLE_NAME, values, selection, selectionArgs);
        Log.i("Database update", note.getId() + ", text: " + note.getText());
    }

    public void delete(NoteListItem note){
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = NotesDBContract.Note.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(note.getId()) };

        db.delete(NotesDBContract.Note.TABLE_NAME, selection, selectionArgs);
        Log.i("Database delete", note.getId() + ", text: " + note.getText());
    }

    public List<NoteListItem> list(){
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                NotesDBContract.Note.COLUMN_NAME_ID,
                NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT,
                NotesDBContract.Note.COLUMN_NAME_STATUS,
                NotesDBContract.Note.COLUMN_NAME_NOTE_DATE
        };
        String sortOrder = NotesDBContract.Note.COLUMN_NAME_NOTE_DATE + " DESC";
        Cursor c = db.query(
                NotesDBContract.Note.TABLE_NAME,        // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );
        List<NoteListItem> notes = new ArrayList<NoteListItem>();

        while(c.moveToNext()){
            long id = c.getLong(c.getColumnIndex(NotesDBContract.Note.COLUMN_NAME_ID));
            String text = c.getString(
                    c.getColumnIndex(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT));
            String status = c.getString(c.getColumnIndex(
                    NotesDBContract.Note.COLUMN_NAME_STATUS));
            Calendar date = new GregorianCalendar();
            date.setTimeInMillis(c.getLong(c.getColumnIndex(
                    NotesDBContract.Note.COLUMN_NAME_NOTE_DATE)) * 1000);
            notes.add(new NoteListItem(id, text, status, date));
            Log.i("Database Stored", Long.toString(id) + ", text:" + text);
        }
        return notes;
    }
}
