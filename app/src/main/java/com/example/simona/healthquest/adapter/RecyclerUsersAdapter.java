package com.example.simona.healthquest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.util.Constants;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 06.8.2017.
 */

public class RecyclerUsersAdapter extends RecyclerView.Adapter<RecyclerUsersAdapter.MyViewHolder> {


    private List<User> users;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{


        public TextView tvName;
        public TextView tvPoints;
        public TextView tvPosition;
        ImageView ivRankUser;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPoints = (TextView) itemView.findViewById(R.id.tvPoints);
            tvPosition = (TextView) itemView.findViewById(R.id.tvPosition);
            ivRankUser = (ImageView) itemView.findViewById(R.id.ivRankUser);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_users, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerUsersAdapter.MyViewHolder holder, int position) {
        User user = users.get(position);
        position++;
        holder.tvPosition.setText(position + "");
        holder.tvName.setText(user.getFirstName()+ " " +user.getLastName());
        holder.tvPoints.setText(user.getPoints().toString());
        Picasso.with(context).load(Constants.BASE_URL + "/user/photo/" + user.id).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.brain).into(holder.ivRankUser);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsers(List<User> items) {
        users = items;
        notifyDataSetChanged();
    }

    public RecyclerUsersAdapter(List<User> users, Context context){
        this.users = users;
        this.context = context;
    }

}
