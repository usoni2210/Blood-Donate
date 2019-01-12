package in.twister.blood_donate.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.twister.blood_donate.R;

public class GenderAdapter extends ArrayAdapter<String> {
    private String[] text;
    private int[] img;
    private LayoutInflater inflater;

    public GenderAdapter(@NonNull Context context, int resource, @NonNull String[] text, int[] img) {
        super(context, resource, text);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.text = text;
        this.img = img;
    }

    @NonNull
    public View getView(final int position, View view, @NonNull ViewGroup parent) {
        View view1 = inflater.inflate(R.layout.layout_gender_gridview, parent, false);

        ImageView gen_img = view1.findViewById(R.id.gender_img);
        TextView gen_txt = view1.findViewById(R.id.gender_text);

        gen_img.setImageResource(img[position]);
        gen_txt.setText(text[position]);
        return view1;
    }
}