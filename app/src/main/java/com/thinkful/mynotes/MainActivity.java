package com.thinkful.mynotes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoteListItemAdapter mAdapter;
    private Button mButton;
    private EditText mEditText;
    private int foregroundColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesDBHelper.getInstance(this).getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setColor();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NoteListItemAdapter(this, mRecyclerView, foregroundColor);
        mRecyclerView.setAdapter(mAdapter);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEditText.getText().toString();
                NoteListItem noteListItem = new NoteListItem(s);
                NoteDAO noteDAO = new NoteDAO(MainActivity.this);
                NoteListItem noteInserted = noteDAO.insertAndReturn(noteListItem);
                mAdapter.addItem(noteInserted);
                mEditText.setText("");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == 1){
            if (data.hasExtra("Note")){
                NoteListItem note = (NoteListItem)data.getSerializableExtra("Note");
                Toast.makeText(this, note.getText(),
                        Toast.LENGTH_LONG).show();
                mAdapter.addItem(note);
            }
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            setColor();
            mAdapter = new NoteListItemAdapter(MainActivity.this, mRecyclerView, foregroundColor);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void openColorDialog(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_color_title)
                .setMessage(R.string.setting_color_message)
                .setView(input)
                .setPositiveButton(R.string.positive_button_label, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String s = input.getText().toString();
                        Toast toast = Toast.makeText(getApplicationContext(), "Input Color is "+s, Toast.LENGTH_LONG);
                        toast.show();
                        editor.putString("NOTE_COLOR", s);
                        editor.commit();
                        setColor();
                    }
                }).setNegativeButton(R.string.negative_button_label, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).show();
    }

    public void openEditSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        this.startActivityForResult(intent, 2);
    }

    public void setColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String color = prefs.getString("NOTE_COLOR", "W");
        if(color.toUpperCase().contains("G")){
            mRecyclerView.setBackgroundColor(Color.GREEN);
        }else if(color.toUpperCase().contains("R")){
            mRecyclerView.setBackgroundColor(Color.RED);
        }else{
            mRecyclerView.setBackgroundColor(Color.WHITE);
        }

        color = prefs.getString("NOTE_FORE_COLOR", "Y");
        if (color.toUpperCase().contains("P")){
            foregroundColor = Color.MAGENTA;
        } else if (color.toUpperCase().contains("G")){
            foregroundColor = Color.GRAY;
        } else if (color.toUpperCase().contains("B")){
            foregroundColor = Color.BLACK;
        } else {
            foregroundColor = Color.YELLOW;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // openColorDialog();
            openEditSetting();
            Toast toast = Toast.makeText(getApplicationContext(), "Click Setting Toast", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
