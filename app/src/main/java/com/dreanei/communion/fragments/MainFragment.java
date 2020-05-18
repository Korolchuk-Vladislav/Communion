package com.dreanei.communion.fragments;


import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dreanei.communion.R;

import java.util.ArrayList;
import java.util.List;

import com.dreanei.communion.adapters.ForumsAdapter;
import com.dreanei.communion.database.Local;
import com.dreanei.communion.database.Remote;
import com.dreanei.communion.models.Forum;
import com.dreanei.communion.server.Server;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements Callback<List<Forum>>{

    public ListView forums;
    ForumsAdapter forumsAdapter;
    ArrayList<Forum> forumslist;

    public MainFragment() {
        forumslist = new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        forums = ((ListView) view.findViewById(R.id.forumslist_main));
        (view.findViewById(R.id.towebsite_main)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString = "https://communion.herokuapp.com";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    MainFragment.this.getActivity().startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    MainFragment.this.getActivity().startActivity(intent);
                }
            }
        });
        forums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int forumId = (forumslist.get(i)).getForumid();
                if (forumId > 0) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new ForumFragment()
                            .setForum(forumslist.get(i))).addToBackStack(null).commit();
                }
            }
        });
        if (Remote.getInstance().isConnected()) {
            ForumsAdapter forumsAdapter = new ForumsAdapter(this.getActivity(), new Forum[]{Forum.WAIT});
            forums.setAdapter(forumsAdapter);
            Server.getInstance().topForums(this);
        }
        else{
            forumslist = (ArrayList<Forum>)Local.getInstance(getActivity().getApplicationContext()).getForums();
            Forum[] res = new Forum[forumslist.size()];
            for(int i = 0; i < forumslist.size(); i++)
                res[i] = forumslist.get(i);
            forumsAdapter = new ForumsAdapter(this.getActivity(),res);
            forums.setAdapter(forumsAdapter);
        }
    }

    @Override
    public void onResponse(Call<List<Forum>> call, Response<List<Forum>> response) {
        forumslist = (ArrayList<Forum>) response.body();
        Forum[] fs = new Forum[response.body().size()];
        if(response.isSuccessful()) {
            fs = response.body().toArray(fs);
            forumsAdapter = new ForumsAdapter(this.getActivity(), fs);
        } else {
            forumsAdapter = new ForumsAdapter(this.getActivity(),new Forum[]{Forum.NO_FORUMS});
        }
        forums.setAdapter(forumsAdapter);
    }

    @Override
    public void onFailure(Call<List<Forum>> call, Throwable t) {
        ForumsAdapter forumsAdapter = new ForumsAdapter(this.getActivity(),new Forum[]{Forum.NO_FORUMS});
        forums.setAdapter(forumsAdapter);
    }
}
