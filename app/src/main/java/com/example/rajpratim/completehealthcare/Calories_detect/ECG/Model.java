package com.example.rajpratim.completehealthcare.Calories_detect.ECG;

/**
 * Created by RajPratim on 5/28/2016.
 */

import android.content.Context;
import android.widget.CheckBox;

import java.io.Serializable;

public class Model implements Serializable {
    CheckBox c1;
    CheckBox c2;
    CheckBox c3;
    CheckBox c4;
    Context context;
    String name1;
    String name2;
    String name3;
    String name4;
    String nameHead;
    int value1;
    int value2;
    int value3;
    int value4;
    double weight;
    double weight1;
    double weight2;
    double weight3;
    double weight4;

    public Model(Context context, String str, String str2, String str3, String str4, String str5) {
        this.weight1 = 0.0;
        this.weight2 = 0.33;
        this.weight3 = 0.66;
        this.weight4 = 1.0;
        this.nameHead = str;
        this.name1 = str2;
        this.value1 = 0;
        this.name2 = str3;
        this.value2 = 0;
        this.name3 = str4;
        this.value3 = 0;
        this.name4 = str5;
        this.value4 = 0;
    }

    public String getName1() {
        return this.name1;
    }

    public String getName2() {
        return this.name2;
    }

    public String getName3() {
        return this.name3;
    }

    public String getName4() {
        return this.name4;
    }

    public String getNameHead() {
        return this.nameHead;
    }


}
