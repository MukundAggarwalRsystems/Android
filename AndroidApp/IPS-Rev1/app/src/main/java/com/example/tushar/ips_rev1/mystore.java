package com.example.tushar.ips_rev1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class mystore extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystore);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*
        //Search local DB to find number of rooms and add Buttons to display accordingly
        LinearLayout topbutton_linearlayout = (LinearLayout)findViewById(R.id.mypage_topbuttons_linearlayout);
        //number is the number of rooms retrieved from local DB
        int number=10;
        for(int i=1;i<=number;i++){
            Button btn = new Button(this);
            String temp = "Room" + i;
            btn.setText(temp);
            btn.setId(i);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            topbutton_linearlayout.addView(btn);
        }
        */


        final DBHelper db = new DBHelper(this);
        //Floor Spinner
        final Spinner mystore_floor_spinner = (Spinner) findViewById(R.id.mystore_floor_spinner);
        final ArrayAdapter<String> floor_spinner_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,db.getAllFloors() );
        floor_spinner_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mystore_floor_spinner.setAdapter(floor_spinner_dataAdapter);

        mystore_floor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                //Room Spinner made when floor selected only
                final Spinner mystore_room_spinner = (Spinner) findViewById(R.id.mystore_room_spinner);
                ArrayAdapter<String> room_spinner_dataAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, db.getAllRooms(mystore_floor_spinner.getSelectedItem().toString()));
                room_spinner_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mystore_room_spinner.setAdapter(room_spinner_dataAdapter);

                mystore_room_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });




        //ListView for Statistics page - implemet custom adpater here
        ListView rooms = (ListView)findViewById(R.id.statistics_list);

        //Get Room values dynamically later from database - use SimpleCursorAdapter
        String[] room_array = new String[] {"Statistic 1","Statistic 2","Statistic 3","Statistic 4","Statistic 5","Statistic 6",
                "Statistic 7","Statistic 8","Statistic 9","Statistic 10","Statistic 11","Statistic 12"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,android.R.id.text2, room_array);

        rooms.setAdapter(adapter);


        //Bottom Buttons
        Button live_view_button = (Button)findViewById(R.id.live_view_button);
        Button chart_view_button = (Button)findViewById(R.id.chart_view_button);
        Button table_view_button = (Button)findViewById(R.id.table_view_button);

        assert live_view_button != null;
        live_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), live_view.class);
                startActivity(i);
            }
        });

        assert table_view_button != null;
        table_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), table_view.class);
                startActivity(i);
            }
        });

        assert chart_view_button != null;
        chart_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), chart_view.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mystore, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_store_view) {
            //finish();
            //Intent i = new Intent(getApplicationContext(), mystore.class);
           // startActivity(i);
        } else if (id == R.id.add_id_here) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
