package com.game.hall.gamehall.widget.search;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.hall.gamehall.R;

/**
 * 输入法布局
 * Created by hezhiyong on 2016/9/17.
 */
public class InputKeyView extends FrameLayout {

    private int mode = 0;//0为key，1为num

    //输入布局
    private RelativeLayout lay_key_eg;
    private RelativeLayout lay_key_num;
    //输出
    private EditText outEdit;

    public InputKeyView(Context context) {
        super(context);
        initView();
    }

    public InputKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InputKeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化按钮
     */
    void initView() {
        View.inflate(getContext(), R.layout.game_view_inputkey, this);
        lay_key_eg = (RelativeLayout) findViewById(R.id.lay_key_eg);
        lay_key_num = (RelativeLayout) findViewById(R.id.lay_key_num);

        updateMode(mode);//
        setTextOnClick(0, lay_key_eg);
        setTextOnClick(1, lay_key_num);
    }

    void setTextOnClick(int mode, ViewGroup viewGroup) {
        if (viewGroup != null) {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof TextView) {
                    view.setOnClickListener(mode == 0 ? egClick : numClick);
                } else if (view instanceof ViewGroup) {
                    setTextOnClick(mode, (ViewGroup) view);
                }

            }
        }
    }


    /**
     * 更新模式
     *
     * @param mode
     */
    void updateMode(int mode) {
        this.mode = mode;
        lay_key_num.setVisibility(this.mode == 0 ? View.GONE : View.VISIBLE);
        lay_key_eg.setVisibility(this.mode == 1 ? View.GONE : View.VISIBLE);
    }

    /**
     * 加入输入法
     *
     * @param outEdit
     */
    public void setOutEdit(EditText outEdit) {
        this.outEdit = outEdit;
        //禁止输入法
        outEdit.setInputType(InputType.TYPE_NULL);
    }

    //英文字母监听
    private OnClickListener egClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.key_123:
                    updateMode(1);
                    break;
                case R.id.key_del:
                    delTxt();
                    break;
                default:
                    outTxt(((TextView) v).getText().toString());
                    break;
            }
        }
    };

    //数字监听
    private OnClickListener numClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.key_ABC:
                    updateMode(0);
                    break;
                case R.id.key_del_2:
                    delTxt();
                    break;
                default:
                    outTxt(((TextView) v).getText().toString());
                    break;
            }
        }
    };

    /**
     * 加入输出字符
     *
     * @param key
     */
    private void outTxt(String key) {
        String out = outEdit.getText().toString();
        StringBuffer stringBuffer = new StringBuffer(TextUtils.isEmpty(out) ? "" : out);
        stringBuffer.append(key);
        outEdit.setText(stringBuffer.toString());
    }


    /**
     * 删除尾部字符
     */
    private void delTxt() {
        String out = outEdit.getText().toString();
        StringBuffer stringBuffer = new StringBuffer(TextUtils.isEmpty(out) ? "" : out);
        if (stringBuffer.length() == 0)
            return;
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        outEdit.setText(stringBuffer.toString());
    }

    /**
     * 清除所有字符
     */
    private void cleanTxt() {
        outEdit.setText("");
    }


}
