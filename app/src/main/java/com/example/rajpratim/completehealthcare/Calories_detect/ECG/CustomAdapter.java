package com.example.rajpratim.completehealthcare.Calories_detect.ECG;

/**
 * Created by RajPratim on 5/28/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajpratim.completehealthcare.R;

public class CustomAdapter extends ArrayAdapter {
    Context context;
    Model[] modelItems;


    public CustomAdapter(Context context, Model[] modelArray) {
        super(context, R.layout.row, modelArray);
        this.context = context;
        this.modelItems = modelArray;
    }

    public View getView(int i, View ConvertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
        ConvertView  = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView)ConvertView.findViewById(R.id.textView1);
        CheckBox checkBox1 = (CheckBox)ConvertView.findViewById(R.id.checkBox1);
        CheckBox checkBox2 = (CheckBox)ConvertView.findViewById(R.id.checkBox2);
        CheckBox checkBox3 = (CheckBox)ConvertView.findViewById(R.id.checkBox3);
        CheckBox checkBox4 = (CheckBox)ConvertView.findViewById(R.id.checkBox4);

        name.setText(modelItems[i].getNameHead());
        checkBox1.setText(this.modelItems[i].getName1());
        checkBox2.setText(this.modelItems[i].getName2());
        checkBox3.setText(this.modelItems[i].getName3());
        checkBox4.setText(this.modelItems[i].getName4());


        checkBox1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                if (checkBox.isChecked()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                modelItems[0].weight =0.0;
                if (CustomAdapter.this.modelItems[0].c2 != null) {
                    CustomAdapter.this.modelItems[0].c2.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[0].c3 != null) {
                    CustomAdapter.this.modelItems[0].c3.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[0].c4 != null) {
                    CustomAdapter.this.modelItems[0].c4.setChecked(false);
                }
                Toast.makeText(CustomAdapter.this.getContext(), Double.toString(CustomAdapter.this.modelItems[0].weight), Toast.LENGTH_SHORT).show();
            }

        });
        checkBox2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                if (checkBox.isChecked()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                modelItems[1].weight =0.33;
                if (CustomAdapter.this.modelItems[1].c1 != null) {
                    CustomAdapter.this.modelItems[1].c1.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[1].c3 != null) {
                    CustomAdapter.this.modelItems[1].c3.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[1].c4 != null) {
                    CustomAdapter.this.modelItems[1].c4.setChecked(false);
                }
                Toast.makeText(CustomAdapter.this.getContext(), Double.toString(CustomAdapter.this.modelItems[1].weight), Toast.LENGTH_SHORT).show();
            }

        });
        checkBox3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                if (checkBox.isChecked()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                modelItems[2].weight =0.66;
                if (CustomAdapter.this.modelItems[2].c2 != null) {
                    CustomAdapter.this.modelItems[2].c2.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[2].c1 != null) {
                    CustomAdapter.this.modelItems[2].c1.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[2].c4 != null) {
                    CustomAdapter.this.modelItems[2].c4.setChecked(false);
                }
                Toast.makeText(CustomAdapter.this.getContext(), Double.toString(CustomAdapter.this.modelItems[2].weight), Toast.LENGTH_SHORT).show();
            }

        });

        checkBox4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                if (checkBox.isChecked()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                modelItems[3].weight =1.0;
                if (CustomAdapter.this.modelItems[3].c2 != null) {
                    CustomAdapter.this.modelItems[3].c2.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[3].c3 != null) {
                    CustomAdapter.this.modelItems[3].c3.setChecked(false);
                }
                if (CustomAdapter.this.modelItems[3].c1 != null) {
                    CustomAdapter.this.modelItems[3].c1.setChecked(false);
                }
                Toast.makeText(CustomAdapter.this.getContext(), Double.toString(CustomAdapter.this.modelItems[3].weight), Toast.LENGTH_SHORT).show();
            }

        });
        return ConvertView;
    }


}