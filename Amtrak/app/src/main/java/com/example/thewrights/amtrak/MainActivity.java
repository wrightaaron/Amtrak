package com.example.thewrights.amtrak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    EditText hour;
    EditText minute;
    EditText tripLength;
    int hours;
    int minutes;
    int tripTimeInMinutes;
    SharedPreferences sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hour = (EditText)findViewById(R.id.editTxtHour);
        minute = (EditText)findViewById(R.id.edTxtMinute);
        tripLength = (EditText)findViewById(R.id.edTxtTripLength);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void getArrival(View view)
    {
        hours = Integer.parseInt(hour.getText().toString());
        minutes = Integer.parseInt(minute.getText().toString());
        tripTimeInMinutes = Integer.parseInt(tripLength.getText().toString());
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putInt("hour", hours);
        editor.putInt("minute", minutes);
        editor.putInt("tripTime", tripTimeInMinutes);
        editor.commit();
        Intent intent = new Intent(this, DisplayMessage.class);
        startActivity(intent);
    }
}
