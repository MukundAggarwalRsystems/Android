package com.example.tushar.ips_rev1;

/**
 * Created by Tushar on 22-Jun-16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class chart_view_adpater extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final String[] values2;

    public chart_view_adpater(Context context, String[] values, String values2[]) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.values2 = values2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.chart_view_rowlayout, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id
                .label);
        textView1.setText(values[position]);

        TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
        textView2.setText(values2[position]);

        TextView textView3 = (TextView) rowView.findViewById(R.id.label3);
        textView3.setText(values2[position]);

        TextView textView4 = (TextView) rowView.findViewById(R.id.label4);
        textView4.setText(values[position]);

        return rowView;
    }
}

