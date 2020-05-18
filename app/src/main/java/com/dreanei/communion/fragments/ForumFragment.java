package com.dreanei.communion.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dreanei.communion.CommunionApp;
import com.dreanei.communion.R;
import com.dreanei.communion.database.Database;
import com.dreanei.communion.database.Remote;
import com.dreanei.communion.models.Forum;
import com.dreanei.communion.models.Section;
import com.dreanei.communion.models.Topic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dreanei.communion.CommunionApp.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment implements Callback<List<Section>>{

    private Forum forum;
    private Section section;

    private ListView sections;
    private ListView topics;

    private ArrayList<Section> sectionsList;
    private ArrayList<Topic> topicsList;

    private Database database;

    private ArrayAdapter<String> sectionsAdapter;
    private ArrayAdapter<String> topicsAdapter;

    Callback<List<Topic>> topicCallback;

    public ForumFragment setForum(Forum forum) {
        this.forum = forum;
        return this;
    }

    public Forum getForum() {
        return forum;
    }

    public ForumFragment setSection(Section section) {
        this.section = section;
        return this;
    }

    public Section getSection() {
        return section;
    }

    public ForumFragment(){
        database = CommunionApp.database;
        setForum(Forum.EMPTY);
        setSection(Section.NO_SECTION);
        topicCallback = new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.body().size() == 0){
                    topicsList = new ArrayList<>();
                    topicsList.add(Topic.NO_TOPIC);
                    String[] topicss = new String[topicsList.size()];
                    for (int i = 0; i < topicsList.size(); i++) {
                        topicss[i] = topicsList.get(i).getTitle();
                        Log.d(TAG, "onResponse: " + topicsList.get(i).getTitle());
                    }
                    topicsAdapter = new ArrayAdapter<String>(ForumFragment.this.getActivity(),
                            android.R.layout.simple_list_item_1, topicss);
                    Log.d(TAG, "onResponse: sections count = 0");
                }
                else {
                    topicsList = (ArrayList<Topic>) response.body();
                    String[] topicss = new String[topicsList.size()];
                    for (int i = 0; i < topicsList.size(); i++) {
                        topicss[i] = topicsList.get(i).getTitle();
                        Log.d(TAG, "onResponse: " + topicsList.get(i).getTitle());
                    }
                    topicsAdapter = new ArrayAdapter<String>(ForumFragment.this.getActivity(),
                            android.R.layout.simple_list_item_1, topicss);
                }
                topics.setAdapter(topicsAdapter);
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                topicsAdapter = new ArrayAdapter<String>(ForumFragment.this.getActivity(),
                        android.R.layout.simple_list_item_1,new String[]{"no topics"});
                Log.d(TAG, "onFailure: no sections");
                topics.setAdapter(topicsAdapter);
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sections = (ListView) view.findViewById(R.id.sections_list);
        topics = (ListView) view.findViewById(R.id.topics_list);
        ((TextView) view.findViewById(R.id.current_forum))
                .setText(forum.getTitle() + ((section.getSectionid() > 0) ? " / " + section.getSectionid() : ""));
        //create buttons initialization
        ((Button) view.findViewById(R.id.createsection_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new CreateSectionFragment().setForum(forum).setSection(section))
                        .addToBackStack(null).commit();
            }
        });
        ((Button) view.findViewById(R.id.createtopic_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new CreateTopicFragment().setForum(forum).setSection(section))
                        .addToBackStack(null).commit();
            }
        });

        if (Remote.getInstance().isConnected()){
            view.findViewById(R.id.createtopic_button).setVisibility(View.VISIBLE);
        } else {
            //view.findViewById(R.id.createtopic_button).setVisibility(View.INVISIBLE);
        }

        //filling lists
        if (Remote.getInstance().isConnected()) {
            if (section == Section.NO_SECTION) {
                database.getSections(forum, this);
                ((Button) view.findViewById(R.id.createtopic_button)).setVisibility(View.INVISIBLE);
            } else {
                database.getSections(forum, section, this);
                ((Button) view.findViewById(R.id.createtopic_button)).setVisibility(View.VISIBLE);
                database.getTopics(section, topicCallback);
            }
            sections.setAdapter(sectionsAdapter);
            sections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new ForumFragment()
                            .setForum(forum).setSection(sectionsList.get(i))).addToBackStack(null).commit();
                }
            });
            topics.setAdapter(topicsAdapter);
            topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new TopicFragment().setTopic(topicsList.get(i)))
                            .addToBackStack(null).commit();
                }
            });
        } else {
            //((Button) view.findViewById(R.id.createsection_button)).setVisibility(View.INVISIBLE);
            if (section == Section.NO_SECTION) {
                ((Button) view.findViewById(R.id.createtopic_button)).setVisibility(View.INVISIBLE);
                sectionsList = (ArrayList<Section>) database.getSections(forum);
                sectionsList.addAll(database.local.getLSections(forum));
                String[] sectionTitles = new String[sectionsList.size()];
                for(int i = 0; i < sectionTitles.length; i++){
                    sectionTitles[i] = sectionsList.get(i).getName();
                }
                sectionsAdapter = new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_list_item_1, sectionTitles);
            } else {
                sectionsList = (ArrayList<Section>) database.getSections(forum, section);
                sectionsList.addAll(database.local.getSubLSections(forum,section));
                sectionsAdapter = new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_list_item_1, database.getSectionsTitles(forum, section));
                topicsList = (ArrayList<Topic>) database.getTopics(section);
                topicsList.addAll(database.local.getLTopics(section));
                String[] titles = new String[topicsList.size()];
                for(int i = 0; i < titles.length;i++){
                    titles[i] = topicsList.get(i).getTitle();
                }
                topicsAdapter = new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_list_item_1, titles);
                topics.setAdapter(topicsAdapter);
            }
            sections.setAdapter(sectionsAdapter);
            sections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (sectionsList.get(i).getSectionid() > 0) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new ForumFragment()
                                .setForum(forum).setSection(sectionsList.get(i))).addToBackStack(null).commit();
                    }
                }
            });
            topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (topicsList.get(i).getTopicid() > 0 || !Remote.getInstance().isConnected()) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new TopicFragment().setTopic(topicsList.get(i)))
                                .addToBackStack(null).commit();
                    }
                }
            });
        }
    }

    @Override
    public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
        if (response.body().size() == 0){
            sectionsList = new ArrayList<>();
            sectionsList.add(Section.NO_SECTION);
            String[] sectionss = new String[sectionsList.size()];
            for (int i = 0; i < sectionsList.size(); i++) {
                sectionss[i] = sectionsList.get(i).getName();
                Log.d(TAG, "onResponse: " + sectionsList.get(i).getName());
            }
            sectionsAdapter = new ArrayAdapter<String>(ForumFragment.this.getActivity(),
                    android.R.layout.simple_list_item_1, sectionss);
            Log.d(TAG, "onResponse: sections count = 0");
        }
        else {
            sectionsList = (ArrayList<Section>) response.body();
            String[] sectionss = new String[sectionsList.size()];
            for (int i = 0; i < sectionsList.size(); i++) {
                sectionss[i] = sectionsList.get(i).getName();
                Log.d(TAG, "onResponse: " + sectionsList.get(i).getName());
            }
            sectionsAdapter = new ArrayAdapter<String>(ForumFragment.this.getActivity(),
                    android.R.layout.simple_list_item_1, sectionss);
        }
        sections.setAdapter(sectionsAdapter);
    }

    @Override
    public void onFailure(Call<List<Section>> call, Throwable t) {
        sectionsAdapter = new ArrayAdapter<String>(ForumFragment.this.getActivity(),
                android.R.layout.simple_list_item_1,new String[]{"no sections"});
        Log.d(TAG, "onFailure: no sections");
        sections.setAdapter(sectionsAdapter);
    }
}
