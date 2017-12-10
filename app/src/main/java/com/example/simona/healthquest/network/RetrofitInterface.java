package com.example.simona.healthquest.network;

import com.example.simona.healthquest.helper.BonusQuestion;
import com.example.simona.healthquest.model.AnswerImage;
import com.example.simona.healthquest.model.Disease;
import com.example.simona.healthquest.model.FacebookLogin;
import com.example.simona.healthquest.model.Level;
import com.example.simona.healthquest.model.LoginInfo;
import com.example.simona.healthquest.model.Question;
import com.example.simona.healthquest.model.QuestionAnswer;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.model.UserQuestion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Simona on 7/17/2017.
 */

public interface RetrofitInterface {

    @GET("/level/")
    Call<List<Level>> getLevels();

    @POST("/user/register/")
    Call<User> registerUser(@Body User user);

    @GET("/level/{id}")
    Call<Level> getLevel(@Path("id") Long id);

    @GET("/question/random/{levelId}/{userId}")
    Call<Question> getRandomQuestion(@Path("levelId") Long levelId, @Path("userId") Long userId);

    @POST("/user/updateLevel/{userId}/{levelId}")
    Call<User> updateLevelForUser(@Path ("userId") Long userId, @Path("levelId") Long levelId);

    @GET("/user/find/{id}")
    Call<User> getUser(@Path("id")Long id);

    @GET("/user/find-all")
    Call<List<User>> getAllUsers ();

    @GET("/questionAnswer/getByQuestion/{id}")
    Call<List<QuestionAnswer>> getAnswers(@Path("id") Long id);

    @POST("/userQuestion/")
    Call<Void> saveUserAnswer(@Body UserQuestion userQuestion);

    @GET("/answerImage/getByQuestion/{id}")
    Call<List<AnswerImage>> getImageAnswers(@Path("id") Long id);

    @POST("/user/facebook/login")
    Call<User> facebookLogin(@Body FacebookLogin facebookLogin);

    @POST("/user/login")
    Call<User> login(@Body LoginInfo loginInfo);

    @POST("/user/update-photo")
    Call<Void> updatePhoto(@Body User user);

    @GET("/user/points/{id}")
    Call<Integer> getPoints(@Path("id") Long id);

    @GET("/question/bonus")
    Call<List<BonusQuestion>> getBonus();
}
