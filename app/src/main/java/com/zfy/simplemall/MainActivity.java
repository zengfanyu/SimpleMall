package com.zfy.simplemall;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;

    //1.使用FragmentTabHost需要Activity继承FragmentActivity，AppCompatActivity已经继承了FragmentActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);

        //2.使用FragmentTabHost一定要调用setup（）方法
        mTabHost.setup(this,getSupportFragmentManager(),R.id.real_tab_content);

        //3.往使用FragmentTabHost一定要调用setup中去添加TabSpec
    }
}
