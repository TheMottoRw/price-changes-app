package com.example.pricechanges;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubscribersAdapter extends RecyclerView.Adapter<SubscribersAdapter.MyViewHolder> {
    public LinearLayout v;
    public Context ctx;
    private JSONArray mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubscribersAdapter(Context context, JSONArray myDataset) {
        super();
        mDataset = myDataset;
        ctx = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SubscribersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_users, parent, false);
        SubscribersAdapter.MyViewHolder vh = new SubscribersAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final SubscribersAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            JSONObject currentObj = mDataset.getJSONObject(position);
            holder.id.setText(currentObj.getString("id"));
            holder.name.setText(currentObj.getString("name"));
            holder.phone.setText(currentObj.getString("phone"));

        } catch (JSONException ex) {

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView id,name,phone;
        public LinearLayout lnlayout;

        public MyViewHolder(LinearLayout lny) {
            super(lny);
            lnlayout = lny.findViewById(R.id.lnyLayout);
            id = lny.findViewById(R.id.tvId);
            name = lny.findViewById(R.id.tvUsername);
            phone = lny.findViewById(R.id.tvPhone);

        }
    }
}