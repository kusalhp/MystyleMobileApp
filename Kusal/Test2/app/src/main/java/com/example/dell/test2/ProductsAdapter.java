package com.example.dell.test2;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import com.squareup.picasso.Picasso;


/**
 * Created by Dell on 9/1/2015.
 */
public class ProductsAdapter extends ArrayAdapter<ItemList> {

    public ProductsAdapter(Context context, int resource, List<ItemList> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItems holder;
        View listview =null;

        if(listview == null){
            LayoutInflater n1 = LayoutInflater.from(getContext());
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
            listview = n1.inflate(R.layout.list_item,parent,false);
            holder= new ViewHolderItems();
            holder.productName = (TextView)listview.findViewById(R.id.title);
            holder.productDescription =(TextView)listview.findViewById(R.id.textView);
            holder.productImage = (ImageView)listview.findViewById(R.id.icon);
            holder.ratings = (RatingBar)listview.findViewById(R.id.rating);
            listview.setTag(holder);


        }
        else
        {
            holder = (ViewHolderItems)listview.getTag();
        }

        ItemList current = getItem(position);
        holder.productName.setText(current.getTopic());
        holder.productDescription.setText(current.getDescription());
        holder.ratings.setRating(Float.parseFloat(current.getItem_id()));
        String url ="http://devolopers.webege.com/SEP_F/"+current.getPath();
        Log.e("URL",url);
        ImageView img=(ImageView)convertView.findViewById(R.id.icon);
       // Ion.with(holder.productImage).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).load("https://www.google.lk/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0CAcQjRxqFQoTCPf6svuJ2ccCFZJtjgod8NoDFw&url=http%3A%2F%2Fgreen-zebras-contant-viewer.googlecode.com%2Fsvn%2Ftrunk%2Fgeocache%2FGeocache%2Fres%2Fdrawable-hdpi%2F&bvm=bv.101800829,d.c2E&psig=AFQjCNG0Llmui61KVzuBaoNf-KuOJddNtw&ust=1441308292507616");
        Picasso.with(getContext()).load(url).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(holder.productImage);


        return listview;
      }

    static class ViewHolderItems{
        TextView productName,productDescription;
        ImageView productImage;
        RatingBar ratings;

    }



}
