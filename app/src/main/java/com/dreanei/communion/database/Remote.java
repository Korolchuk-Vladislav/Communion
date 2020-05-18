package com.dreanei.communion.database;

import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.models.User;
import com.dreanei.communion.server.Server;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by Александр on 23.05.2017.
 */

public class Remote {
    private static Remote instance;
    static {
        instance = new Remote();
    }

    private boolean connected;
    private Server server;
    {
        connected = false;
        server = Server.getInstance();
    }

    private Remote(){

    }

    public static Remote getInstance() {
        return instance;
    }

    public boolean isConnected(){
        return connected;
    }
    public void connected() { connected = true; }
    public void disconnected() { connected = false; }

    public void getUser(int userId, Callback<User> callback){
        server.getUser(userId, callback);
    }

    public void getSections(Forum forum, Callback<List<Section>> callback){
        server.getSections(forum.getForumid(),callback);
    }

    public void getSubSections(Forum forum, Section section, Callback<List<Section>> callback){
        server.getSections(forum.getForumid(), section.getSectionid(), callback);
    }

    public void getTopics(Section section, Callback<List<Topic>> callback){
        server.getTopics(section.getSectionid(),callback);
    }

    public void getPosts(Topic topic, Callback<List<Post>> callback){
        server.getPosts(topic.getTopicid(),callback);
    }

}
