package com.example.tushar.ips_rev1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class chart_view extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);

        String[] values = new String[] { "Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name","Name"};
        String[] values2 = new String[] { "9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24","9:24"};
        chart_view_adpater adapter = new chart_view_adpater(this, values,values2);
        ListView test= (ListView)findViewById(R.id.chart_view_table);
        test.setAdapter(adapter);

        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        // creating data values
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));

        PieDataSet dataset = new PieDataSet(entries,"");

        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("In Time");
        labels.add("Out Time");
        labels.add("Productivity");
        labels.add("Absent");

        PieData data = new PieData(labels, dataset); // initialize Piedata
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(data);
        pieChart.setDescription("");
    }
}
