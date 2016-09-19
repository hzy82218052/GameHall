package com.game.hall.gamehall.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.hall.download.bean.AppBean;
import com.game.hall.gamehall.R;
import com.game.hall.gamehall.mode.bean.GameDetail;
import com.game.hall.gamehall.mode.response.GameHallResponse;
import com.game.hall.gamehall.net.RequestManager;
import com.game.hall.gamehall.net.okhttputils.callback.Callback;
import com.game.hall.gamehall.ui.detail.RelatedAdapter;
import com.game.hall.gamehall.utils.CommonUtil;
import com.game.hall.gamehall.utils.GsonUtil;
import com.game.hall.gamehall.utils.LogUtil;
import com.game.hall.gamehall.widget.recycler.MyRecyclerView;
import com.game.hall.gamehall.widget.recycler.cell.CellAdapter;
import com.game.hall.gamehall.widget.recycler.cell.CellViewHolder;
import com.game.hall.gamehall.widget.recycler.cell.GridAdapter;
import com.game.hall.gamehall.widget.recycler.cell.GridViewHolder;
import com.game.hall.gamehall.widget.search.InputKeyView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 搜索页面
 * Created by hezhiyong on 2016/9/17.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    //组件
    private EditText mEdit;
    private ImageView mSearch;
    private InputKeyView keyView;
    private TextView mSearchHint;
    private MyRecyclerView mRecyler;

    //数据
    private List<AppBean> gameDatas = new ArrayList<AppBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_search);

        initView();

        initLinstener();

        initRecycler();
    }


    /**
     * 初始化view
     */
    void initView() {
        mEdit = (EditText) findViewById(R.id.game_edit);
        mSearch = (ImageView) findViewById(R.id.game_search);
        keyView = (InputKeyView) findViewById(R.id.game_key_lay);
        mSearchHint = (TextView) findViewById(R.id.txt_hint);
        mRecyler = (MyRecyclerView) findViewById(R.id.game_search_datas);

        keyView.setOutEdit(mEdit);
        setHint();
    }

    /**
     * 初始化监听
     */
    void initLinstener() {
        mSearch.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    void initRecycler() {
        RelatedAdapter relatedAdapter = new RelatedAdapter(gameDatas);

        GridLayoutManager myLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        mRecyler.setLayoutManager(myLayoutManager);
        mRecyler.setHasFixedSize(true);

        final GridAdapter cellAdapter = new GridAdapter(relatedAdapter, null);

        cellAdapter.setOnItemClick(new GridAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < mRecyler.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = mRecyler.getChildViewHolder(mRecyler.getChildAt(i));
                    if (viewHolder instanceof GridViewHolder) {
                        LogUtil.i("@hzy", "onScrolled---------" + i);

                        GridViewHolder cellViewHolder = ((GridViewHolder) viewHolder);

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

        mRecyler.setAdapter(cellAdapter);

        mRecyler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                    if (viewHolder instanceof GridViewHolder) {
                        Log.i("@hzy", "onScrolled---------" + i);
                        GridViewHolder cellViewHolder = ((GridViewHolder) viewHolder);
                        if (cellViewHolder.getPosition() == 0) {
                            cellViewHolder.enlarge(true);
                        } else {
                            cellViewHolder.reduce(true);
                        }
                    }
                }
            }
        });
    }


    /**
     * 设置提示语句
     */
    void setHint() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<font color = 'blue'>例如：搜索“天天飞车”</font><br>\n")
                .append("<font color = 'blue'>输入</font>")
                .append("<font color = '#FFFF00'>T</font>")
                .append("<font color = 'blue'>ian</font>")
                .append("<font color = '#FFFF00'>T</font>")
                .append("<font color = 'blue'>ian</font>")
                .append("<font color = '#FFFF00'>F</font>")
                .append("<font color = 'blue'>ei</font>")
                .append("<font color = '#FFFF00'>C</font>")
                .append("<font color = 'blue'>he首字母</font>")
                .append("<font color = '#FFFF00'>TTFC</font><br>")
                .append("<font color = 'blue'>即可</font>");
        CharSequence charSequence = Html.fromHtml(stringBuffer.toString());
        mSearchHint.setText(charSequence);
        mSearchHint.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.game_search) {
            String keyword = mEdit.getText().toString();
            requestDatas(keyword);
        }
    }

    void requestDatas(String keyword) {
//        RequestManager.getGameSearch(keyword,new Callback<GameHallResponse>() {
//            @Override
//            public GameHallResponse parseNetworkResponse(Response response, int id) throws Exception {
//                String str = response.body().string();
//                LogUtil.i("@hzy", "search------" + str);
//                return GsonUtil.getInstance().formJson(str, GameHallResponse.class);
//            }
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(GameHallResponse response, int id) {
//                if (response.isSuccess()) {
//                    List<AppBean> list = response.data;
//                    if(list==null)
//                        return;
//                    gameDatas.clear();
//                    gameDatas.addAll(list);
//                    mRecyler.getAdapter().notifyDataSetChanged();
//                }
//            }
//        });
        List<AppBean> datas = new ArrayList<AppBean>();
        for (int i = 0; i < 8; i++) {
            AppBean bean = new AppBean();
            bean.setAppcode(167);
            bean.setAppname("欢乐斗地主" + i);
            bean.setApppackagename("com.qqgame.hlddz");
            bean.setApplink("http://113.107.216.43/imtt.dd.qq.com/16891/2D4C5564FAD16B59F41F2A969DB8685D.apk?mkey=57cad5ae53b64884&f=d688&c=0&fsname=com.qqgame.hlddz_5.12.007_167.apk&csr=4d5s&p=.apk");
            bean.setAppsize("1000000" + i);
            bean.setRptkey("1000" + i);
            datas.add(bean);
        }
        List<AppBean> list = datas;
        if(list==null)
            return;
        gameDatas.clear();
        gameDatas.addAll(list);
        mRecyler.getAdapter().notifyDataSetChanged();

    }
}
