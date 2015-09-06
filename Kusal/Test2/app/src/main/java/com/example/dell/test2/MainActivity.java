package com.example.dell.test2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Button btnAddNewCategory;
    private   String label;
    private Spinner spinnerFood;
    private ArrayList<Category> categoriesList;
    ProgressDialog pDialog;
    private String jsonResult,Searchtext;
    private List<ItemList> CustomList;
    private ListView lv;
    private String Listurl = "http://devolopers.webege.com/FilterItems.php";

    private String url = "http://devolopers.webege.com/GetCategories.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 =(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n1 = new Intent(MainActivity.this,DataFromMySQL.class);
                startActivity(n1);
            }
        });

        spinnerFood = (Spinner) findViewById(R.id.spinFood);

        GetCategories n1 = new GetCategories();
        n1.execute(new String[]{url});

        spinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                label = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "You selected: " + label, Toast.LENGTH_LONG).show();

                lv = (ListView)findViewById(R.id.listView2);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Item " + (position + 1) + ": " + CustomList.get(position).getItem_id(),
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);


                        Intent newIntent = new Intent(MainActivity.this,ProductDetails.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Pid",CustomList.get(position).getItem_id());
                        bundle.putString("Pname",CustomList.get(position).getTopic());
                        bundle.putString("Pdescription",CustomList.get(position).getDescription());
                        bundle.putString("Pimage",CustomList.get(position).getPath());
                        newIntent.putExtras(bundle);
                        startActivity(newIntent);
                    }
                });

                StartWebService_LoadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageButton search = (ImageButton)findViewById(R.id.imageButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText search = (EditText)findViewById(R.id.search);
                Searchtext = search.getText().toString();

                JsonReadTask1 n1 = new JsonReadTask1();
                n1.execute(new String[]{Listurl});
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class GetCategories extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching product categories..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
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
            return  null;        }

        @Override
        protected void onPostExecute(String result) {
            ListDrawer();
            pDialog.dismiss();
        }
    }

    private void ListDrawer() {
        categoriesList = new ArrayList<>();
        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonmain = jsonResponse.optJSONArray("categories");

            for(int i=0;i<jsonmain.length();i++)
            {
                JSONObject jsonChild = jsonmain.getJSONObject(i);
                String name = jsonChild.optString("category_id");
                String Description = jsonChild.optString("cat_name");
                Log.e("Name",name);

                categoriesList.add(new Category(name,Description));
            }

        }
        catch (Exception ex)
        {

        }

        List<String> lables = new ArrayList<String>();

  //      txtCategory.setText("");

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getCat_name());

        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerFood.setAdapter(spinnerAdapter);
    }

    public void StartWebService()
    {
        GetCategories n1 = new GetCategories();
        n1.execute(new String[]{url});
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
            MainActivity.this.finish();
        }
        return answer;
    }


    //ListItemData

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Data ....");
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("category",label));

            Searchtext="";
            label="";
            Log.e("label",label);


            HttpClient n1 = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(args[0]);


            try
            {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = n1.execute(httppost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                Log.e("Result",jsonResult);
            }
            catch (Exception ex)
            {

            }
            return  null;
        }

        protected void onPostExecute(String s) {

            GetListItems();
            pDialog.dismiss();
        }
    }
    public void StartWebService_LoadList()
    {
        JsonReadTask n1 = new JsonReadTask();
        n1.execute(new String[]{Listurl});
    }

    private void GetListItems() {
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

        ArrayAdapter adapter = new ProductsAdapter(MainActivity.this,R.layout.list_item,CustomList);
        lv.setAdapter(adapter);
    }

     //add

    private class JsonReadTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Data ....");
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("topic", Searchtext));

            Searchtext="";
            label="";
            Log.e("label",label);


            HttpClient n1 = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(args[0]);


            try
            {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = n1.execute(httppost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                Log.e("Result",jsonResult);
            }
            catch (Exception ex)
            {

            }
            return  null;
        }

        protected void onPostExecute(String s) {

            GetListItems();
            pDialog.dismiss();
        }
    }



}
