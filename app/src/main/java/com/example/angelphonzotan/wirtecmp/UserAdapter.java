package com.example.angelphonzotan.wirtecmp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anjoh on 13/03/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<User> UserList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, time;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
        }
    }


    public UserAdapter(List<User> RestaurantList) {
        this.UserList = RestaurantList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User restaurant = UserList.get(position);
        holder.name.setText(restaurant.getName());
        holder.time.setText(restaurant.getTime());

    }




    @Override
    public int getItemCount() {
        return UserList.size();
    }
}
