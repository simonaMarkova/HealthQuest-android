package com.example.simona.healthquest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.model.QuestionAnswer;

import java.util.List;

/**
 * Created by Simona on 7/19/2017.
 */

public class RecyclerAnswerAdapter extends RecyclerView.Adapter<RecyclerAnswerAdapter.MyViewHolder>{

    public int selectedItem = -1;
    List<QuestionAnswer> questionAnswerList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private RadioButton questionAnswer;

        public MyViewHolder(View itemView) {
            super(itemView);

            questionAnswer = (RadioButton) itemView.findViewById(R.id.questionAnswer);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0,questionAnswerList.size());
                }
            };

            itemView.setOnClickListener(clickListener);
            questionAnswer.setOnClickListener(clickListener);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_answer, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAnswerAdapter.MyViewHolder holder, int position) {
        QuestionAnswer questionAnswer = questionAnswerList.get(position);
        holder.questionAnswer.setChecked(position == selectedItem);
        holder.questionAnswer.setText(questionAnswer.getAnswer().getDescription().toString());
    }

    @Override
    public int getItemCount() {
        return questionAnswerList.size();
    }

    public void updateAnswers(List<QuestionAnswer> items) {
        questionAnswerList = items;
        notifyDataSetChanged();
    }

    public RecyclerAnswerAdapter(List<QuestionAnswer> questionAnswerList){
        this.questionAnswerList = questionAnswerList;
    }

}
