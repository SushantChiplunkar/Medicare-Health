package com.test.medicarehealth.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.medicarehealth.R;
import com.test.medicarehealth.databinding.ActivityDetailScreenBinding;
import com.test.medicarehealth.model.Article;
import com.test.medicarehealth.util.DateTimeUtil;
import com.test.medicarehealth.util.DownloadImage;
import com.test.medicarehealth.util.ImageStorage;

import java.io.File;

public class DetailScreen extends AppCompatActivity {
    private ActivityDetailScreenBinding binding;

    private LiveData<Article> articleLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_screen);
        binding.setLifecycleOwner(this);

        if (getIntent()!=null && getIntent().hasExtra(HomeScreen.DATA)){
            MutableLiveData<Article> article = new MutableLiveData<>();
            article.setValue(getIntent().getParcelableExtra(HomeScreen.DATA));
            articleLiveData = article;
        }

        articleLiveData.observe(this,article -> {
            if (article!=null)
                updateView(article);
            else showMessageView();
        });

    }

    private void showMessageView() {
        binding.detailScreenWebView.setVisibility(View.GONE);
        binding.emptyMessage.setVisibility(View.VISIBLE);
    }

    private void updateView(Article article) {
        binding.detailScreenWebView.setVisibility(View.VISIBLE);
        binding.emptyMessage.setVisibility(View.GONE);

        Glide.with(this).load(article.getImageUrl()).into(binding.itemImage);
        binding.newsSiteTV.setText("News site: "+article.getNewsSite());
        binding.titleDSTv.setText("Title: "+article.getTitle());
        binding.summaryDSTv.setText("Summary: "+ article.getSummary());
        binding.publishAtDSTv.setText("Publish At: "+ DateTimeUtil.getDateTimeFrom(article.getPublishedAt()));
        if (article.getUrl()!=null && article.getUrl().length()>0)
            binding.detailScreenWebView.loadUrl(article.getUrl());
        if (article.getImageUrl()!=null && !article.getImageUrl().isEmpty()) {
            binding.saveImageBtn.setVisibility(View.VISIBLE);
        }else binding.saveImageBtn.setVisibility(View.GONE);

        binding.saveImageBtn.setOnClickListener(view -> {
            if (article.getImageUrl()!=null && article.getImageUrl().isEmpty()) {
                downloadFile(article.getImageUrl());
            }
        });
    }

    private String getFileName(String imageUrl) {
        String fileName = imageUrl.substring( imageUrl.lastIndexOf('/')+1, imageUrl.length());
        String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
        return fileNameWithoutExtn;
    }

    private void downloadFile(String uRl) {
        File direct = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/IMAGES/");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Image download")
                .setDescription("Image file download..")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "/MedicareHealth"+getFileName(uRl));

        mgr.enqueue(request);

    }


}