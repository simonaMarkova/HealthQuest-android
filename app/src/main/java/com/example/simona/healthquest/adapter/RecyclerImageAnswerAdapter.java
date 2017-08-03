package com.example.simona.healthquest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.model.AnswerImage;
import com.example.simona.healthquest.util.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Simona on 7/21/2017.
 */

public class RecyclerImageAnswerAdapter  extends RecyclerView.Adapter<RecyclerImageAnswerAdapter.MyViewHolder>{

    public int selectedItem = -1;
    List<AnswerImage> answerImageList;
    private Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        AnswerImage answerImage = answerImageList.get(position);
        holder.rbImageAnswer.setChecked(position == selectedItem);
        if(holder.rbImageAnswer.isChecked()){
            holder.imageLayout.setBackground(context.getResources().getDrawable(R.drawable.customborder));
        }else{
            holder.imageLayout.setBackgroundResource(0);
        }
        Picasso.with(context).load(Constants.BASE_URL+"/answerImage/getImage/" + answerImage.getQuestion().id+"/"+answerImage.getNumber()).into(holder.ivImageAnswer, new Callback() {
            @Override
            public void onSuccess() {
                holder.rbImageAnswer.setBackground(holder.ivImageAnswer.getDrawable());
                holder.ivImageAnswer.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return answerImageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton rbImageAnswer;
        ImageView ivImageAnswer;
        LinearLayout imageLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            rbImageAnswer = (RadioButton) itemView.findViewById(R.id.rbImageAnswer);
            ivImageAnswer = (ImageView) itemView.findViewById(R.id.ivImageAnswer);
            imageLayout = (LinearLayout) itemView.findViewById(R.id.imageLayout);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0,answerImageList.size());
                }
            };

            itemView.setOnClickListener(clickListener);
            rbImageAnswer.setOnClickListener(clickListener);
        }
    }

    public void updateAnswers(List<AnswerImage> items) {
        answerImageList = items;
        notifyDataSetChanged();
    }

    public RecyclerImageAnswerAdapter(List<AnswerImage> questionAnswerList, Context context){
        this.answerImageList = questionAnswerList;
        this.context = context;
    }
}
