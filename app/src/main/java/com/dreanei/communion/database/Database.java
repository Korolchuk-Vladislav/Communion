package com.dreanei.communion.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.models.Update;
import com.dreanei.communion.models.Updates;
import com.dreanei.communion.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static com.dreanei.communion.CommunionApp.TAG;
import static com.dreanei.communion.CommunionApp.database;

/**
 * Created by Александр on 23.05.2017.
 */

public class Database {
    private static Database instance;
    private Database(Context context){
        local = Local.getInstance(context);
    }

    public static Database getInstance(Context context) {
        if (instance == null) instance = new Database(context);
        return instance;
    }

    public Local local;
    public Remote remote;
    {
        remote = Remote.getInstance();
    }

    public void update(Updates updates){
        local.update(updates);
    }

    public String[] getSectionsTitles(Forum forum){
        List<Section> sections =  getSections(forum);
        String[] res = new String[sections.size()];
        for (int i = 0; i < sections.size(); i++){
            res[i] = sections.get(i).getName();
        }
        return res;
    }

    public String[] getSectionsTitles(Forum forum, Section section){
        List<Section> sections =  getSections(forum, section);
        String[] res = new String[sections.size()];
        for (int i = 0; i < sections.size(); i++){
            res[i] = sections.get(i).getName();
        }
        return res;
    }

    public void getSections(Forum forum, Callback<List<Section>> callback){
        if (remote.isConnected()){
            remote.getSections(forum, callback);
        }
    }

    public List<Section> getSections(Forum forum){
        return local.getSections(forum);
    }

    public void getSections(Forum forum, Section section, Callback<List<Section>> callback){
        if (remote.isConnected()) {
            remote.getSubSections(forum, section, callback);
        }
    }

    public List<Section> getSections(Forum forum, Section section){
        return (ArrayList<Section>) local.getSubSections(forum, section);
    }

    public void getTopics(Section section, Callback<List<Topic>> topicCallback) {
        if (remote.isConnected()) {
            remote.getTopics(section, topicCallback);
        }
    }

    public List<Topic> getTopics(Section section) {
        return (ArrayList<Topic>) local.getTopics(section);
    }

    public void getPosts(Topic topic, Callback<List<Post>> callback) {
        if (remote.isConnected()) {
            remote.getPosts(topic, callback);
        }
    }

    public void getUser(int usrid, Callback<User> callback){
        if (remote.isConnected()) {
            remote.getUser(usrid, callback);
        }
    }

    public List<Post> getPosts(Topic topic) {
        return (ArrayList<Post>) local.getPosts(topic);
    }

    public int getLastUpdateId(){
        return ((local.getUpdatesCount() > 0) ? local.getLastUpdate().getId() : 0);
    }

    public Update getLastUpdate(){
        return local.getLastUpdate();
    }

    public int getUpdatesCount(){
        return local.getUpdatesCount();
    }

    public Section getSection(int id) {
        return local.getSection(id);
    }
}
