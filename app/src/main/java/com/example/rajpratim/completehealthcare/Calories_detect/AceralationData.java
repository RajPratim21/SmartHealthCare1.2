package com.example.rajpratim.completehealthcare.Calories_detect;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by RajPratim on 7/7/2016.
 */
public class AceralationData {

private int _id;
private float AcData;

   public AceralationData(){
       
   }
   public AceralationData(int id, float acData){

       this._id = id;
       this.AcData = acData;
   }

    public int get_id(){
        return this._id;
    }
    public void set_id(int id ){
        this._id = id;
    }
    public void set_data(float acData ){
        this.AcData = acData;
    }

    public float getAcData()
    {
        return AcData;
    }
}
