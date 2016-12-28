package com.example.tushar.ips_rev1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

public class live_view extends AppCompatActivity {

    private RelativeLayout plot_area;
    private ImageView location_dot;
    private TextView XYPointValue;
    private boolean isStopped;
    final private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_view);
        StrictMode.enableDefaults();

        //Get from previous activity name room
        String selected_room = "001";
        //Get photo from SD Card
        File f = new File(Environment.getExternalStorageDirectory().getPath() + "/IPS/room layouts/" + selected_room + ".png");
        ImageView floor_layout = (ImageView) findViewById(R.id.display_image);
        Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
        floor_layout.setImageBitmap(bmp);

        //Find relative layout to plot in
        plot_area = (RelativeLayout) findViewById(R.id.plot_area);
        location_dot = new ImageView(this);
        location_dot.setImageResource(R.drawable.location);
        location_dot.setLayoutParams(new RelativeLayout.LayoutParams(10, 10));


        XYPointValue = new TextView(this);

        //Automatic Repeat in few seconds - add timer function
        // Execute some code after 2 seconds have passed
        handler.postDelayed(sendData, 2000);

        //Refresh Button
        FloatingActionButton refresh = (FloatingActionButton) findViewById(R.id.live_view_refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plot();
            }
        });


    }

    private final Runnable sendData = new Runnable(){
        public void run(){
            try {
                plot();
                if(!isStopped){
                    handler.postDelayed(this, 2000);
                }
            }
            catch (Exception e) {
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            isStopped = true;
            if(this.handler != null)
            {
                this.handler.removeCallbacks(sendData);
            }
            this.finish();
        }catch (Exception ex){}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            isStopped = true;
            if(this.handler != null)
            {
                this.handler.removeCallbacks(sendData);
            }
            this.finish();
        }catch (Exception ex){}
    }

    // to plot function add date time parameters to find position at that time
    public void plot()
    {
        if(!isStopped){
            String[] SourceDeviceAddress = new String[]{"40:88:05:67:6E:81","40:88:05:28:64:FD","40:88:05:67:6C:5F"};
            String DiscoveredDeviceAddress = "FF:FF:00:00:84:84";
            //Get Data from database server - getData()
            //Returns Rssi_Set object with 3 values Values_RSSI_A, Values_RSSI_B, Values_RSSI_C
            RSSI_Set rssi_set = new RSSI_Set(this.getApplicationContext(), SourceDeviceAddress, DiscoveredDeviceAddress);

            //Manipulate Data for result
            //Call rssi - distance - trilateration should return location object x & y
            //pass getdata object with values Values_RSSI_A, Values_RSSI_B, Values_RSSI_C
            if(rssi_set.Values_RSSI_A == 0 && rssi_set.Values_RSSI_B == 0 && rssi_set.Values_RSSI_C == 0){
                Toast.makeText(getApplicationContext(), "Device not found.", Toast.LENGTH_SHORT).show();
                plot_area.removeAllViews();
            }
            else {
                PointLocation location = Calculate(rssi_set);

                // Plot using this
                plot_area.removeAllViews();
                plot_area.addView(location_dot);
                location_dot.setX(Math.round(location.y));
                location_dot.setY(Math.round(location.x));

                plot_area.addView(XYPointValue);
                XYPointValue.setX(Math.round(location.y));
                XYPointValue.setY(Math.round(location.x) + 10);
            }
        }
    }

    //Calculate function - make all data initialization global
    protected PointLocation Calculate(RSSI_Set rssi_set)
    {

        double RSSI_A, RSSI_B, RSSI_C;
        //Take input from server and apply smoothing function and average
        RSSI_A = -rssi_set.Values_RSSI_A;
        RSSI_B = -rssi_set.Values_RSSI_B;
        RSSI_C = -rssi_set.Values_RSSI_C;

        //Get From room_layout DB for each room
        //Beacon A (0,0), Beacon B (d,0), Beacon C (i,j) Locations are Known -
        double d = 7.2;
        double i = 3.6;
        double j = 3.4;

        //Assuming value of n(path loss) has been initialized while hardware setup
        //Assumed value of n=2;
        //Get From room_layout DB for each room
        double n = 2.0;

        //RSSI_1M can be fixed
        //Get From room_layout DB for each room
        int RSSI_1M = -60;

        //Converting RSSI to Distance
        double Distance_A, Distance_B, Distance_C;

        /*
        Distance_A = 1 * Math.exp((RSSI_A - RSSI_1M) / (10 * n));
        Distance_B = 1 * Math.exp((RSSI_B - RSSI_1M) / (10 * n));
        Distance_C = 1 * Math.exp((RSSI_C - RSSI_1M) / (10 * n));
        */

        Distance_A = 1 * Math.exp((RSSI_1M - RSSI_A) / (10 * n));
        Distance_B = 1 * Math.exp((RSSI_1M - RSSI_B) / (10 * n));
        Distance_C = 1 * Math.exp((RSSI_1M - RSSI_C) / (10 * n));

        //Perform Trilateration with one point at 0,0

        PointLocation location = new PointLocation();

        //d is the distance between Beacon A and B, and i,j between A and C

        location.x = Math.abs((Math.pow(Distance_A, 2) - Math.pow(Distance_B, 2) + Math.pow(d, 2)) / (2 * d));

        location.y = Math.abs(Math.abs((Math.pow(Distance_A, 2) - Math.pow(Distance_C, 2) + Math.pow(i, 2) + Math.pow(j, 2)) / (2 * j)) - ((i / j) * location.x));

        if(location.x > d)
            location.x = d;
        if (location.x < 0)
            location.x = 0;
        if(location.y > j)
            location.y = j;
        if (location.y < 0)
            location.y = 0;

        XYPointValue.setText("X: "+ String.format("%.2f", location.x) + "\nY: "+ String.format("%.2f", location.y));

        location = getInPixel(location);

        //Return both values as object
        return location;
    }

    private PointLocation getInPixel(PointLocation _loc){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ImageView floor_layout = (ImageView) findViewById(R.id.display_image);
        /*
        _loc.x = (double) ((_loc.x / 7.2) * floor_layout.getWidth()) + floor_layout.getLeft();
        _loc.y = (double) ((_loc.y / 3.4) * floor_layout.getHeight()) + floor_layout.getTop() + (57 * metrics.density);
        */
        _loc.x = (double) ((_loc.x / 7.2) * floor_layout.getHeight()) + (floor_layout.getTop() + (57 * metrics.density));
        _loc.y = (double) ((_loc.y / 3.4) * floor_layout.getWidth()) + floor_layout.getLeft();
        return _loc;
    }

}


