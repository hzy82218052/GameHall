
package com.zhucai.example.graphicsdemo.main;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhucai.example.graphicsdemo.ColorFilterActivity;
import com.zhucai.example.graphicsdemo.DrawBitmapMeshActivity;
import com.zhucai.example.graphicsdemo.DrawBitmapMeshMagicLampActivity;
import com.zhucai.example.graphicsdemo.DrawPathGlassBrokenActivity;
import com.zhucai.example.graphicsdemo.MatrixPolyToPolyActivity;
import com.zhucai.example.graphicsdemo.PixelChangeActivity;
import com.zhucai.example.graphicsdemo.R;
import com.zhucai.example.graphicsdemo.R.id;
import com.zhucai.example.graphicsdemo.R.layout;
import com.zhucai.example.graphicsdemo.R.menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    static class DemoItem {
        public String title;
        public Intent intent;

        public DemoItem(String _title, Intent _intent) {
            this.title = _title;
            this.intent = _intent;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        List<DemoItem> demoItems = new ArrayList<DemoItem>();

        demoItems.add(new DemoItem(DrawBitmapMeshActivity.TITLE,
                Intent.makeMainActivity(new ComponentName(this, DrawBitmapMeshActivity.class))));
        demoItems.add(new DemoItem(DrawBitmapMeshMagicLampActivity.TITLE,
                Intent.makeMainActivity(new ComponentName(this, DrawBitmapMeshMagicLampActivity.class))));
        demoItems.add(new DemoItem(PixelChangeActivity.TITLE,
                Intent.makeMainActivity(new ComponentName(this, PixelChangeActivity.class))));
        demoItems.add(new DemoItem(DrawPathGlassBrokenActivity.TITLE,
                Intent.makeMainActivity(new ComponentName(this, DrawPathGlassBrokenActivity.class))));
        demoItems.add(new DemoItem(MatrixPolyToPolyActivity.TITLE,
                Intent.makeMainActivity(new ComponentName(this, MatrixPolyToPolyActivity.class))));
        demoItems.add(new DemoItem(ColorFilterActivity.TITLE,
                Intent.makeMainActivity(new ComponentName(this, ColorFilterActivity.class))));

        listView.setAdapter(new ArrayAdapter<DemoItem>(this, R.layout.list_view_item, demoItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final DemoItem item = (DemoItem)getItem(position);
                TextView view = (TextView)super.getView(position, convertView, parent);
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        startActivity(item.intent);
                    }
                });
                return view;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
