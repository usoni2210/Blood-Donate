package in.twister.blood_donate.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import in.twister.blood_donate.R;

public class BloodTypeAdapter extends ArrayAdapter<String> {
    private String[] text;
    private LayoutInflater inflater;

    public BloodTypeAdapter(@NonNull Context context, int resource, @NonNull String[] text) {
        super(context, resource, text);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.text = text;
    }

    @NonNull
    public View getView(final int position, View view, @NonNull ViewGroup parent) {
        View view1 = inflater.inflate(R.layout.layout_bloodgrp_listview, parent, false);

        TextView name = view1.findViewById(R.id.name);
        name.setText(text[position]);
        return view1;
    }
}