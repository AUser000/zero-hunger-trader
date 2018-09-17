package com.zerohunger.zerohungertrader.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zerohunger.zerohungertrader.MainActivity;
import com.zerohunger.zerohungertrader.R;
import com.zerohunger.zerohungertrader.model.Order;

import java.util.List;

/**
 * Created by Dhanushka Dharmasena on 12/09/2018.
 */
public class OrderListAdapter extends ArrayAdapter<Order> {

    Context context;
    int resource;
    List<Order> objects;

    public OrderListAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        TextView customerName = view.findViewById(R.id.userName);
        TextView itemName = view.findViewById(R.id.itemName);
        TextView itemPrice = view.findViewById(R.id.price);
        TextView itemQut = view.findViewById(R.id.qut);
        TextView itemTime = view.findViewById(R.id.time);
        final FloatingActionButton submitBtn = view.findViewById(R.id.submitBtn);
        final FloatingActionButton cancelBtn = view.findViewById(R.id.cancelBtn);

        Order object = objects.get(position);

        customerName.setText(""+ object.clientName);
        itemName.setText("" + object.itemName);
        itemPrice.setText("" + object.price);
        itemQut.setText(""+ object.quantity);
        itemTime.setText(""+ object.startedTime);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelBtn.setVisibility(view.INVISIBLE);
                submitBtn.setBackgroundTintList(ColorStateList.valueOf(Color
                        .parseColor("#27ab1c")));// #27ab1c
                Toast.makeText(context, "looking for accept?",Toast.LENGTH_SHORT ).show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objects.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "looking for delete?",Toast.LENGTH_SHORT ).show();
            }
        });

        return view;
    }
}
