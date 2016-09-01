package com.game.hall.download.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

public class UGGetResUtil {

	private static UGGetResUtil	getResUtil;
	
	public static UGGetResUtil getInstance(){
		if (getResUtil == null) {
			getResUtil = new UGGetResUtil();
		}
		return getResUtil;
	}
	
	/** 获取本应用的包名 */
	private String getThisAppPackageName(Context context){
		SharedPreferences sp = context.getSharedPreferences(UGConstant.PREF_USER_LOGIN, Context.MODE_PRIVATE);
		String thisAppPackageName = sp.getString("thisAppPackageName", "");
		if (!TextUtils.isEmpty(thisAppPackageName)) {
		} else {
			thisAppPackageName = context.getPackageName();
			Editor editor = sp.edit();
			editor.putString("thisAppPackageName", thisAppPackageName);
			editor.commit();
		}
		return thisAppPackageName;
	}
	
	/** 根据name、type=id找到资源id对应的view */
	public View getViewWithID(Context context, String name, View v){
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "id", getThisAppPackageName(context));
		View view = v.findViewById(resid);
		return view;
	}
	
	/** 根据name、type=drawable找到资源id */
	public int getResidWithDrawable(Context context, String name){
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "drawable", getThisAppPackageName(context));
		return resid;
	}
	
	/** 根据name、type=layout找到资源id */
	public int getResidWithLayout(Context context,String name){
		Resources resources = context.getResources();
		return resources.getIdentifier(name, "layout", getThisAppPackageName(context));
	}
	
	/** 根据name、type=layout得到资源layout对应的view */
	public View getViewWithLayout(Context context, String name){
		Resources resources = context.getResources();
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		int resid = resources.getIdentifier(name, "layout", getThisAppPackageName(context));
		View view = layoutInflater.inflate(resid, null);
		return view;
	}
	
	/** 根据name、type=anim找到资源id */
	public int getResidWithAnim(Context context, String name){
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "anim", getThisAppPackageName(context));
		return resid;
	}
	
	/** 根据name、type=color找到资源id */
	public int getResidWithColor(Context context, String name){
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "color", getThisAppPackageName(context));
		return resid;
	}
	
	/** 根据name、type=style找到资源id */
	public int getResidWithStyle(Context context, String name){
		Resources resources = context.getResources();
		int resid = resources.getIdentifier(name, "style", getThisAppPackageName(context));
		return resid;
	}
	
	/** 根据name、type=string找到资源id */
    public int getResidWithString(Context context, String name)
    {
    	return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }
	
}
