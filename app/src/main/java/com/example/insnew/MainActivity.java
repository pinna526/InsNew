package com.example.insnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    private String[] title = {"头条", "新闻", "财经", "科技"};

    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4;
    FrameLayout splash;
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setBackgroundResource(R.drawable.background_white);
        setContentView(R.layout.activity_main);

        //管理启动页面
//        SplashManager();
//        splash = (FrameLayout)findViewById(R.id.splash);
//        Message msg = new Message();
//        msg.what = STOPSPLASH;
//        splashManager.sendMessageDelayed(msg, SPLASHTIME);
        //初始化按钮
        InitRadioButton();
        //默认加载fragment
        AddFragment();
        //配置顶部导航栏
        TopbarController();
    }

@SuppressLint("HandlerLeak")
private Handler splashManager = new Handler(){
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case STOPSPLASH:
                SystemClock.sleep(4000);
                splash.setVisibility(View.GONE);
                break;
        }
        super.handleMessage(msg);
    }

};

    private void InitRadioButton() {
        radioButton1 = (RadioButton)findViewById(R.id.button1);
        radioButton2 = (RadioButton)findViewById(R.id.button2);
        radioButton3 = (RadioButton)findViewById(R.id.button3);
        radioButton4 = (RadioButton)findViewById(R.id.button4);

        radioButton1.setText(title[0]);
        radioButton2.setText(title[1]);
        radioButton3.setText(title[2]);
        radioButton4.setText(title[3]);

    }

    /*
    默认加载第一个fragment
     */
    private void AddFragment() {
        //向卡片传值
        First_Fragment first_fragment = new First_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("title", title[0]);
        first_fragment.setArguments(bundle);
        //用管理器管理该fragment
        fragmentManager.beginTransaction()
                .add(R.id.cards_fragment,first_fragment)
                .commit();
//        getSupportFragmentManager().beginTransaction().show(first_fragment).commit();
    }

    /*
    根据选中否渲染按钮字体
    根据选中值加载卡片
     */
    private void TopbarController() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //默认选中第一个
        RadioButton rb = (RadioButton)radioGroup.getChildAt(0);
        rb.setSelected(true);

        //根据选中情况渲染不同的按钮样式
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int buttonCount = radioGroup.getChildCount();
                for (int i = 0; i < buttonCount; i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton.isChecked()) {
                        //被选中的按钮字体和颜色
                        String title_select = radioButton.getText().toString();
//                        Log.i(TAG, "onCheckedChanged:" + title_select);
                        radioButton.setTextSize(18);
                        radioButton.setTextColor(MainActivity.this.getResources().getColor(R.color.colorBlack));

                        //向卡片传值
                        First_Fragment first_fragment = new First_Fragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title_select);
                        first_fragment.setArguments(bundle);
                        //用管理器管理该fragment
                        fragmentManager.beginTransaction()
                                .replace(R.id.cards_fragment, first_fragment)
                                .addToBackStack(null)
                                .show(first_fragment)
                                .commit();
//                        fragmentManager.beginTransaction()
//                                .add(R.id.cards_fragment,First_Fragment.newInstance(title_select))
//                                .commit();
//                        fragmentManager.beginTransaction()
//                                .add(R.id.cards_fragment,first_fragment)
//                                .commit();

                    } else {
                        //未被选中
                        radioButton.setTextSize(14);
                        radioButton.setTextColor(MainActivity.this.getResources().getColor(R.color.colorGray));
                    }
                }
            }
        });
    }


}