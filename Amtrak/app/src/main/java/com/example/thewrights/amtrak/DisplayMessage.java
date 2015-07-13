package com.example.thewrights.amtrak;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DisplayMessage extends ActionBarActivity {

    SharedPreferences sharedPrefs;
    int hour;
    int minute;
    int tripLength;
    String arrivalTime;
    final int dayInMinutes = 1440;
    int arrivalHour;
    String displayHour;
    String displayMinute;
    TextView timeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        timeText = (TextView)findViewById(R.id.txtTime);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        hour = sharedPrefs.getInt("hour", 0);
        minute = sharedPrefs.getInt("minute", 0);
        tripLength = sharedPrefs.getInt("tripTime", 0);
        arrivalTime = getArrivalTime();
        timeText.setText(arrivalTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_message, menu);
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

    public String getArrivalTime()
    {
        int timeInMinutes = getMinutes(hour) + minute;
        int arriveInMinutes = timeInMinutes + tripLength;
        arrivalHour = getHours(arriveInMinutes);
        int arrivalMinute = arriveInMinutes % 60;
        if(arrivalHour > 23)
        {
            //RedEye flight typically leaves after 9 pm and arrives before 5 am
            if(arrivalHour < 30 && timeInMinutes > 1260)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("This train has a Red Eye arrival time").setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }).setTitle("Red Eye").setIcon(R.mipmap.redeye).create();
                alert.show();
            }
            arrivalHour = arrivalHour - 24;
        }
        if(arrivalHour < 10)
        {
            displayHour = "0" + Integer.toString(arrivalHour);
        }
        else
        {
            displayHour = Integer.toString(arrivalHour);
        }

        if(arrivalMinute < 10)
        {
            displayMinute = "0" + Integer.toString(arrivalMinute);
        }
        else
        {
            displayMinute = Integer.toString(arrivalMinute);
        }

        String arrival = displayHour + ":" + displayMinute;
        return arrival;
    }

    public int getMinutes(int hours)
    {
        int minutes = hours * 60;
        return minutes;
    }

    public int getHours(int minutes)
    {
        int hours = minutes/60;
        return hours;
    }
}
