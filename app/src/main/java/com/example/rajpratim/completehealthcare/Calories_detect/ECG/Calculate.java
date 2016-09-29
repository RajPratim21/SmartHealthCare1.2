package com.example.rajpratim.completehealthcare.Calories_detect.ECG;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajpratim.completehealthcare.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.UUID;

public class Calculate extends AppCompatActivity {

    Double[] Mat2 = new Double[4];
    Double max;
    private ListView listView;
    private Button bluetoothbtn;
    private Button scan;
    private Button Connect;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();
    private BluetoothAdapter mBluetoothAdapter;
    private static final int ENABLE_BT_REQUEST_CODE = 1;
    private ProgressBar pbar;
    private int mBufferSize = 50000; //Default
    public static final String DEVICE_EXTRA = " com.example.rajpratim.healthcare112.SOCKET";
    public static final String DEVICE_UUID = " com.example.rajpratim.healthcare112.uuid";
    private static final String DEVICE_LIST = " com.example.rajpratim.healthcare112.devicelist";
    private static final String DEVICE_LIST_SELECTED = " com.example.rajpratim.healthcare112.devicelistselected";
    public static final String BUFFER_SIZE = " com.example.rajpratim.healthcare112.buffersize";
    private static final String TAG = "BlueTest5-Homescreen";

    int flag =0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard SPP UUID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        this.Mat2[0] = 0.6;
        this.Mat2[1] = 0.2;
        this.Mat2[2] = 0.7;
        this.Mat2[3] = 0.4;
        ArrayList arrayList = (ArrayList) getIntent().getSerializableExtra("ARRAYLIST");
        ArrayList arrayList2 = new ArrayList();
        //Bluetooth Start
        pbar = (ProgressBar) findViewById(R.id.progressBar);
        pbar.setVisibility(View.INVISIBLE);
        listView = (ListView) findViewById(R.id.listView);
        scan = (Button) findViewById(R.id.btn_Scan);
        scan.setVisibility(View.INVISIBLE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Connect = (Button)findViewById(R.id.btn_connect);
        Connect.setEnabled(false);
        bluetoothbtn = (Button) findViewById(R.id.btnBluetooth);
        bluetoothbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    // A dialog will appear requesting user permission to enable Bluetooth
                    Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBluetoothIntent, ENABLE_BT_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "Your device has already been enabled." +
                                    "\n" + "Scanning for remote Bluetooth devices...",
                            Toast.LENGTH_SHORT).show();
                    // To discover remote Bluetooth devices
                }
                scan.setVisibility(View.VISIBLE);

            }
        });


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbar.setVisibility(View.VISIBLE);
                mBluetoothAdapter.startDiscovery();

                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

                registerReceiver(mReceiver, filter);
            }
        });
          //  final MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.list_item , mDeviceList);
          //  listView.setAdapter(adapter);
          /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    manky(mDeviceList.get(position));
                    adapter.setSelectedIndex(position);
                    Connect.setEnabled(true);
                }
            });
            */
        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothDevice device = ((MyAdapter)(listView.getAdapter())).getSelectedItem();
                if(device!=null) {
                    Toast.makeText(Calculate.this, "Device is Not Null", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                    intent.putExtra(DEVICE_EXTRA, device);
                    intent.putExtra(DEVICE_UUID, mDeviceUUID.toString());
                    intent.putExtra(BUFFER_SIZE, mBufferSize);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Calculate.this, "Device Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //-----------------------------------------------------------------------\\

/*        if (arrayList != null) {
            Toast.makeText(this, "Array Mot Null", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 4; i++) {
                if (((Model) arrayList.get(i)).weight < this.Mat2[i].doubleValue()) {

                    arrayList2.add(Double.valueOf(((Model) arrayList.get(i)).weight));
                } else {
                    arrayList2.add(this.Mat2[i]);
                }
            }
            Collections.sort(arrayList2);
            this.max = (Double) arrayList2.get(3);
            Toast.makeText(this, Double.toString(this.max.doubleValue()), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Null Array", Toast.LENGTH_SHORT).show();
        }
*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void manky(BluetoothDevice s)
    {
     Toast.makeText(this, s.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device);
                //mDeviceList.add(device.getName() + "\n" + device.getAddress());

                Log.i("BT", device.getName() + "\n" + device.getAddress());
              final MyAdapter adapter = new MyAdapter(context, R.layout.list_item , mDeviceList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        manky(mDeviceList.get(position));
                        adapter.setSelectedIndex(position);
                        Connect.setEnabled(true);
                    }
                });
                if (mDeviceList.size() > 0) {
                    pbar.setVisibility(View.GONE);
                }
            }
        }
    };

    public void call()
    {
        Intent intent = new Intent(Calculate.this, BluetoothActivity.class);
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
//------------------------------------------------------------------------------------------\\

    private class MyAdapter extends ArrayAdapter<BluetoothDevice> {
        private int selectedIndex;
        private Context context;
        private int selectedColor = Color.parseColor("#abcdef");
        private ArrayList<BluetoothDevice> myList;

        public MyAdapter(Context ctx, int resource,  ArrayList<BluetoothDevice> objects) {
            super(ctx, resource , objects);
            context = ctx;
            myList = objects;
            selectedIndex = -1;
        }

        public void setSelectedIndex(int position) {
            selectedIndex = position;

            Toast.makeText(getApplicationContext(), "Index Selected =  "+Integer.toString(position) , Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }

        public BluetoothDevice getSelectedItem() {
            Toast.makeText(getApplicationContext(), "Returning Selected =  "+Integer.toString(selectedIndex) , Toast.LENGTH_SHORT).show();
            return myList.get(selectedIndex);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public BluetoothDevice getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView tv;
        }

        public void replaceItems(ArrayList<BluetoothDevice> list) {
            myList = list;
            notifyDataSetChanged();
        }

        public ArrayList<BluetoothDevice> getEntireList() {
            return myList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if (convertView == null) {
                vi = LayoutInflater.from(context).inflate(R.layout.list_item, null);
                holder = new ViewHolder();

                holder.tv = (TextView) vi.findViewById(R.id.lstContent);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            if (selectedIndex != -1 && position == selectedIndex) {
                holder.tv.setBackgroundColor(selectedColor);
            } else {
                holder.tv.setBackgroundColor(Color.WHITE);
            }
            BluetoothDevice device = myList.get(position);
            holder.tv.setText(device.getName() + "\n   " + device.getAddress());

            return vi;
        }

    }



}


