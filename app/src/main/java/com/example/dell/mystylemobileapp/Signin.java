package com.example.dell.mystylemobileapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Signin extends ActionBarActivity {

    int code;
    InputStream is=null;
    String result=null;
    String line=null;
    String Uname;
    String Pward;
    boolean ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        EditText e1 = (EditText)findViewById(R.id.editText);
        Uname = e1.getText().toString();

        EditText e2 = (EditText)findViewById(R.id.editText2);
        Pward = e1.getText().toString();


        Button n3 = (Button)findViewById(R.id.button3);
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent n = new Intent(Signin.this, Home.class);
                startActivity(n);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
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


    public void check()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("Uname",Uname));
        nameValuePairs.add(new BasicNameValuePair("Pward",Pward));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.devolopers.webege.com/MyStyle/SEP_F/login.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");

        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success "+result);
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {
            JSONObject json_data = new JSONObject(result);
            code=(json_data.getInt("code"));

            if(code==1)
            {
                ok=true;
            }
            else
            {
                ok=false;
                Toast.makeText(getBaseContext(), "Username And Password is incorrect",
                        Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }
}
