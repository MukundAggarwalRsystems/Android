package com.example.rsi.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.view.View;

import layout.FragmentOne;
import layout.FragmentTwo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Changefragment(View view){
        Fragment fragment;

        if (view == findViewById(R.id.button_fragment1)){
            FragmentOne fragmentOne = new FragmentOne();
            fragment = fragmentOne;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
        if (view == findViewById(R.id.button_fragment2)){
            FragmentTwo fragmenttwo = new FragmentTwo();
            fragment = fragmenttwo;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }

    }

}
