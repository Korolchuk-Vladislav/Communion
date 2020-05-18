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
import com.dreanei.communion.database.Remote;
import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.ServerMessage;
import com.dreanei.communion.server.Server;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dreanei.communion.CommunionApp.TAG;
import static com.dreanei.communion.CommunionApp.database;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateSectionFragment extends Fragment {

    Forum forum;
    Section section;

    public CreateSectionFragment() {
        // Required empty public constructor
    }

    public CreateSectionFragment setForum(Forum forum){
        this.forum = forum;
        return this;
    }

    public CreateSectionFragment setSection(Section section){
        this.section = section;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_section, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((Button) view.findViewById(R.id.createsection_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Section newSection;
                if (section.getSectionid() > 0){
                    newSection = new Section(-1,forum.getForumid(),
                            ((TextView)CreateSectionFragment.this.getView()
                                    .findViewById(R.id.createsection_name)).getText().toString(),
                            section.getSectionid());
                }
                else {
                    newSection = new Section(-1,forum.getForumid(),
                            ((TextView)CreateSectionFragment.this.getView()
                                    .findViewById(R.id.createsection_name)).getText().toString(),
                            -1);
                }
                if (database.remote.isConnected()){
                    Server.getInstance().newSection(newSection, new Callback<ServerMessage>() {
                        @Override
                        public void onResponse(Call<ServerMessage> call, Response<ServerMessage> response) {
                            Log.d(TAG, "section created with response: " + response.body().getMessage());
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new ForumFragment().setForum(forum).setSection(section))
                                    .commit();
                        }

                        @Override
                        public void onFailure(Call<ServerMessage> call, Throwable t) {
                            Toast.makeText(CreateSectionFragment.this.getActivity(), "error creating section", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    database.local.newLSection(newSection);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new ForumFragment().setForum(forum).setSection(section))
                            .commit();
                }
            }
        });
    }
}
