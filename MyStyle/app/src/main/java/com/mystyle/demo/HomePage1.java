package com.mystyle.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;


public class HomePage1 extends Activity {

    ViewPager viewPager;
    swipeAD adapter;
    String user;
    ImageButton loyaty,offers,Map,about,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page1);


        viewPager = (ViewPager) findViewById(R.id.view_Pager);
        adapter = new swipeAD(this);
        viewPager.setAdapter(adapter);
        viewPager.setAdapter(adapter);
        loyaty= (ImageButton) findViewById(R.id.btnLoyal);
        offers= (ImageButton) findViewById(R.id.btnMenu);
        Map = (ImageButton) findViewById(R.id.btnHistory);
        about = (ImageButton) findViewById(R.id.btnAbout);
        contact= (ImageButton)  findViewById(R.id.btnContact);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
