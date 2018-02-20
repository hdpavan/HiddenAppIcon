package com.deepshikha.hideappicon;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_hide;
    private static final ComponentName LAUNCHER_COMPONENT_NAME = new ComponentName(
            "com.deepshikha.hideappicon", "com.deepshikha.hideappicon.Launcher");

    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fn_permission();
       // listener();
        fn_hideicon();
        //scheduleTask();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                //forceClose();
                exitApplication();
            }
        }, 6000);


    }


    private void forceClose(){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

   private void exitApplication(){
       if(android.os.Build.VERSION.SDK_INT >= 21)
       {
           finishAndRemoveTask();
       }
       else
       {
           finish();
       }
    }


    private void init() {
        btn_hide = (Button) findViewById(R.id.btn_hide);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Alert");
        progressDialog.setMessage("Please wait");


        if (isLauncherIconVisible()) {
            //Log.d("isLauncherIconVisible","init: Not Hide "+isLauncherIconVisible());
            btn_hide.setText("Hide");
        } else {
           // Log.d("isLauncherIconVisible","init: Hidden"+isLauncherIconVisible());
            btn_hide.setText("Unhide");
        }


    }

    private void listener() {
        btn_hide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hide:

                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (isLauncherIconVisible()) {
                            btn_hide.setText("Hide");
                        } else {
                            btn_hide.setText("Unhide");
                        }
                    }
                }, 10000);


                if (boolean_permission) {

                    if (isLauncherIconVisible()) {
                        fn_hideicon();
                    } else {
                        fn_unhide();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
                }
                break;

        }

    }

    private boolean isLauncherIconVisible() {
        int enabledSetting = getPackageManager().getComponentEnabledSetting(LAUNCHER_COMPONENT_NAME);
        Log.d("isLauncherIconVisible","enabledSetting: "+enabledSetting);
        Log.d("isLauncherIconVisible","PackageManager.COMPONENT_ENABLED_STATE_DISABLED:"+PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        //is component not disabled
        return enabledSetting != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    private void fn_hideicon() {

        getPackageManager().setComponentEnabledSetting(LAUNCHER_COMPONENT_NAME,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);


//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Important!");
//        builder.setMessage("To launch the app again, dial phone number 1234567890");
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//
////                getPackageManager().setComponentEnabledSetting(LAUNCHER_COMPONENT_NAME,
////                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
////                        PackageManager.DONT_KILL_APP);
//
//                getPackageManager().setComponentEnabledSetting(LAUNCHER_COMPONENT_NAME,
//                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                        PackageManager.DONT_KILL_APP);
//
//               // android.os.Process.killProcess(android.os.Process.myPid());
//
//
//            }
//        });
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
//        builder.show();
    }

    private void fn_unhide() {
       // PackageManager p = getPackageManager();
        //ComponentName componentName = new ComponentName(this, com.deepshikha.hideappicon.MainActivity.class);
        getPackageManager().setComponentEnabledSetting(LAUNCHER_COMPONENT_NAME,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.PROCESS_OUTGOING_CALLS))) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.PROCESS_OUTGOING_CALLS},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.PROCESS_OUTGOING_CALLS))) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private void scheduleTask(){
        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

// Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 30);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);


    }

}


