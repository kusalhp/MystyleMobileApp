
package com.example.dell.test2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
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
import java.net.URL;
import java.util.ArrayList;


public class ProductDetails extends ActionBarActivity {
    String Id,Name,Description,imagepath;
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;
    RatingBar rate;

    int code;
    InputStream is=null;
    String result=null;
    String line=null;
    String Rating;
    boolean ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Log.e("Working", "dd");
        Bundle bundle =getIntent().getExtras();

        Id = bundle.getString("Pid").toString();
        Name = bundle.getString("Pname").toString();
        Description = bundle.getString("Pdescription").toString();
        imagepath = bundle.getString("Pimage").toString();

         Log.e("Id",Id);


        img = (ImageView)findViewById(R.id.imageView);

        new LoadImage().execute("http://devolopers.webege.com/SEP_F/" + imagepath);

        rate = (RatingBar)findViewById(R.id.ratingBar);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

           //     Toast.makeText(ProductDetails.this,
           //             String.valueOf(ratingBar.getRating()),
           //             Toast.LENGTH_SHORT).show();

                 Rating = String.valueOf(ratingBar.getRating());

                new ratingsfeedback().execute();
            }
        });


        Button Commentbtn = (Button)findViewById(R.id.button2);
        Commentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comment = new Intent(ProductDetails.this,ProductComments.class);
                Bundle bundle = new Bundle();
                bundle.putString("Pname",Name);
                comment.putExtras(bundle);
                startActivity(comment);
            }
        });

        final Button Quotaion = (Button)findViewById(R.id.button3);
        Quotaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Quotation = new Intent(ProductDetails.this,RequestQuotation.class);
                startActivity(Quotation);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
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

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductDetails.this);
            pDialog.setMessage("Loading Product Details ....");
            pDialog.show();


        }
        protected Bitmap doInBackground(String... args) {
            try {

                TextView name = (TextView)findViewById(R.id.textView2);
                name.setText(Name);

                TextView desc = (TextView)findViewById(R.id.textView3);
                desc.setText(Description);

                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(ProductDetails.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class ratingsfeedback extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            InsertRating();
            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            Toast.makeText(getApplicationContext(), "Your Ratings Saved Succesfully",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void InsertRating()
    {


        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("id",Id));
        nameValuePairs.add(new BasicNameValuePair("rating",Rating));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://devolopers.webege.com/InsertRating.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            Log.e("asa","asa");
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
                // Toast.makeText(getBaseContext(), "Inserted Successfully",
                //       Toast.LENGTH_SHORT).show();
            }
            else
            {
                ok=false;
                Toast.makeText(getBaseContext(), "Unable to save rating details",
                        Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }
}

