package com.example.tushar.ips_rev1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class room_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_view);

        Intent i = getIntent();
        final String selected_room = i.getStringExtra("selected_room");
        TextView display_selected_room = (TextView)findViewById(R.id.selected_room);
        display_selected_room.setText(selected_room);
    }
}
