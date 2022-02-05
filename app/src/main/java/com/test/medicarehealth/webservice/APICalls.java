package com.test.medicarehealth.webservice;

import com.test.medicarehealth.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICalls {

    @GET("/v3/articles")
    Call<List<Article>> getArticles();

}
