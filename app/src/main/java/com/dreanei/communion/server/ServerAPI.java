package com.dreanei.communion.server;

import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.ServerMessage;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Александр on 24.04.2017.
 */

public interface ServerAPI {

    @GET("service?srv=topforums")
    Call<List<Forum>> topForums( @Query("num") int count );
    @GET("service?srv=topforums")
    Call<List<Forum>> topForums();

    @GET("service?srv=forums")
    Call<List<Forum>> getForums();
    @GET("service?srv=sections")
    Call<List<Section>> getSections();
    @GET("service?srv=topics")
    Call<List<Topic>> getTopics();
    @GET("service?srv=posts")
    Call<List<Post>> getPosts();

    @GET("service?srv=forum")
    Call<Forum> getForum(@Query("forumId") int id);

    @GET("user?srv=ownforums")
    Call<List<Forum>> ownForums( @Query("userid") int userId );

    @GET("service?srv=sections")
    Call<List<Section>> getSections( @Query("forumid") int forumid);

    @GET("service?srv=sections")
    Call<List<Section>> getSections( @Query("forumid") int forumid, @Query("sectionid") int sectionid);

    @GET("service?srv=section")
    Call<Section> getSection(@Query("sectionId") int sectionId);

    @GET("user?srv=gotuser")
    Call<ServerMessage> gotUser(@Query("userId") int userId);

    @GET("user?srv=userinfo")
    Call<User> getUser(@Query("userId") int userId );

    @GET("user?srv=forums")
    Call<List<Forum>> getUserForums(@Query("userId") int userId);

    @GET("service?srv=topics")
    Call<List<Topic>> getTopics( @Query("sectionid") int sectionid);

    @GET("service?srv=posts")
    Call<List<Post>> getPosts(@Query("topicid") int topicid);

    @GET("service?srv=post")
    Call<Post> getPost(@Query("postId") int postId);

    @GET("service?srv=topic")
    Call<Topic> getTopic(@Query("topicId") int topicId);

    @POST("newforum")
    Call<ServerMessage> newForum(@Body Forum forum);
    @POST("newsection")
    Call<ServerMessage> newSection(@Body Section section);
    @POST("newtopic")
    Call<ServerMessage> newTopic(@Body Topic topic);
    @POST("newpost")
    Call<ServerMessage> newPost(@Body Post post);
    @POST("newsuser")
    Call<ServerMessage> newUser(@Body User user);
}
