package com.example.dell.test2;

/**
 * Created by Dell on 9/1/2015.
 */
public class ItemList {

    private String topic,description,path,item_id;


    public ItemList(String topic, String description, String path) {
        this.topic = topic;
        this.description = description;
        this.path = path;
    }

    public ItemList(String topic, String description, String path, String item_id) {
        this.topic = topic;
        this.description = description;
        this.path = path;
        this.item_id = item_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
