package com.example.insnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRadioButton();
        //默认加载fragment
        AddFragment();
        //配置顶部导航栏
        TopbarController();
    }

    private void initRadioButton() {
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