package com.mystyle.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mystyle.demo.custom.CustomActivity;

/**
 * Created by Administrator on 8/26/2015.
 */
public class home extends CustomActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
     //   startActivity(new Intent(home.this, UserList.class));
        setTouchNClick(R.id.cht_btn);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        startActivity(new Intent(this, UserList.class));
    }
}
