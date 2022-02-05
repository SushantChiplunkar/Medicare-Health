package com.test.medicarehealth.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.test.medicarehealth.MainActivity;
import com.test.medicarehealth.R;
import com.test.medicarehealth.adapter.ArticleListAdapter;
import com.test.medicarehealth.databinding.ActivityHomeScreenBinding;
import com.test.medicarehealth.model.Article;
import com.test.medicarehealth.util.KeyEnum;
import com.test.medicarehealth.viewmodel.ArticlesViewModel;

public class HomeScreen extends AppCompatActivity {
    public static final String DATA = "article_data";
    private ActivityHomeScreenBinding binding;
    private ArticlesViewModel viewModel;
    private ArticleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_screen);
        binding.setLifecycleOwner(this);

        setSupportActionBar(binding.homeTitleLayout.toolbar);
        getSupportActionBar().setTitle("Articles");
        viewModel = new ViewModelProvider(this).get(ArticlesViewModel.class);
        hideListView();

        viewModel.getArticles().observe(this,articles -> {
            if (articles!=null && articles.size()>0){
                adapter.submitList(articles);
                showListView();
            }else Toast.makeText(this, "fail...", Toast.LENGTH_SHORT).show();
        });

        adapter = new ArticleListAdapter();
        binding.itemList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.itemList.setAdapter(adapter);
        adapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article item) {
                Intent intent = new Intent(HomeScreen.this,DetailScreen.class);
                intent.putExtra(DATA,item);
                startActivity(intent);
            }
        });

    }

    private void hideListView() {
        binding.itemList.setVisibility(View.GONE);
        binding.progressbar.setVisibility(View.VISIBLE);
    }

    private void showListView() {
        binding.progressbar.setVisibility(View.GONE);
        binding.itemList.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_logout_action: {  //index returned when home button pressed
                appLogoutPress();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void appLogoutPress() {
        new AlertDialog.Builder(this).setTitle("Logout Alert!")
                .setMessage("Are you sure you have to log out?")
                .setPositiveButton("Yes",(dialogInterface, i) -> {
                  logoutProcessed();
                  dialogInterface.dismiss();
                }).setNegativeButton("No",(dialogInterface, i) -> {
                    dialogInterface.dismiss();
        }).setCancelable(false).show();
    }

    private void logoutProcessed() {
        Prefs.putBoolean(KeyEnum.IS_LOGIN.toString(),false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}