package com.example.insnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    private String[] title = {"为你推荐", "国际", "时政", "财经", "热点"};

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        配置顶部导航栏
        TopbarController();

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
                                .addToBackStack(null).
                                commit();

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