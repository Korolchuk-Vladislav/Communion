package com.dreanei.communion.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreanei.communion.CommunionApp;
import com.dreanei.communion.R;
import com.dreanei.communion.adapters.PostsAdapter;
import com.dreanei.communion.database.Database;
import com.dreanei.communion.database.Remote;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends Fragment implements Callback<List<Post>>{

    private Topic topic;
    private List<Post> posts;
    private List<User> users;
    private ListView postsList;
    private PostsAdapter postsAdapter;
    private int userscount;

    private Database database;

    private Callback<User> usersCallback;

    public TopicFragment() {
        setTopic(Topic.NO_TOPIC);
        posts = new ArrayList<>();
        users = new ArrayList<>();
        database = CommunionApp.database;
        usersCallback = new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                users.add(response.body());
                if (users.size() == posts.size()){
                    fillList();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(TopicFragment.this.getActivity(), "error getting post's author", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public TopicFragment setTopic(Topic topic){
        this.topic = topic;
        return this;
    }

    public Topic getTopic(){
        return topic;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.current_topic)).setText(topic.getTitle());
        postsList = ((ListView)view.findViewById(R.id.postslist_topic));
        ((Button) view.findViewById(R.id.writepost_topic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new WritePostFragment().setTopic(topic))
                        .addToBackStack(null).commit();
            }
        });

        if (topic.getTopicid() <= 0 || !Remote.getInstance().isConnected())
            ((Button) view.findViewById(R.id.writepost_topic)).setVisibility(View.INVISIBLE);

        //filling list
        if (Remote.getInstance().isConnected()) {
            database.getPosts(topic,this);
        }
        else {
            posts = database.getPosts(topic);
        }
        postsList.setAdapter(postsAdapter);
    }

    public void fillList(){
        User[] usersArr = new User[users.size()];
        Post[] postsArr = new Post[posts.size()];
        for(int i = 0; i < posts.size(); i++){
            usersArr[i] = users.get(i);
            postsArr[i] = posts.get(i);
        }
        postsAdapter = new PostsAdapter(this.getActivity(),postsArr, usersArr);
        postsList.setAdapter(postsAdapter);
    }

    @Override
    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        posts = response.body();
        for (Post post:posts){
            database.getUser(post.getUserid(),usersCallback);
        }
    }

    @Override
    public void onFailure(Call<List<Post>> call, Throwable t) {
        Toast.makeText(this.getActivity(), "error getting posts", Toast.LENGTH_SHORT).show();
    }
}
