package com.example.tushar.ips_rev1;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class welcome_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_welcome_page);

            Button new_updates =(Button)findViewById(R.id.new_alerts);
            new_updates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView notification_heading = (TextView)findViewById(R.id.welcome_update);
                    notification_heading.setText(R.string.new_alerts_heading);
                    //Query new updates
                }
            });

            Button all_updates =(Button)findViewById(R.id.all_alerts);
            all_updates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView notification_heading = (TextView)findViewById(R.id.welcome_update);
                    notification_heading.setText(R.string.all_alerts_heading);
                    //Query All updates
                }
            });


            //Add Live Notification Field using list view
            //ListView for Notifications page
            //Implement Custom View
            ListView notifications = (ListView)findViewById(R.id.notifications_list);

            //Get Room values dynamically later from database - use SimpleCursorAdapter
            String[] notification_array = new String[] {"Notification 1","Notification 2","Notification 3","Notification 4","Notification 5","Notification 6",};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,android.R.id.text2, notification_array);
            notifications.setAdapter(adapter);

            //Add onClickListener for Notifications on ListView

            //Proceed Button
            Button track =(Button)findViewById(R.id.welcome_button);
            assert track != null;
            track.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), mystore.class);
                    startActivity(i);
                }
            });
    }
}
