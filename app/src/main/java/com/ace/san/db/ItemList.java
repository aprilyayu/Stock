package com.ace.san.db;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.ace.san.R;
import com.ace.san.db.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class ItemList extends ArrayAdapter<Item> {
    private Activity context;
    List<Item> items;


    public ItemList(Activity context, List<Item> items) {
        super(context, R.layout.list, items);
        this.context = context;
        this.items = items;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_itemlist, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewCat = (TextView) listViewItem.findViewById(R.id.textViewCat);

        Item item = items.get(position);
        textViewName.setText(item.getName());
        textViewCat.setText(item.getCategory());


        return listViewItem;
    }

}