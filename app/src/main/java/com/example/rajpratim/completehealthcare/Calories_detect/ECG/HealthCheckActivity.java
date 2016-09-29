package com.example.rajpratim.completehealthcare.Calories_detect.ECG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.rajpratim.completehealthcare.R;

import java.util.ArrayList;

public class HealthCheckActivity extends AppCompatActivity {

    Context context;
    ListView lv;
    ArrayList<Model> modelArray = new ArrayList<Model>();
    double[] myItems;
    Button btn;
    Model[] modelItems = new Model[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.lv = (ListView) findViewById(R.id.listView);
        this.btn = (Button) findViewById(R.id.button);
        double[] dArr = new double[4];

        modelItems[0] = new Model(this, "Does anyone in patient's family has cardiatic disease", "No one has", "Some of them has minute cardiatic disease", "Some of them has severe Cardiatic disease", "More than 3 people have severe Cardiatic disease");
        modelArray.add(modelItems[0]);
        modelItems[1] = new Model(this, "Does the Patient Smoke", "Not at all", "Occasionaly", "2 cigaretes a day", "More than 2");
        modelArray.add(modelItems[1]);
        modelItems[2] = new Model(this, "Does the Patient take alcohol", "Not at all", "Occasionaly", "weekly", "daily");
        modelArray.add(modelItems[2]);
        modelItems[3] = new Model(this, "Does the patient has Depression", "Not at all", "Some times take stress", "suffering from hypertension", " under severe Depression");
        modelArray.add(modelItems[3]);

        ListAdapter customAdapter = new CustomAdapter(this, this.modelItems);
        this.lv.setAdapter(customAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HealthCheckActivity.this, Calculate.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ARRAYLIST", HealthCheckActivity.this.modelArray);
                intent.putExtras(bundle);
                HealthCheckActivity.this.startActivity(intent);
            }
        });


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

}
