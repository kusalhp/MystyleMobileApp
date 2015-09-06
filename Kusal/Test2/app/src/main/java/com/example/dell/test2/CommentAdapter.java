package com.example.dell.test2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dell on 9/5/2015.
 */
public class CommentAdapter extends ArrayAdapter<CommentList> {
    public CommentAdapter(Context context, int resource, List<CommentList> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItems holder;
        View listview =null;

        if(listview == null){
            LayoutInflater n1 = LayoutInflater.from(getContext());
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.commentlist_item,parent,false);
            listview = n1.inflate(R.layout.commentlist_item,parent,false);
            holder= new ViewHolderItems();
            holder.Name = (TextView)listview.findViewById(R.id.title);
            holder.Comment =(TextView)listview.findViewById(R.id.textView);
            holder.productImage = (ImageView)listview.findViewById(R.id.icon);
            listview.setTag(holder);


        }
        else
        {
            holder = (ViewHolderItems)listview.getTag();
        }

        CommentList current = getItem(position);
        holder.Name.setText(current.getName());
        holder.Comment.setText(current.getComment());
        //String url ="http://devolopers.webege.com/SEP_F/"+current.getPath();
        //Log.e("URL", url);
        //ImageView img=(ImageView)convertView.findViewById(R.id.icon);
        // Ion.with(holder.productImage).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).load("https://www.google.lk/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0CAcQjRxqFQoTCPf6svuJ2ccCFZJtjgod8NoDFw&url=http%3A%2F%2Fgreen-zebras-contant-viewer.googlecode.com%2Fsvn%2Ftrunk%2Fgeocache%2FGeocache%2Fres%2Fdrawable-hdpi%2F&bvm=bv.101800829,d.c2E&psig=AFQjCNG0Llmui61KVzuBaoNf-KuOJddNtw&ust=1441308292507616");
       // Picasso.with(getContext()).load(url).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(holder.productImage);
        return listview;
    }

    static class ViewHolderItems{
        TextView Name,Comment;
        ImageView productImage;


    }

}
