package com.dreanei.communion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreanei.communion.R;
import com.dreanei.communion.async.DownloadImageTask;
import com.dreanei.communion.models.Post;
import com.dreanei.communion.models.User;

/**
 * Created by Александр on 24.05.2017.
 */

public class PostsAdapter extends BaseAdapter {

    Context context;
    Post[] data;
    private static LayoutInflater inflater = null;
    User[] user;

    public PostsAdapter(Context context, Post[] data, User[] user) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.user = user;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.post_row, null);
        TextView post = (TextView) vi.findViewById(R.id.post_postrow);
        TextView postname = (TextView) vi.findViewById(R.id.postname_postrow);
        TextView username = (TextView) vi.findViewById(R.id.username_postrow);
        TextView date = (TextView) vi.findViewById(R.id.date_postrow);
        ImageView image = (ImageView) vi.findViewById(R.id.image_postrow);
        username.setText(user[position].getFirst_name() + " " + user[position].getLast_name());
        postname.setText(data[position].getPostName());
        post.setText(data[position].getPost());
        date.setText(data[position].getPostDate());
        new DownloadImageTask(image).execute(user[position].getPhoto());
        return vi;
    }
}
