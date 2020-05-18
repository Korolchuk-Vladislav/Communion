package com.dreanei.communion.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Александр on 24.05.2017.
 */

class DBHeler extends SQLiteOpenHelper {

    private static final String DROP_TABLE_UPDATES = "drop table if exists updates;";
    private static final String CREATE_TABLE_UPDATES = "create TABLE " +
            "updates(id integer not null, " +
            "table_name text not null, " +
            "idRow integer not null);";
    private static final String DROP_TABLE_FORUMS = "drop table if exists forums;";
    private static final String CREATE_TABLE_FORUMS = "create table if not exists " +
            "forums(forumid integer primary key, userid integer, title text, image text, ispublic integer);";
    private static final String DROP_TABLE_SECTIONS = "drop table if exists sections;";
    private static final String CREATE_TABLE_SECTIONS = "create table if not exists " +
            "sections(sectionid integer, forumid integer, name text, parent int);";
    private static final String DROP_TABLE_TOPICS = "drop table if exists topics;";
    private static final String CREATE_TABLE_TOPICS = "create table if not exists " +
            "topics(topicid integer, userid integer, sectionid integer, title text, updated text);";
    private static final String DROP_TABLE_POSTS = "drop table if exists posts;";
    private static final String CREATE_TABLE_POSTS = "create table if not exists " +
            "posts(postid integer, userid integer, topicid integer, postname text, post text, postdata text);";

    private static final String DROP_TABLE_LSECTIONS = "drop table if exists lsections;";
    private static final String CREATE_TABLE_LSECTIONS = "create table if not exists " +
            "lsections(sectionid integer primary key AUTOINCREMENT, forumid integer, name text, parent int);";
    private static final String DROP_TABLE_LTOPICS = "drop table if exists ltopics;";
    private static final String CREATE_TABLE_LTOPICS = "create table if not exists " +
            "ltopics(topicid integer primary key AUTOINCREMENT, userid integer, sectionid integer, title text, updated text);";
    private static final String DROP_TABLE_LPOSTS = "drop table if exists lposts;";
    private static final String CREATE_TABLE_LPOSTS = "create table if not exists " +
            "lposts(postid integer primary key AUTOINCREMENT, userid integer, topicid integer, postname text, post text, postdata text);";

    public static final String[] Tables = new String[]{"updates","forums","sections","topics","posts"};
    public DBHeler(Context context){
        super(context,"communiondb",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dropAllTables(db);
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropAllTables(db);
        createTables(db);
    }

    public void dropAllTables(SQLiteDatabase database){
        database.execSQL(DROP_TABLE_UPDATES);
        database.execSQL(DROP_TABLE_FORUMS);
        database.execSQL(DROP_TABLE_SECTIONS);
        database.execSQL(DROP_TABLE_TOPICS);
        database.execSQL(DROP_TABLE_POSTS);
        database.execSQL(DROP_TABLE_LSECTIONS);
        database.execSQL(DROP_TABLE_LTOPICS);
        database.execSQL(DROP_TABLE_LPOSTS);
    }

    public void createTables(SQLiteDatabase database){
        database.execSQL(CREATE_TABLE_UPDATES);
        database.execSQL(CREATE_TABLE_FORUMS);
        database.execSQL(CREATE_TABLE_SECTIONS);
        database.execSQL(CREATE_TABLE_TOPICS);
        database.execSQL(CREATE_TABLE_POSTS);
        database.execSQL(CREATE_TABLE_LSECTIONS);
        database.execSQL(CREATE_TABLE_LTOPICS);
        database.execSQL(CREATE_TABLE_LPOSTS);
    }
}
