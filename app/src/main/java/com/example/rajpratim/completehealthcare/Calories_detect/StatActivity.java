package com.example.rajpratim.completehealthcare.Calories_detect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rajpratim.completehealthcare.R;

import java.util.concurrent.LinkedBlockingQueue;

public class StatActivity extends AppCompatActivity {

    private LinkedBlockingQueue AcQueue = new LinkedBlockingQueue();
    private StatisticsView statisticsView;
    private float TimeStart,TimeEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        statisticsView = (StatisticsView)findViewById(R.id.stat_view);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        AcQueue = b.getParcelable("DATA_QUEUE");
       // TimeStart = b.getParcelable("TIME_START");
       // TimeEnd = b.getParcelable("TIME_END");
        AcQueue.add((float) 1.34);
        Log.e("Length",AcQueue.size()+"ms");
        /*        for(int i =0;i<AcQueue.size();i++)
        {
            publishGraph(AcQueue.peek());
        }
  */
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
