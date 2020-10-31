package com.example.insnew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;


public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    TextView news_title,news_content,news_src;
    ImageButton back_button;
    ImageView news_photo;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setBackgroundResource(R.drawable.background_white);
        setContentView(R.layout.activity_second);
        String news_data = getIntent().getStringExtra("news_data_title");
        JSONObject mydata = JSONObject.parseObject(news_data);
//        Log.i(TAG, "SeconCreate: "+mydata);

        news_title = (TextView)findViewById(R.id.news_title);
        news_content = (TextView)findViewById(R.id.news_content);
        news_src = (TextView)findViewById(R.id.news_src);
        news_photo = (ImageView)findViewById(R.id.news_photo);

        news_title.setText(mydata.getString("title"));
        news_content.setText(mydata.getString("content"));
        news_content.setText(Html.fromHtml(mydata.getString("content"),Html.FROM_HTML_MODE_LEGACY));
        news_src.setText(mydata.getString("src"));
        Glide.with(SecondActivity.this).load(mydata.getString("pic")).into(news_photo);

        //返回上一Activity
        backToPre();
    }

    private void backToPre() {
        back_button = (ImageButton)findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}