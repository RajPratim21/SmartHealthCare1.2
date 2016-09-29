package com.example.rajpratim.completehealthcare.Calories_detect;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajpratim.completehealthcare.R;

import java.util.concurrent.LinkedBlockingQueue;

public class ActivityTracker extends AppCompatActivity implements SensorEventListener {

    private float lastX, lastY, lastZ;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float deltaXMax = 0;
    private float deltaYMax = 0,deltaMax =0,delta=0;
    private float deltaZMax = 0;
    public float TimeStart,TimeEnd,T0,T1 ;
    private float deltaX = 0;
    private float deltaY = 0,weight;
    private float Kinetic_Energy = (float) 0.0;
    private float deltaZ = 0;
    private Button Start,Stop,weight_button;
    private float vibrateThreshold = 0;
    public int st =1;
    public long startTime;
    public long currentTime;
    public int flag = 2;
    private TextView   max,KE;
    private EditText weightGEt;
    public float valnrm,Velocity=0;
    public Vibrator v;
    public int fid = 0,tflagx =0,tflagy = 0,tflagz=0;
    private StatisticsView statisticsView;
    private LinkedBlockingQueue<Float> AccDataQueue = new LinkedBlockingQueue<Float>();
    DatabaseHandler db = new DatabaseHandler(this);
     @Override
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         KE = (TextView)findViewById(R.id.kinetic_energy);
         max = (TextView) findViewById(R.id.max);
         statisticsView = (StatisticsView)findViewById(R.id.stat_view);
         Start = (Button)findViewById(R.id.Start_Acc);
         Stop = (Button)findViewById(R.id.Stop_Acc);
         weightGEt = (EditText)findViewById(R.id.editText);
         weight_button = (Button)findViewById(R.id.submit_weight);
         Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag =1;
                TimeStart = System.currentTimeMillis();
            }
        });
         Stop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 flag =0;
                 TimeEnd = System.currentTimeMillis();
                 String val = String.valueOf(Kinetic_Energy*0.000239);
                 KE.setText( val.substring(0,7)+" Kcal");
             }
         });
         weight_button.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 weight = Float.parseFloat(weightGEt.getText().toString());
             }
         });
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            // fai! we dont have an accelerometer!
        }

        initializeViews();
                //vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

    }
    public void initializeViews() {



    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
         // clean current values
        displayCleanValues();
        // display the current x,y,z accelerometer values
        displayCurrentValues();
        // display the max x,y,z accelerometer values
        displayMaxValues();

        // get the change of the x,y,z values of the accelerometer
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        // if the change is below 2, it is just plain noise
        if (lastX < 3.0)
            lastX = 0;
        if (lastY < 3.0)
            lastY = 0;
        if (lastZ < 3.0)
            lastZ = 0;

        // set the last know values of x,y,z
        lastX = event.values[0];
        lastY = event.values[1];
        lastZ = event.values[2];

        if (lastZ >=9&&lastY<=1&&lastX<=1)
        {   if(st == 1)
            {
                startTime = System.currentTimeMillis();
            }
            st =2;
                currentTime = System.currentTimeMillis();
            Log.e("Received",currentTime-startTime+"ms");
             v.vibrate(50);
            if(currentTime-startTime>200)
            {
                Toast.makeText(ActivityTracker.this,"Person didn't waked up", Toast.LENGTH_SHORT).show();
                startTime = currentTime;

            }
        }
        else{
            st =1;
        }
        vibrate();

    }

    // if the change in the accelerometer value is big enough, then vibrate!
    // our threshold is MaxValue/2
    public void vibrate() {
        if ((deltaX > vibrateThreshold) || (deltaY > vibrateThreshold) || (deltaZ > vibrateThreshold)) {
            v.vibrate(50);
        }
    }

    public void displayCleanValues() {
          }

    // display the current x,y,z accelerometer values
    public void displayCurrentValues() {

        fid++;
              if(flag == 1)
            {
                Log.e("KINETIC ENERGY",Kinetic_Energy+"j");
               valnrm =  lastX*lastX+ lastY*lastY + lastZ*lastZ;
                valnrm = (float) (Math.sqrt(valnrm) - 9.7);
                Log.e("Calculated",valnrm+"ms");
                 Velocity= (float) (valnrm*0.02);
                Kinetic_Energy += (float) (0.5*weight*Velocity*Velocity);
                publishGraph((float) (valnrm));

                    //db.addData(new AceralationData(fid,valnrm));
            }
    }

    // display the max x,y,z accelerometer values
    public void displayMaxValues() {
        delta = deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ;
        delta = (float) (Math.sqrt(delta)-9.7);

        if (delta > deltaMax) {
            deltaMax = delta;
            max.setText(Float.toString(deltaMax));
        }
    }

    public void publishGraph(final float fstr){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                float data =  fstr;
                statisticsView.plotProcedure(data);
            }
        });

    }

}
