package com.test.medicarehealth.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.medicarehealth.model.Article;
import com.test.medicarehealth.repository.ArticleRepository;

import java.util.List;

public class ArticlesViewModel extends AndroidViewModel {
    private ArticleRepository repository;
    private MutableLiveData<Boolean> isDataFetchDone = new MutableLiveData<>();

    public ArticlesViewModel(@NonNull Application application) {
        super(application);
        this.repository = ArticleRepository.getInstance();
        this.isDataFetchDone.setValue(false);
    }

    public LiveData<List<Article>> getArticles(){
        return repository.getArticles(getApplication().getApplicationContext());
    }


}
