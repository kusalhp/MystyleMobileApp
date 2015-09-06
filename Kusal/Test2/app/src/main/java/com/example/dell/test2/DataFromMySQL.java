package com.example.dell.test2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DataFromMySQL extends ActionBarActivity {

    private String jsonResult;
    ProgressDialog pDialog;
    private List<ItemList> CustomList;
    private ListView lv;
    private String url = "http://devolopers.webege.com/ItemList.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_from_my_sql);
        lv = (ListView)findViewById(R.id.listView);

        StartWebService();
 //       ConnectivityManager cr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
 //       NetworkInfo n1 = cr.getActiveNetworkInfo();

 //       boolean connected = n1 != null && n1.isConnectedOrConnecting();

 //       if(connected)
 //       {
 //           if(n1.getType() == ConnectivityManager.TYPE_WIFI||n1.getType() == ConnectivityManager.TYPE_MOBILE)
 //           {

  //          }
  //      }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Item " + (position + 1) + ": " + CustomList.get(position).getItem_id(),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);


                Intent newIntent = new Intent(DataFromMySQL.this,ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("Pid",CustomList.get(position).getItem_id());
                bundle.putString("Pname",CustomList.get(position).getTopic());
                bundle.putString("Pdescription",CustomList.get(position).getDescription());
                bundle.putString("Pimage",CustomList.get(position).getPath());
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_from_my_sql, menu);
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

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DataFromMySQL.this);
            pDialog.setMessage("Loading Data ....");
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            HttpClient n1 = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(args[0]);

            try
            {
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
    public void StartWebService()
    {
       JsonReadTask n1 = new JsonReadTask();
        n1.execute(new String[]{url});
    }

    private void ListDrawer() {
       CustomList = new ArrayList<>();
        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonmain = jsonResponse.optJSONArray("items");

            for(int i=0;i<jsonmain.length();i++)
            {
                JSONObject jsonChild = jsonmain.getJSONObject(i);
                String name = jsonChild.optString("topic");
                String Description = jsonChild.optString("description");
                String path = jsonChild.optString("path");
                String item_id = jsonChild.optString("item_id");

                CustomList.add(new ItemList(name,Description,path,item_id));
            }

        }
        catch (Exception ex)
        {

        }

        ArrayAdapter adapter = new ProductsAdapter(DataFromMySQL.this,R.layout.list_item,CustomList);
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
            DataFromMySQL.this.finish();
        }
        return answer;
    }


}
