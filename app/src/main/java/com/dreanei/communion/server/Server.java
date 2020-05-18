package com.dreanei.communion.server;

import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.ServerMessage;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Александр on 16.05.2017.
 */

public class Server {
    private static final String URI = "https://communion.herokuapp.com/api/";

    private Gson gson;
    private Retrofit retrofit;
    private ServerAPI serverAPI;
    {
        gson = new GsonBuilder()
            .setLenient()
            .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serverAPI = retrofit.create(ServerAPI.class);
    }

    private static Server instance;
    static {
        instance = new Server();
    }

    private Server(){

    }

    public static Server getInstance(){
        return instance;
    }

    public void gotUser(int userId, Callback<ServerMessage> callback){
        Call<ServerMessage> call = serverAPI.gotUser(userId);
        call.enqueue(callback);
    }

    public void getForums(Callback<List<Forum>> callback){
        Call<List<Forum>> call = serverAPI.getForums();
        call.enqueue(callback);
    }

    public void getSections(Callback<List<Section>> callback){
        Call<List<Section>> call = serverAPI.getSections();
        call.enqueue(callback);
    }

    public void getTopics(Callback<List<Topic>> callback){
        Call<List<Topic>> call = serverAPI.getTopics();
        call.enqueue(callback);
    }

    public void getPosts(Callback<List<Post>> callback){
        Call<List<Post>> call = serverAPI.getPosts();
        call.enqueue(callback);
    }

    public void getUser(int userid, Callback<User> callback){
        Call<User> call = serverAPI.getUser(userid);
        call.enqueue(callback);
    }

    public void getForum(int id, Callback<Forum> callback){
        Call<Forum> call = serverAPI.getForum(id);
        call.enqueue(callback);
    }

    public void getPost(int id, Callback<Post> callback){
        Call<Post> call = serverAPI.getPost(id);
        call.enqueue(callback);
    }

    public void topForums(Callback<List<Forum>> callback){
        Call<List<Forum>> call = serverAPI.topForums();
        call.enqueue(callback);
    }

    public void topForums(int count, Callback<List<Forum>> callback){
        Call<List<Forum>> call = serverAPI.topForums(count);
        call.enqueue(callback);
    }

    public void newForum(Forum forum, Callback<ServerMessage> callback){
        Call<ServerMessage> call = serverAPI.newForum(forum);
        call.enqueue(callback);
    }
    public void newSection(Section section, Callback<ServerMessage> callback){
        Call<ServerMessage> call = serverAPI.newSection(section);
        call.enqueue(callback);
    }
    public void newTopic(Topic topic, Callback<ServerMessage> callback){
        Call<ServerMessage> call = serverAPI.newTopic(topic);
        call.enqueue(callback);
    }
    public void newPost(Post post, Callback<ServerMessage> callback){
        Call<ServerMessage> call = serverAPI.newPost(post);
        call.enqueue(callback);
    }

    public void getSections(int forumid, Callback<List<Section>> callback){
        Call<List<Section>> call = serverAPI.getSections(forumid);
        call.enqueue(callback);
    }

    public void getSections(int forumid, int sectionid, Callback<List<Section>> callback){
        Call<List<Section>> call = serverAPI.getSections(forumid, sectionid);
        call.enqueue(callback);
    }

    public void getSection(int sectionId, Callback<Section> callback){
        Call<Section> call = serverAPI.getSection(sectionId);
        call.enqueue(callback);
    }

    public void getTopic(int topicId, Callback<Topic> callback){
        Call<Topic> call = serverAPI.getTopic(topicId);
        call.enqueue(callback);
    }

    public void getTopics(int sectionid, Callback<List<Topic>> callback){
        Call<List<Topic>> call = serverAPI.getTopics(sectionid);
        call.enqueue(callback);
    }

    public void getPosts(int topicId, Callback<List<Post>> callback){
        Call<List<Post>> call = serverAPI.getPosts(topicId);
        call.enqueue(callback);
    }

    public void newUser(User user, Callback<ServerMessage> callback){
        Call<ServerMessage> call = serverAPI.newUser(user);
        call.enqueue(callback);
    }
}
