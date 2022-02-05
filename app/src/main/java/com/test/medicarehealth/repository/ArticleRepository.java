package com.test.medicarehealth.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.medicarehealth.config.ServiceGenerator;
import com.test.medicarehealth.model.Article;
import com.test.medicarehealth.webservice.APICalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private static ArticleRepository instance;
    private APICalls apiCalls;
    private MutableLiveData<Boolean> isDataFetchDone = new MutableLiveData<>();

    public static ArticleRepository getInstance() {
        if (instance==null)
            instance = new ArticleRepository();
        return instance;
    }

    public ArticleRepository() {
        apiCalls = ServiceGenerator.createService(APICalls.class);
    }

    public LiveData<List<Article>> getArticles(final Context context){
        isDataFetchDone.setValue(false);
        final MutableLiveData<List<Article>> articles = new MutableLiveData<>();
        apiCalls.getArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful()){
                    articles.postValue(response.body());
                    isDataFetchDone.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                isDataFetchDone.postValue(true);
            }
        });

        return articles;
    }
}
