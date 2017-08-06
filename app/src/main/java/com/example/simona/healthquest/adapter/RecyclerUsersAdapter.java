package com.example.simona.healthquest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.model.User;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by user on 06.8.2017.
 */

public class RecyclerUsersAdapter extends RecyclerView.Adapter<RecyclerUsersAdapter.MyViewHolder> {


    private List<User> users;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tvUsername;
        public TextView tvName;
        public TextView tvLastname;
        public TextView tvPoints;
        public TextView tvPosition;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvLastname = (TextView) itemView.findViewById(R.id.tvLastname);
            tvPoints = (TextView) itemView.findViewById(R.id.tvPoints);
            tvPosition = (TextView) itemView.findViewById(R.id.tvPosition);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_users, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerUsersAdapter.MyViewHolder holder, int position) {
        User user = users.get(position);
        position++;
        holder.tvPosition.setText(position + "");
        holder.tvUsername.setText(user.getUsername());
        holder.tvName.setText(user.getFirstName());
        holder.tvLastname.setText(user.getLastName());
        holder.tvPoints.setText(user.getPoints().toString());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsers(List<User> items) {
        users = items;
        notifyDataSetChanged();
    }

    public RecyclerUsersAdapter(List<User> users){
        this.users = users;
    }

}
