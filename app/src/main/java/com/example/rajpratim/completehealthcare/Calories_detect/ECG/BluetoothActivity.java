package com.example.rajpratim.completehealthcare.Calories_detect.ECG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajpratim.completehealthcare.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class BluetoothActivity extends Activity  implements View.OnClickListener{
    private static final String TAG = "Health-BlueActivities";

    //All COntrols Here

    private TextView mTxtReceive;
    private Button mBtnDisconnect;

    private boolean mIsBluetoothConnected = false;
    private BluetoothDevice mDevice;
    private static TextView myView;
    private ProgressDialog progressDialog;
    private BluetoothServerSocket mmServerSocket = null;
    private String defaultUUID = "00001101-0000-1000-8000-00805F9B34FB";
    private AcceptThread server;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private TextView textView;
    public Button start;
    private GraphView graphView;
    static LinkedBlockingQueue<Double> bluetoothQueueForSaving = new LinkedBlockingQueue<Double>();
    private Button uploadButton;
    /**
     * Magic number used in the bluetooth enabling request.
     */
    private final int REQ = 111;
    private final static String PREFIX = "RPB";

    private NotificationCenter mNotificationCenter;
    private SQLiteDatabase mydb ;
    private static final String MESSAGE_RECEIVED_INTENT = "MESSAGE_RECEIVED";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        //Upload_JSON up=new Upload_JSON("Hello World");
        //up.execute();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        graphView = (GraphView) findViewById(R.id.graph_view);

        ActivityHelper.initialize(this);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mDevice = b.getParcelable(Calculate.DEVICE_EXTRA);
        //mDeviceUUID = UUID.fromString(b.getString(Calculate.DEVICE_UUID));
        start =(Button)findViewById(R.id.start_server);
        uploadButton = (Button)findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jstr=DavaJsonsaving();
                if(jstr!=null) {
                    Upload_JSON up = new Upload_JSON(jstr);
                    up.execute();
                }
                else
                {
                    Upload_JSON up = new Upload_JSON("Hello World");
                    up.execute();
                }
            }
        });

        mNotificationCenter = new NotificationCenter();

        if(mBluetoothAdapter == null) {
            Log.e(TAG, "No Bluetooth Adapter available. Exiting...");
            this.finish();
        }

        registerReceiver(mNotificationCenter, new IntentFilter(MESSAGE_RECEIVED_INTENT));

        setHandlers();

        Log.d(TAG, "Ready");
    }


    class Upload_JSON extends AsyncTask<Void,Void,Void>
    {
        private String json;
        Httpcall call;
        String url="http://testprojectme.netne.net/app_connection.php";
        ArrayList<NameValuePair> par;
        ProgressDialog  pr;
        String r;
        public Upload_JSON(String str)
        {
            json=str;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            call=new Httpcall();
            par=new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("json",json));
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("Inside","Background");
            String response12 = call.makeServiceCall(url,Httpcall.POST,par);
            r=response12;
            Log.e("Received",response12+"Eee");
            Log.e("HHd","Hello");
            Log.e("Received123123123",response12+"Eee");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("JSON", "Async completed");
            Toast.makeText(getApplicationContext(),"hello"+r,Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }


    /*@Override
    public void onBackPressed() {
        onPause();
    }
*/
    @Override
    public void onPause() {
 //       server.cancel();

        restoreBTDeviceName();

        super.onPause();
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

    @Override
    public void onClick(View v) {
        Button btn =(Button)v;
        if(btn.getId()==R.id.start_server)
        {
            if (!mBluetoothAdapter.getName().startsWith(PREFIX))
                mBluetoothAdapter.setName(PREFIX + mBluetoothAdapter.getName());


            if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
                requestBTDiscoverable();


            server = new AcceptThread();
            server.execute();

            btn.setEnabled(false);
            ((Button) this.findViewById(R.id.btn_disconnect)).setEnabled(true);
        }
        else if (btn.getId() == R.id.btn_disconnect){
            server.cancel();
            DataSaving dataSaving = new DataSaving();
            btn.setEnabled(false);
            ((Button) this.findViewById(R.id.start_server)).setEnabled(true);

            Log.i(TAG, "SizeofQueue " + String.valueOf(bluetoothQueueForSaving.size()));
           // dataSaving.savingData("EcgFile",bluetoothQueueForSaving);
            restoreBTDeviceName();
        }

    }
    private void restoreBTDeviceName() {
        if (mBluetoothAdapter.getName().startsWith(PREFIX))
            mBluetoothAdapter.setName(mBluetoothAdapter.getName().substring(PREFIX.length()));
    }
    public void requestBTDiscoverable() {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);

        startActivityForResult(i, REQ);

        int result = 0;

        this.onActivityResult(REQ, result, i);
        Log.i(TAG, "Bluetooth discoverability enabled");
    }
    private Vibrator getVibrator() {
        return (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }


    private void showInformation(String message, long duration) {
       /* final Dialog mDialog = new Dialog(this);
        TextView txt = new TextView(this);
        txt.setText(message);
        mDialog.setContentView(txt);
        mDialog.setTitle("Information");
        mDialog.show();

        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                mDialog.dismiss();
            }
        }, duration); // Close dialog after delay
        */
    }
    public void setHandlers() {
        Button start_server = (Button) this.findViewById(R.id.start_server);
        start_server.setOnClickListener(this);

        Button stop_server = (Button) this.findViewById(R.id.btn_disconnect);
        stop_server.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mNotificationCenter);
        super.onDestroy();
    }


    class AcceptThread extends AsyncTask<Void,Void,Void>
    {
        private final String ACCEPT_TAG = AcceptThread.class.getName();
        private final BluetoothServerSocket mServerSocket;
        private String finalStr;

        public void cancel() {
            try {
                mServerSocket.close();
            } catch (IOException e) {
            }
        }

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(ACCEPT_TAG,
                        UUID.fromString(defaultUUID));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mServerSocket = tmp;
        }

        @Override
        protected Void doInBackground(Void... params) {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    Log.i(ACCEPT_TAG, "Listening for a connection...");

                    socket = mServerSocket.accept();
                    Log.i(ACCEPT_TAG, "Connected to " + socket.getRemoteDevice().getName());

                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    try {
                        // Read the incoming string.
                        Log.i(ACCEPT_TAG, "incomming String ");

                        InputStream inputStream;
                        inputStream = socket.getInputStream();

                        DataInputStream din = new DataInputStream(inputStream);

                        finalStr  = din.readUTF() ;
                        bluetoothQueueForSaving.add(Double.parseDouble(finalStr));

                        Log.e("String" ,finalStr);
                        publishGraph(finalStr);

                        // textView.setText(finalStr+" ");
                        Intent i = new Intent(MESSAGE_RECEIVED_INTENT);     //strInput
                        i.putExtra("Message", String.format("%sn From: %s",finalStr, socket.getRemoteDevice().getName()));

                        getBaseContext().sendBroadcast(i);
                    } catch (IOException e) {
                        Log.e(ACCEPT_TAG, "Error obtaining InputStream from socket");
                        e.printStackTrace();
                    }
                   /* try {
                        mServerSocket.close();
                    } catch (IOException e) {
                    }*/
                    //break;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    public void publishGraph(final String fstr){

        Log.v(TAG, "reporting back from the Bluetooth Data");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Double data = Double.parseDouble(fstr);
                graphView.plotProcedure(data);
            }
        });

    }


    public static void showTextDisplay(String str)
    {
        myView.setText(str);
    }

    class NotificationCenter extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_RECEIVED_INTENT)) {
                showInformation(intent.getExtras().getString("Message"), 5000);
                Toast.makeText(BluetoothActivity.this,"Got Message", Toast.LENGTH_SHORT).show();
                getVibrator().vibrate(1000);
            }
        }

    }
public String DavaJsonsaving()
    {
    JSONObject obj = new JSONObject();
        try {
            obj.put("Beats",bluetoothQueueForSaving);
            Log.e(TAG,obj.toString());
            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

public class DataSaving{
    File file;
    public void savingData(String savepath, LinkedBlockingQueue queue){
        FileOutputStream  outputStream = null;

        file = new File(Environment.getExternalStorageDirectory(), "MyECG");
        try {
              //outputStream = new FileOutputStream(file);
            outputStream = openFileOutput(savepath,Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (!queue.isEmpty()){
            try{
                Double d = (Double) queue.poll();
                if(d==null) return;
                outputStream.write(d.toString().getBytes());
            } catch (IOException e) {
                System.out.println(e+"\nError writing to file!");
                break;
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e+"\nError closing file");
        }
        Log.e(TAG, "Ecg file saved");

    }
}

}