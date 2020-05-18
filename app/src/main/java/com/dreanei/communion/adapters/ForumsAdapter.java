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
import com.dreanei.communion.models.Forum;

/**
 * Created by Александр on 24.05.2017.
 */

public class ForumsAdapter extends BaseAdapter {

    Context context;
    Forum[] data;
    private static LayoutInflater inflater = null;

    public ForumsAdapter(Context context, Forum[] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
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
            vi = inflater.inflate(R.layout.forum_row, null);
        TextView title = (TextView) vi.findViewById(R.id.title_forumrow);
        ImageView image = (ImageView) vi.findViewById(R.id.image_forumrow);
        title.setText(data[position].getTitle());
        new DownloadImageTask(image).execute(data[position].getImg());
        return vi;
    }
}