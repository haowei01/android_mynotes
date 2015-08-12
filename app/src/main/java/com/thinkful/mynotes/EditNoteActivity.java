package com.thinkful.mynotes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditNoteActivity extends ActionBarActivity {

    private EditText noteEditText;
    private NoteListItem note;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = getIntent();
        note = (NoteListItem)intent.getSerializableExtra("Note");
        noteEditText = (EditText)findViewById(R.id.note_text_edit);
        noteEditText.setText(note.getText());

        saveButton = (Button)findViewById(R.id.note_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String note_text = noteEditText.getText().toString();
                note.setText(note_text);
//                Log.i("Database EditNoteAct", Long.toString(note.getId()) + ", text" + note.getText());

                NoteDAO dao = new NoteDAO(EditNoteActivity.this);
                dao.update(note);

                Intent intent = new Intent();
                intent.putExtra("Note", note);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
