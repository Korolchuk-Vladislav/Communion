package com.dreanei.communion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.ServerMessage;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.models.Update;
import com.dreanei.communion.models.Updates;
import com.dreanei.communion.server.Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dreanei.communion.CommunionApp.TAG;

public class Local {
    private static Local instance;

    private SQLiteDatabase db;
    private DBHeler dbHeler;

    private Local(Context context){
        dbHeler = new DBHeler(context);
        db = dbHeler.getWritableDatabase();
    }

    public static Local getInstance(Context context) {
        if (instance == null) instance = new Local(context);
        return instance;
    }

    public void update(Updates updates){
        Server server = Server.getInstance();
        for (Update update : updates.getUpdates()) {
            newUpdate(update);
            switch (update.getTable()){
                case "forums":
                    server.getForum(update.getIdRow(), new Callback<Forum>() {
                        @Override
                        public void onResponse(Call<Forum> call, Response<Forum> response) {
                            newForum(response.body());
                        }

                        @Override
                        public void onFailure(Call<Forum> call, Throwable t) {

                        }
                    });
                    break;
                case "sections":
                    server.getSection(update.getIdRow(), new Callback<Section>() {
                        @Override
                        public void onResponse(Call<Section> call, Response<Section> response) {
                            newSection(response.body());
                        }

                        @Override
                        public void onFailure(Call<Section> call, Throwable t) {

                        }
                    });
                    break;
                case "topics":
                    server.getTopic(update.getIdRow(), new Callback<Topic>() {
                        @Override
                        public void onResponse(Call<Topic> call, Response<Topic> response) {
                            newTopic(response.body());
                        }

                        @Override
                        public void onFailure(Call<Topic> call, Throwable t) {

                        }
                    });
                    break;
                case "posts":
                    server.getPost(update.getIdRow(), new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            newPost(response.body());
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
            }
        }
    }

    public List<Update> updates(){
        ArrayList<Update> updates = new ArrayList<>();
        Cursor cursor = db.query("updates",null,null,null,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()) {
                updates.add(new Update(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }
        }
        return updates;
    }

    public List<Forum> getForums(){
        ArrayList<Forum> forums = new ArrayList<>();
        Cursor cursor = db.query("forums",null,null,null,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()) {
                forums.add(new Forum(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
                        cursor.getString(3),cursor.getInt(4)));
            }
        }
        return forums;
    }

    public void newUpdate(Update update){
        Log.d(TAG, "newUpdate: " + update.toString());
        ContentValues values = new ContentValues();
        values.put("id",update.getId());
        values.put("table_name",update.getTable());
        values.put("idRow",update.getIdRow());
        db.insert("updates",null,values);
    }

    public void newForum(Forum forum){
        Log.d(TAG, "newForum: "+forum.toString());
        ContentValues values = new ContentValues();
        values.put("forumid",forum.getForumid());
        values.put("userid",forum.getUserid());
        values.put("title",forum.getTitle());
        values.put("image",forum.getImg());
        values.put("ispublic",forum.getPublic());
        db.insert("forums",null,values);
    }

    public void newSection(Section section){
        Log.d(TAG, "newLSection: " + section.toString());
        ContentValues values = new ContentValues();
        values.put("sectionid",section.getSectionid());
        values.put("forumid",section.getForumid());
        values.put("name",section.getName());
        values.put("parent",section.getParent());
        db.insert("sections",null,values);
    }

    public void newLSection(Section section){
        Log.d(TAG, "newLSection: " + section.toString());
        ContentValues values = new ContentValues();
        //values.put("sectionid",section.getSectionid());
        values.put("forumid",section.getForumid());
        values.put("name",section.getName());
        values.put("parent",section.getParent());
        db.insert("lsections",null,values);
    }

    public void newTopic(Topic topic){
        Log.d(TAG, "newTopic: " + topic.toString());
        ContentValues values = new ContentValues();
        values.put("topicid",topic.getTopicid());
        values.put("userid",topic.getUserid());
        values.put("sectionid",topic.getSectionid());
        values.put("title",topic.getTitle());
        values.put("updated",new Date().toString());
        db.insert("topics",null,values);
    }

    public void newLTopic(Topic topic){
        Log.d(TAG, "newLTopic: " + topic.toString());
        ContentValues values = new ContentValues();
        values.put("topicid",topic.getTopicid());
        values.put("userid",topic.getUserid());
        values.put("sectionid",topic.getSectionid());
        values.put("title",topic.getTitle());
        values.put("updated",new Date().toString());
        db.insert("ltopics",null,values);
    }

    public void newPost(Post post){
        Log.d(TAG, "newPost: " + post.toString());
        ContentValues values = new ContentValues();
        values.put("postid",post.getTopicid());
        values.put("userid",post.getUserid());
        values.put("topicid",post.getTopicid());
        values.put("postname",post.getPostName());
        values.put("post",post.getPost());
        values.put("postdata",post.getPostDate());
        db.insert("posts",null,values);
    }

    public void newLPost(Post post){
        Log.d(TAG, "newLPost: " + post.toString());
        ContentValues values = new ContentValues();
        values.put("postid",post.getTopicid());
        values.put("userid",post.getUserid());
        values.put("topicid",post.getTopicid());
        values.put("postname",post.getPostName());
        values.put("post",post.getPost());
        values.put("postdata",post.getPostDate());
        db.insert("lposts",null,values);
    }

    public List<Section> getSections(Forum forum){
        ArrayList<Section> res = new ArrayList<>();
        Cursor cursor = db.query("sections",null,"forumid=? and parent<1",
                new String[]{forum.getForumid().toString()},null,null,null);
        while (cursor.moveToNext()){
            res.add(new Section(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3)));
        }
        return res;
    }

    public List<Section> getSubSections(Forum forum, Section section){
        ArrayList<Section> res = new ArrayList<>();
        Cursor cursor = db.query("sections",null,"forumid=? and parent=?",
                new String[]{forum.getForumid().toString(),section.getSectionid().toString()},null,null,null);
        while (cursor.moveToNext()){
            res.add(new Section(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3)));
        }
        return res;
    }

    public List<Topic> getTopics(Section section){
        ArrayList<Topic> res = new ArrayList<>();
        Cursor cursor = db.query("topics",null,"sectionid=?",
                new String[]{section.getSectionid().toString()},null,null,null);
        while (cursor.moveToNext()){
            res.add(new Topic(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4)));
        }
        return res;
    }

    public List<Post> getPosts(Topic topic) {
        ArrayList<Post> res = new ArrayList<>();
        Cursor cursor = db.query("posts",null,"topicid=?",
                new String[]{topic.getTopicid().toString()},null,null,null);
        while (cursor.moveToNext()){
            res.add(new Post(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        return res;
    }

    public int getUpdatesCount(){
        Cursor cursor = db.rawQuery("select count(*) from updates",null);
        cursor.moveToNext();
        Log.d(TAG, "getUpdatesCount: "+cursor.getInt(0));
        return cursor.getInt(0);
    }

    public Update getLastUpdate() {
        Cursor cursor = db.query("updates",null,null,null,null,null,"id","1");
        cursor.moveToNext();
        Update res = new Update(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
        Log.d(TAG, "getLastUpdate: " + res.toString());
        return res;
    }

    public void addUpdate(Updates updates){
        for (Update update :updates.getUpdates()) {

        }
    }

    public Section getSection(int id) {
        Cursor cursor = db.query("sections",null,"sectionId=?",
                new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToNext();
        return new Section(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3));
    }

    Callback<ServerMessage> callback = new Callback<ServerMessage>() {
        @Override
        public void onResponse(Call<ServerMessage> call, Response<ServerMessage> response) {

        }

        @Override
        public void onFailure(Call<ServerMessage> call, Throwable t) {

        }
    };

    public void send() {
        Cursor cursor;
        cursor = db.rawQuery("select count(*) from lsections",null);
        cursor.moveToNext();
        Log.d(TAG, "send: sections to push: " + cursor.getInt(0));
        cursor = db.rawQuery("select * from lsections",null);
        while (cursor.moveToNext()){
            Log.d(TAG, "send: section");
            Server.getInstance().newSection(
                    new Section(
                            cursor.getInt(0),cursor.getInt(1),cursor.getString(2), cursor.getInt(3)
                    ), callback
            );
        }
        cursor = db.rawQuery("select count(*) from ltopics",null);
        cursor.moveToNext();
        Log.d(TAG, "send: topics to push: " + cursor.getInt(0));
        cursor = db.rawQuery("select * from ltopics",null);
        while (cursor.moveToNext()){
            Log.d(TAG, "send: topic");
            Server.getInstance().newTopic(
                    new Topic(
                            cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),
                            cursor.getString(4)
                    ), callback
            );
        }
        cursor = db.rawQuery("select count(*) from lposts",null);
        cursor.moveToNext();
        Log.d(TAG, "send: posts to push: " + cursor.getInt(0));
        cursor = db.rawQuery("select * from lposts",null);
        while (cursor.moveToNext()){
            Log.d(TAG, "send: post");
            Server.getInstance().newPost(
                    new Post(
                            cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),
                            cursor.getString(4)
                    ), callback
            );
        }
    }

    public void clearLocal() {
        Log.d(TAG, "clearLocal");
        db.execSQL("delete from lsections;");
        db.execSQL("delete from ltopics;");
        db.execSQL("delete from lposts;");
    }

    public List<Section> getLSections(Forum forum) {
        ArrayList<Section> res = new ArrayList<>();
        Cursor cursor = db.query("lsections",null,"forumid=? and parent<1",
                new String[]{forum.getForumid().toString()},null,null,null);
        while (cursor.moveToNext()){
            res.add(new Section(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3)));
        }
        return res;
    }

    public List<Section> getSubLSections(Forum forum, Section section){
        ArrayList<Section> res = new ArrayList<>();
        Cursor cursor = db.query("lsections",null,"forumid=? and parent=?",
                new String[]{forum.getForumid().toString(),section.getSectionid().toString()},null,null,null);
        while (cursor.moveToNext()){
            res.add(new Section(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3)));
        }
        return res;
    }

    public List<Topic> getLTopics(Section section){
        ArrayList<Topic> res = new ArrayList<>();
        Cursor cursor = db.query("ltopics",null,"sectionid=?",
                new String[]{section.getSectionid().toString()},null,null,null);
        while (cursor.moveToNext()){
            res.add(new Topic(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4)));
        }
        return res;
    }
}
