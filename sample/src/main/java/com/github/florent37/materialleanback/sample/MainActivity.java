package com.github.florent37.materialleanback.sample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialleanback.MaterialLeanBack;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    MaterialLeanBack materialLeanBack;

    Toolbar toolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
//        setContentView(R.layout.activity_main);
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(0xFFFFFFFF);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
//        mDrawer.setDrawerListener(mDrawerToggle);
//
//        materialLeanBack = (MaterialLeanBack) findViewById(R.id.materialLeanBack);
//
//        materialLeanBack.setCustomizer(new MaterialLeanBack.Customizer() {
//            @Override
//            public void customizeTitle(TextView textView) {
//                textView.setTypeface(null, Typeface.BOLD);
//            }
//        });
//
//        //Picasso.with(getApplicationContext())
//        //        .load("http://www.journaldugeek.com/wp-content/blogs.dir/1/files/2015/01/game-of-thrones-saison-5-documentaire.jpg")
//        //        .fit().centerCrop()
//        //        .into(materialLeanBack.getImageBackground());
//
//        materialLeanBack.setCallAdapter(new MaterialLeanBack.Adapter<TestViewHolder>() {
//            @Override
//            public int getLineCount() {
//                return 10;
//            }
//
//            @Override
//            public int getCellsCount(int line) {
//                return 10;
//            }
//
//            @Override
//            public TestViewHolder onCreateViewHolder(ViewGroup viewGroup, int line) {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_test, viewGroup, false);
//                return new TestViewHolder(view);
//            }
//
//            @Override
//            public void onBindViewHolder(TestViewHolder viewHolder, int i) {
//                viewHolder.textView.setText("test " + i);
//
//                String url = "http://www.lorempixel.com/40" + viewHolder.row + "/40" + viewHolder.cell + "/";
//                Picasso.with(viewHolder.imageView.getContext()).load(url).into(viewHolder.imageView);
//            }
//
////            @Override
////            public String getTitleForRow(int row) {
////                return "Line " + row;
////            }
////
////            //region customView
////            @Override
////            public RecyclerView.ViewHolder getCustomViewForRow(ViewGroup viewgroup, int row) {
////                if (row == 3) {
////                    View view = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.header, viewgroup, false);
////                    return new RecyclerView.ViewHolder(view) {
////                    };
////                } else
////                    return null;
////            }
////
////            @Override
////            public boolean isCustomView(int row) {
////                return row == 3;
////            }
////
////            @Override
////            public void onBindCustomView(RecyclerView.ViewHolder viewHolder, int row) {
////                super.onBindCustomView(viewHolder, row);
////            }
//
//            //endregion
//
//        });
        init();
    }

    private void init() {
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        materialLeanBack = (MaterialLeanBack) findViewById(R.id.materialLeanBack);

//        materialLeanBack.setCustomizer(new MaterialLeanBack.Customizer() {
//            @Override
//            public void customizeTitle(TextView textView) {
//                textView.setTypeface(null, Typeface.BOLD);
//            }
//        });
        MaterialLeanBack.Adapter adapter = new MaterialLeanBack.Adapter<TestViewHolder>() {
            @Override
            public int getLineCount() {
                return 10;
            }

            @Override
            public int getCellsCount(int line) {
                return 10;
            }

            @Override
            public TestViewHolder onCreateViewHolder(ViewGroup viewGroup, int line) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_test, viewGroup, false);
                return new TestViewHolder(view);
            }

            @Override
            public void onBindViewHolder(TestViewHolder viewHolder, int i) {
                viewHolder.textView.setText("test " + i);

                String url = "http://www.lorempixel.com/40" + viewHolder.row + "/40" + viewHolder.cell + "/";
                Picasso.with(viewHolder.imageView.getContext()).load(url).into(viewHolder.imageView);
            }

            @Override
            public String getTitleForRow(int row) {
                return "Line " + row;
            }

            //region customView
            @Override
            public RecyclerView.ViewHolder getCustomViewForRow(ViewGroup viewgroup, int row) {
                if (row == 3) {
                    View view = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.header, viewgroup, false);
                    return new RecyclerView.ViewHolder(view) {
                    };
                } else
                    return null;
            }

            @Override
            public boolean isCustomView(int row) {
                return row == 3;
            }

            @Override
            public void onBindCustomView(RecyclerView.ViewHolder viewHolder, int row) {
                super.onBindCustomView(viewHolder, row);
            }

            //endregion

        };

        final RecyclerView recyclerView = materialLeanBack.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        CellAdapter cellAdapter = new CellAdapter(adapter, new CellAdapter.HeightCalculatedCallback() {
            @Override
            public void onHeightCalculated(int height) {
//                if (!isWrapped()) {
//                    recyclerView.getLayoutParams().height = height;
//                    recyclerView.requestLayout();
//                }
            }

            @Override
            public boolean isWrapped() {
                return false;
            }
        });

        cellAdapter.setOnItemClick(new CellAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                    if (viewHolder instanceof CellViewHolder) {
                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);

                        if (cellViewHolder.getPosition() == position) {
                            cellViewHolder.enlarge(true);
                        } else {
                            cellViewHolder.reduce(true);
                        }
//                        cellViewHolder.newPosition(i);
                    }
                }
            }
        });

        recyclerView.setAdapter(cellAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                    if (viewHolder instanceof CellViewHolder) {
                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);
                        if (cellViewHolder.getPosition() == 1) {
                            cellViewHolder.enlarge(true);
                        } else {
                            cellViewHolder.reduce(true);
                        }
                    }
                }
            }
        });

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }
}
