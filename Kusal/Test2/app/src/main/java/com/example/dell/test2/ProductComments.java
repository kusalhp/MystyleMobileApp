package com.example.dell.test2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ProductComments extends ActionBarActivity {

    private String jsonResult;
    ProgressDialog pDialog;
    private List<CommentList> CustomList;
    private ListView lv;
    private String url = "http://devolopers.webege.com/GetComments.php";
    private String name,Nname,Ncomment;

    int code;
    InputStream is=null;
    String result=null;
    String line=null;
    boolean ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_comments);
        lv = (ListView)findViewById(R.id.listView3);

        Bundle bundle =getIntent().getExtras();

        name = bundle.getString("Pname").toString();

        StartWebService_comments();

        Button submit = (Button)findViewById(R.id.button5);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name = (EditText)findViewById(R.id.editText3);
                Nname = name.getText().toString();

                EditText comment = (EditText)findViewById(R.id.editText4);
                Ncomment = comment.getText().toString();

                if(Nname.equals("") && Ncomment.equals(""))
                {
                      Toast.makeText(getApplicationContext(), "Comment And Name Fields Cannot Be Null",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    new commentfeedback().execute();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_comments, menu);
        return true;

    }

    public void StartWebService_comments()
    {
        JsonReadTask n1 = new JsonReadTask();
        n1.execute(new String[]{url});
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


    //Method to get retrieve data from the server as json result
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductComments.this);
            pDialog.setMessage("Loading Data ....");
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            HttpClient n1 = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(args[0]);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name",name));

            try
            {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = n1.execute(httppost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            }
            catch (Exception ex)
            {

            }
            return  null;
        }

        protected void onPostExecute(String s) {

            ListDrawer();
            pDialog.dismiss();
        }
    }

     // Generating List View For Comments Under Selected Product
    private void ListDrawer() {
        CustomList = new ArrayList<>();
        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonmain = jsonResponse.optJSONArray("comments");

            for(int i=0;i<jsonmain.length();i++)
            {
                JSONObject jsonChild = jsonmain.getJSONObject(i);
                String name = jsonChild.optString("name");
                String comment = jsonChild.optString("comment");

                CustomList.add(new CommentList(name,comment));
            }

        }
        catch (Exception ex)
        {

        }

        ArrayAdapter adapter = new CommentAdapter(ProductComments.this,R.layout.commentlist_item,CustomList);
        lv.setAdapter(adapter);
    }

    private StringBuilder inputStreamToString(InputStream n1){
        String line="";
        StringBuilder answer=new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(n1));
        try
        {
            while((line= br.readLine())!=null )
            {
                answer.append(line);
            }
        }
        catch (Exception ex)
        {
            ProductComments.this.finish();
        }
        return answer;
    }


    //Adding New Comment
    public void InsertCommnets()
    {


        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("topic",name));
        nameValuePairs.add(new BasicNameValuePair("name",Nname));
        nameValuePairs.add(new BasicNameValuePair("comment",Ncomment));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://devolopers.webege.com/AddComment.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            Log.e("asa", "asa");
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

    private class commentfeedback extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            InsertCommnets();
            return null;
        }

        @Override
        protected void onPostExecute(String message) {

            Toast.makeText(getApplicationContext(), "Thank You For Your Feedback",
                    Toast.LENGTH_LONG).show();
            StartWebService_comments();
        }
    }


}

