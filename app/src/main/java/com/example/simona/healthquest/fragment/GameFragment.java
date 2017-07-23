package com.example.simona.healthquest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.adapter.RecyclerAnswerAdapter;
import com.example.simona.healthquest.adapter.RecyclerImageAnswerAdapter;
import com.example.simona.healthquest.enumeration.QuestionType;
import com.example.simona.healthquest.model.AnswerImage;
import com.example.simona.healthquest.model.Question;
import com.example.simona.healthquest.model.QuestionAnswer;
import com.example.simona.healthquest.model.QuestionImage;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.model.UserQuestion;
import com.example.simona.healthquest.network.RetrofitManager;
import com.example.simona.healthquest.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Simona on 7/19/2017.
 */

public class GameFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout questionAnswer;
    private RelativeLayout imageQuestionAnswer;
    private RelativeLayout questionImageAnswers;
    private RelativeLayout endOfGame;
    private RecyclerView rvQuestionAnswers;
    private RecyclerView rvImageQuestionAnswers;
    private RecyclerView rvQuestionImageAnswers;
    private TextView tvQuestionAnswer;
    private TextView tvEnd;
    private TextView tvImageQuestionAnswer;
    private TextView tvQuestionImageAnswer;
    private Button btnGameSaveAnswer;
    private Button btnSaveImageQuestionAnswer;
    private Button btnGoBack;
    private Button btnSaveQuestionImageAnswer;
    private ImageView ivImageQuestion;
    private RecyclerAnswerAdapter answerAdapter;
    private RecyclerImageAnswerAdapter imageAnswerAdapter;
    private User user;
    private Question question;
    private QuestionImage questionImage;
    private List<QuestionAnswer> answerList;
    private List<AnswerImage> answerImageList;
    private Date opened;
    private Date answered;
    private int numberQuestions;
    private int points;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_game, container, false);

        tvQuestionAnswer = (TextView) rootView.findViewById(R.id.tvQuestionAnswer);
        tvEnd = (TextView) rootView.findViewById(R.id.tvEnd);
        tvImageQuestionAnswer = (TextView) rootView.findViewById(R.id.tvImageQuestionAnswer);
        tvQuestionImageAnswer = (TextView) rootView.findViewById(R.id.tvQuestionImageAnswer);

        rvQuestionAnswers = (RecyclerView) rootView.findViewById(R.id.rvQuestionAnswers);
        rvImageQuestionAnswers = (RecyclerView) rootView.findViewById(R.id.rvImageQuestionAnswers);
        rvQuestionImageAnswers = (RecyclerView) rootView.findViewById(R.id.rvQuestionImageAnswers);

        btnGameSaveAnswer = (Button) rootView.findViewById(R.id.btnGameSaveAnswer);
        btnGameSaveAnswer.setOnClickListener(this);
        btnSaveImageQuestionAnswer = (Button) rootView.findViewById(R.id.btnSaveImageQuestionAnswer);
        btnSaveImageQuestionAnswer.setOnClickListener(this);
        btnGoBack = (Button) rootView.findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(this);
        btnSaveQuestionImageAnswer = (Button) rootView.findViewById(R.id.btnSaveQuestionImageAnswer);
        btnSaveQuestionImageAnswer.setOnClickListener(this);

        questionAnswer = (RelativeLayout) rootView.findViewById(R.id.questionAnswer);
        imageQuestionAnswer = (RelativeLayout) rootView.findViewById(R.id.imageQuestionAnswer);
        questionImageAnswers = (RelativeLayout) rootView.findViewById(R.id.questionImageAnswers);
        endOfGame = (RelativeLayout) rootView.findViewById(R.id.endOfGame);

        ivImageQuestion = (ImageView) rootView.findViewById(R.id.ivImageQuestion);

        answerList = new ArrayList<>();
        answerImageList = new ArrayList<>();

        answerAdapter = new RecyclerAnswerAdapter(answerList);
        imageAnswerAdapter = new RecyclerImageAnswerAdapter(answerImageList, context);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context);
        rvImageQuestionAnswers.setLayoutManager(layoutManager1);
        rvQuestionAnswers.setLayoutManager(layoutManager2);
        rvQuestionImageAnswers.setLayoutManager(new GridLayoutManager(context, 2));
        rvQuestionImageAnswers.setNestedScrollingEnabled(false);

        rvQuestionAnswers.setAdapter(answerAdapter);
        rvImageQuestionAnswers.setAdapter(answerAdapter);
        rvQuestionImageAnswers.setAdapter(imageAnswerAdapter);

        numberQuestions = 5;
        points = 0;
        generateQuestionAnswer();

        return rootView;
    }

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    private void generateQuestionAnswer() {
        endOfGame.setVisibility(View.GONE);
        questionAnswer.setVisibility(View.GONE);
        imageQuestionAnswer.setVisibility(View.GONE);
        questionImageAnswers.setVisibility(View.GONE);
        RetrofitManager.getInstance().getRetrofitService().getUser(Long.valueOf(1)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();

                    RetrofitManager.getInstance().getRetrofitService().getRandomQuestion(user.getLevel().id, user.id).enqueue(new Callback<Question>() {
                        @Override
                        public void onResponse(Call<Question> call, Response<Question> response) {
                            if (response.isSuccessful()) {
                                question = response.body();

                                if (question.getQuestionType() == QuestionType.ANSWER_SELECT) {
                                    questionAnswer.setVisibility(View.VISIBLE);
                                    tvQuestionAnswer.setText(question.getQuestion());
                                    RetrofitManager.getInstance().getRetrofitService().getAnswers(question.id).enqueue(new Callback<List<QuestionAnswer>>() {
                                        @Override
                                        public void onResponse(Call<List<QuestionAnswer>> call, Response<List<QuestionAnswer>> response) {
                                            if (response.isSuccessful()) {
                                                opened = null;
                                                answerList = response.body();
                                                answerAdapter.updateAnswers(answerList);
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<List<QuestionAnswer>> call, Throwable t) {
                                        }
                                    });
                                } else if (question.getQuestionType() == QuestionType.IMAGE_SELECT) {
                                    imageQuestionAnswer.setVisibility(View.VISIBLE);
                                    tvImageQuestionAnswer.setText(question.getQuestion());
                                    Picasso.with(context).load(Constants.BASE_URL+"/questionImage/getImage/" + question.id).into(ivImageQuestion);
                                    RetrofitManager.getInstance().getRetrofitService().getAnswers(question.id).enqueue(new Callback<List<QuestionAnswer>>() {
                                        @Override
                                        public void onResponse(Call<List<QuestionAnswer>> call, Response<List<QuestionAnswer>> response) {
                                            if (response.isSuccessful()) {
                                                opened = null;
                                                answerList = response.body();
                                                answerAdapter.updateAnswers(answerList);
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<List<QuestionAnswer>> call, Throwable t) {
                                        }
                                    });
                                } else if(question.getQuestionType()==QuestionType.MULTIPLE_IMAGE_SELECT){
                                    questionImageAnswers.setVisibility(View.VISIBLE);
                                    tvQuestionImageAnswer.setText(question.getQuestion());
                                    RetrofitManager.getInstance().getRetrofitService().getImageAnswers(question.id).enqueue(new Callback<List<AnswerImage>>() {
                                        @Override
                                        public void onResponse(Call<List<AnswerImage>> call, Response<List<AnswerImage>> response) {
                                            if (response.isSuccessful()) {
                                                opened = null;
                                                answerImageList = response.body();
                                                imageAnswerAdapter.updateAnswers(answerImageList);
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<List<AnswerImage>> call, Throwable t) {
                                        }
                                    });
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Question> call, Throwable t) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGameSaveAnswer:
                saveQuestionAnswer();
                break;
            case R.id.btnSaveImageQuestionAnswer:
                saveQuestionAnswer();
                break;
            case R.id.btnGoBack:
                supportFragmentManager.popBackStack();
                break;
            case R.id.btnSaveQuestionImageAnswer:
                saveImageAnswers();
                break;
        }
    }

    private void saveImageAnswers() {
        answered = null;
        if(imageAnswerAdapter.selectedItem!=-1){
            AnswerImage answerImage = answerImageList.get(imageAnswerAdapter.selectedItem);
            if(question!=null && user!=null){
                final UserQuestion userQuestion = new UserQuestion();
                userQuestion.setUser(user);
                userQuestion.setQuestion(question);
                userQuestion.setAnswerImage(answerImage);
                userQuestion.setOpenedAt(opened);
                userQuestion.setAnsweredAt(answered);
                userQuestion.setWin(answerImage.isStatus());
                if(answerImage.isStatus()){
                    userQuestion.setPoints(user.getLevel().getXp());
                }else {
                    userQuestion.setPoints(0);
                }
                RetrofitManager.getInstance().getRetrofitService().saveUserAnswer(userQuestion).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            numberQuestions--;
                            points+=userQuestion.getPoints();
                            if(numberQuestions>0){
                                answerAdapter.selectedItem = -1;
                                generateQuestionAnswer();
                            }else if(numberQuestions==0){
                                questionAnswer.setVisibility(View.GONE);
                                imageQuestionAnswer.setVisibility(View.GONE);
                                questionImageAnswers.setVisibility(View.GONE);
                                endOfGame.setVisibility(View.VISIBLE);
                                tvEnd.setText("Congratulations you finished the game!\n\nYou won "+ points+" points");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void saveQuestionAnswer() {
        answered = null;
        if(answerAdapter.selectedItem!=-1){
            QuestionAnswer answer = answerList.get(answerAdapter.selectedItem);
            if(question!=null && user!=null){
                final UserQuestion userQuestion = new UserQuestion();
                userQuestion.setUser(user);
                userQuestion.setQuestion(question);
                userQuestion.setQuestionAnswer(answer);
                userQuestion.setOpenedAt(opened);
                userQuestion.setAnsweredAt(answered);
                userQuestion.setWin(answer.isStatus());
                if(answer.isStatus()){
                    userQuestion.setPoints(user.getLevel().getXp());
                }else {
                    userQuestion.setPoints(0);
                }
                RetrofitManager.getInstance().getRetrofitService().saveUserAnswer(userQuestion).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            numberQuestions--;
                            points+=userQuestion.getPoints();
                            if(numberQuestions>0){
                                answerAdapter.selectedItem = -1;
                                generateQuestionAnswer();
                            }else if(numberQuestions==0){
                                questionAnswer.setVisibility(View.GONE);
                                imageQuestionAnswer.setVisibility(View.GONE);
                                questionImageAnswers.setVisibility(View.GONE);
                                endOfGame.setVisibility(View.VISIBLE);
                                tvEnd.setText("Congratulations you finished the game!\n\nYou won "+ points+" points");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
