package com.thinkful.mynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SettingActivity extends ActionBarActivity {

    private RadioGroup mRadioGroup;
    private Button mButton;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        mRadioGroup = (RadioGroup)findViewById(R.id.background_color_group);
        mRadioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Setting Group Clicked", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        mButton = (Button)findViewById(R.id.save_setting);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.commit();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Radio Button Clicked", Toast.LENGTH_LONG);
        toast.show();
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.background_color_red:
                if (checked)
                    editor.putString("NOTE_COLOR", "R");
                    break;
            case R.id.background_color_green:
                if (checked)
                    editor.putString("NOTE_COLOR", "G");
                    break;
            case R.id.background_color_white:
                if (checked)
                    editor.putString("NOTE_COLOR", "W");
                    break;
        }
    }

    public void onForegroundRadioButtonClicked(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Foreground Radio Button Clicked", Toast.LENGTH_LONG);
        toast.show();
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.foreground_color_yello:
                if (checked)
                    editor.putString("NOTE_FORE_COLOR", "Y");
                break;
            case R.id.foreground_color_purple:
                if (checked)
                    editor.putString("NOTE_FORE_COLOR", "P");
                break;
            case R.id.foreground_color_grey:
                if (checked)
                    editor.putString("NOTE_FORE_COLOR", "G");
                break;
            case R.id.foreground_color_black:
                if (checked)
                    editor.putString("NOTE_FORE_COLOR", "B");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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
