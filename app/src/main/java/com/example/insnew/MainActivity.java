package com.example.insnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.first_cards);

        //初始化卡片填充内容
        List<String> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add("第"+i+"张");
        }
        //设置为true保证每个card尺寸固定，不需要根据内容重新计算
        recyclerView.setHasFixedSize(true);
        //设置card滚动的布局和方向
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        this,LinearLayoutManager.HORIZONTAL,false));
        First_CardsAdapter first_cardsAdapter = new First_CardsAdapter(list);
        recyclerView.setAdapter(first_cardsAdapter);
    }
}