package com.dreanei.communion.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dreanei.communion.CommunionApp;
import com.dreanei.communion.R;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.ServerMessage;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.server.Server;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WritePostFragment extends Fragment {

    private Topic topic;

    public WritePostFragment() {
        setTopic(Topic.NO_TOPIC);
    }

    public WritePostFragment setTopic(Topic topic){
        this.topic = topic;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_post, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.submit_write);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.getInstance().newPost(new Post(-1, CommunionApp.uid, topic.getTopicid(),
                        ((EditText) view.findViewById(R.id.postname_write)).getText().toString(),
                        ((EditText) view.findViewById(R.id.post_write)).getText().toString()
                ), new Callback<ServerMessage>() {
                    @Override
                    public void onResponse(Call<ServerMessage> call, Response<ServerMessage> response) {
                        Toast.makeText(getActivity(), response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new TopicFragment().setTopic(topic)).commit();
                    }

                    @Override
                    public void onFailure(Call<ServerMessage> call, Throwable t) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new TopicFragment().setTopic(topic)).commit();
                    }
                });
            }
        });
    }
}
