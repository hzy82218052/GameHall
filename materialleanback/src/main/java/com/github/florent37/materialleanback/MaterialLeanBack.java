package com.github.florent37.materialleanback;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialleanback.cell.CellAdapter;
import com.github.florent37.materialleanback.cell.CellViewHolder;
import com.github.florent37.materialleanback.line.LineAdapter;
import com.github.florent37.materialleanback.line.LineViewHolder;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by florentchampigny on 28/08/15.
 */
public class MaterialLeanBack extends FrameLayout {

    RecyclerView recyclerView;
    ImageView imageBackground;
    View imageBackgroundOverlay;

    MaterialLeanBack.Adapter adapter;
    MaterialLeanBack.Customizer customizer;
    MaterialLeanBackSettings settings = new MaterialLeanBackSettings();

    public MaterialLeanBack(Context context) {
        super(context);
    }

    public MaterialLeanBack(Context context, AttributeSet attrs) {
        super(context, attrs);
        settings.handleAttributes(context, attrs);
    }

    public MaterialLeanBack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settings.handleAttributes(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addView(LayoutInflater.from(getContext()).inflate(R.layout.mlb_layout, this, false));

        imageBackground = (ImageView) findViewById(R.id.mlb_imageBackground);
        imageBackgroundOverlay = findViewById(R.id.mlb_imageBackgroundOverlay);

        if (settings.backgroundId != null)
            imageBackground.setBackgroundDrawable(getContext().getResources().getDrawable(settings.backgroundId));

        if (settings.backgroundOverlay != null)
            ViewHelper.setAlpha(imageBackgroundOverlay,settings.backgroundOverlay);
        if (settings.backgroundOverlayColor != null)
            imageBackgroundOverlay.setBackgroundColor(settings.backgroundOverlayColor);

        recyclerView = (RecyclerView) findViewById(R.id.mlb_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public static abstract class Adapter<VH extends MaterialLeanBack.ViewHolder> {
        public int getLineCount() {
            return 0;
        }

        public int getCellsCount(int row) {
            return 0;
        }

        public VH onCreateViewHolder(ViewGroup viewGroup, int row) {
            return null;
        }

        public void onBindViewHolder(VH viewHolder, int i) {
        }

        public String getTitleForRow(int row) {
            return null;
        }

        public boolean isCustomView(int row) {
            return false;
        }

        //if you want to set a custom view into the MaterialLeanBack, eg: a header
        public RecyclerView.ViewHolder getCustomViewForRow(ViewGroup viewGroup, int row) {
            return null;
        }

        public void onBindCustomView(RecyclerView.ViewHolder viewHolder, int row) {}
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(new LineAdapter(settings,adapter,customizer));
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    protected int position = 1;
    protected boolean wrapped = false;
    public void setCallAdapter(Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        CellAdapter cellAdapter = new CellAdapter(0, adapter, settings, new CellAdapter.HeightCalculatedCallback() {
            @Override
            public void onHeightCalculated(int height) {
                if(!wrapped) {
                    recyclerView.getLayoutParams().height = height;
                    recyclerView.requestLayout();
                    wrapped = true;
                }
            }
        });

        cellAdapter.setOnItemClick(new CellAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                    if (viewHolder instanceof CellViewHolder) {
                        Log.i("@hzy","onScrolled---------"+i);

                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);

                        if(cellViewHolder.getPosition()==position){
                            cellViewHolder.enlarge(true);
                        }else{
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
                        Log.i("@hzy","onScrolled---------"+i);
                        CellViewHolder cellViewHolder = ((CellViewHolder) viewHolder);
                        if(cellViewHolder.getPosition()==position){
                            cellViewHolder.enlarge(true);
                        }else{
                            cellViewHolder.reduce(true);
                        }
                    }
                }
            }
        });
    }

    public static class ViewHolder {

        public int row;
        public int cell;
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

    }

    public interface Customizer {
        void customizeTitle(TextView textView);
    }

    public Customizer getCustomizer() {
        return customizer;
    }

    public void setCustomizer(Customizer customizer) {
        this.customizer = customizer;
    }

    public ImageView getImageBackground() {
        return imageBackground;
    }
}
