package com.example.thewrights.amtrak;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    boolean isValid;
    SharedPreferences sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hour = (EditText) findViewById(R.id.editTxtHour);
        minute = (EditText)findViewById(R.id.edTxtMinute);
        tripLength = (EditText)findViewById(R.id.edTxtTripLength);
        isValid = false;
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
        String hourText = hour.getText().toString();
        String minuteText = minute.getText().toString();
        String tripTime = tripLength.getText().toString();

        hours = Integer.parseInt(hourText);
        minutes = Integer.parseInt(minuteText);
        tripTimeInMinutes = Integer.parseInt(tripTime);

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("hour", hours);
        editor.putInt("minute", minutes);
        editor.putInt("tripTime", tripTimeInMinutes);
        editor.commit();
        Intent intent = new Intent(this, DisplayMessage.class);
        startActivity(intent);

    }

    public void validateData(View view)
    {

        String hourText = " ";
        String minuteText = " ";
        String tripTime = " ";
        if(hour.getText().toString().isEmpty())
        {
            hour.requestFocus();
            hour.setError("This field is required!");
        }
        else if(minute.getText().toString().isEmpty())
        {
            minute.requestFocus();
            minute.setError("This field is required!");
        }
        else if(tripLength.getText().toString().isEmpty())
        {
            tripLength.requestFocus();
            tripLength.setError("This field is required!");
        }
        else
        {
            isValid = true;
            hourText = hour.getText().toString();
            minuteText = minute.getText().toString();
            tripTime = tripLength.getText().toString();
            hours = Integer.parseInt(hourText);
            minutes = Integer.parseInt(minuteText);
            tripTimeInMinutes = Integer.parseInt(tripTime);
            /*
            hours = Integer.parseInt(hour.getText().toString());
            minutes = Integer.parseInt(minute.getText().toString());
            tripTimeInMinutes = Integer.parseInt(tripLength.getText().toString());*/
        }


        if(hours <= 23)
        {
            if(minutes <= 59)
            {
                if(tripTimeInMinutes <=1500)
                {
                    if(isValid)
                    {
                        getArrival(view);
                    }
                }
                else
                {
                    tripLength.requestFocus();
                    tripLength.setError("The trip time must be 1500 minutes or less");
                }
            }
            else
            {
                minute.requestFocus();
                minute.setError("The minutes portion of the time must be less than 60");
            }
        }
        else
        {
            hour.requestFocus();
            hour.setError("The hours portion of the time must be less than 24");
        }
    }

    public void showMessage(String msg, String title)
    {
        AlertDialog.Builder alertMsg = new AlertDialog.Builder(this);
        alertMsg.setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }).setIcon(R.mipmap.amtraklogo).setTitle(title).create();
        alertMsg.show();
    }
}
