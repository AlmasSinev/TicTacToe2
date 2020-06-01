package com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private View view;

    private int[] boxes;

    ItemAdapter(Context context, int[] boxes) {
        this.context = context;
        this.boxes = boxes;
    }

    void setBoxes(int[] boxes) {
        this.boxes = boxes;
    }

    @Override
    public int getCount() {
        return boxes.length;
    }

    @Override
    public Object getItem(int position) {
        return boxes[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view = new View(context);
            view = inflater.inflate(R.layout.single_item, null);
            ImageView img = view.findViewById(R.id.img_view);
            img.setImageResource(boxes[position]);
        }
        return view;
    }
}
