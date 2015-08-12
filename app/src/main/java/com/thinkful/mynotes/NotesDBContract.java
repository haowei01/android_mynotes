package com.thinkful.mynotes;

import android.provider.BaseColumns;

/**
 * Created by haowei on 8/5/15.
 */
public class NotesDBContract {

    private NotesDBContract() {}

    public static final String DATABASE_NAME = "notesdb";

    public static final int DATABASE_VERSION = 1;

    /* Inner class that defines the note table */
    public static abstract class Note implements BaseColumns {
        public static final String COLUMN_NAME_ID = "rowid";
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_NOTE_TEXT = "note_text";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_NOTE_DATE = "note_date";
    }

    /* Inner class that defines the tag table */
    public static abstract class Tag implements BaseColumns {
        public static final String TABLE_NAME = "tag";
        public static final String COLUMN_NAME_TAG_NAME = "tag_name";
    }

    /* Inner class that defines the note_tag table*/
    public static abstract class NoteTag implements BaseColumns {
        public static final String TABLE_NAME = "note_tag";
        public static final String COLUMN_NAME_NOTE_ID = "note_id";
        public static final String COLUMN_NAME_TAG_ID = "tag_id";
    }

    public static final String SQL_CREATE_NOTE = String.format(
            "CREATE TABLE %s ( %s TEXT, %s TEXT, %s DATETIME)",
            Note.TABLE_NAME, Note.COLUMN_NAME_NOTE_TEXT,
            Note.COLUMN_NAME_STATUS,
            Note.COLUMN_NAME_NOTE_DATE
    );

    public static final String SQL_CREATE_TAG = String.format(
            "CREATE TABLE %s ( %s TEXT)",
            Tag.TABLE_NAME,
            Tag.COLUMN_NAME_TAG_NAME
    );

    public static final String SQL_CREATE_NOTE_TAG = String.format(
            "CREATE TABLE %s ( %s INT, %s INT)",
            NoteTag.TABLE_NAME,
            NoteTag.COLUMN_NAME_NOTE_ID,
            NoteTag.COLUMN_NAME_TAG_ID
    );

    public static final String SQL_DELETE_NOTE = String.format(
            "DELETE TABLE %s", Note.TABLE_NAME
    );

    public static final String SQL_DELETE_TAG = String.format(
            "DELETE TABLE %s", Tag.TABLE_NAME
    );

    public static final String SQL_DELETE_NOTE_TAG = String.format(
            "DELETE TABLE %s", NoteTag.TABLE_NAME
    );
}
