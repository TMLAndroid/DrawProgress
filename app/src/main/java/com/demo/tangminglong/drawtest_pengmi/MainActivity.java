package com.demo.tangminglong.drawtest_pengmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomProgressView customProgressView = (CustomProgressView) findViewById(R.id.custom_view);
        customProgressView.setTopText("已学习生词");
        customProgressView.setMiddleText("250个");
        customProgressView.setBottomText("未学习生词50个");
    }
}
