package com.dreanei.communion.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreanei.communion.CommunionApp;
import com.dreanei.communion.R;
import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.ServerMessage;
import com.dreanei.communion.models.Topic;
import com.dreanei.communion.server.Server;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dreanei.communion.CommunionApp.TAG;
import static com.dreanei.communion.CommunionApp.database;
import static com.dreanei.communion.CommunionApp.user;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTopicFragment extends Fragment {

    Forum forum;
    Section section;

    public CreateTopicFragment() {
        // Required empty public constructor
    }

    public CreateTopicFragment setForum(Forum forum){
        this.forum = forum;
        return this;
    }

    public CreateTopicFragment setSection(Section section){
        this.section = section;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_topic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((Button) view.findViewById(R.id.createtopic_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                Log.d(TAG, "onClick: new Topic: uid=" + user.getUserid());
                final Topic newTopic = new Topic(-1,CommunionApp.uid,
                        section.getSectionid(),
                        ((TextView)CreateTopicFragment.this.getView().findViewById(R.id.createtopic_name)).getText().toString(),
                        new Date().toString());
                Server.getInstance().newTopic(newTopic, new Callback<ServerMessage>() {
                    @Override
                    public void onResponse(Call<ServerMessage> call, Response<ServerMessage> response) {
                        if (response.body().getStatus().equals("exception")){
                            Log.d(TAG, "exception: " + response.body().getMessage() + "\nuserid: " +  newTopic.getUserid()
                                + "\ntitle: " + newTopic.getTitle()
                                + "\nsection: " + newTopic.getSectionid());
                            Toast.makeText(CreateTopicFragment.this.getActivity(),
                                    "Зарегистрируйтесь, через вэб-приложение, чтобы создавать темы", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new ForumFragment().setForum(forum).setSection(section))
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<ServerMessage> call, Throwable t) {
                        database.local.newLTopic(newTopic);
                    }
                });

            }
        });
    }
}
