package com.dreanei.communion;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreanei.communion.fragments.MainFragment;

public class MainActivity extends Activity {

    FrameLayout content;
    private static final int CONTENT_VIEW_ID = 10101010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (FrameLayout) findViewById(R.id.content_frame);

        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        ListView menuListView = (ListView) findViewById(R.id.left_drawer);

        menuListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, menuItems));

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new MainFragment()).commit();

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, ((TextView)view).getText().toString(), Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (((TextView)view).getText().toString().equals("Главная")){
                    ft.replace(R.id.content_frame, new MainFragment()).addToBackStack(null).commit();
                } else
                if (((TextView)view).getText().toString().equals("Профиль")){
                    ft.replace(R.id.content_frame, new ProfileFragment()).addToBackStack(null).commit();
                } else
                if (((TextView)view).getText().toString().equals("Выход")){
                    System.exit(0);
                } else
                    ft.commit();
            }
        });
    }
}
