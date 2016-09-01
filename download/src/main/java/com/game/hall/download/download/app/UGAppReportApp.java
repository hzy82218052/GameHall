package com.game.hall.download.download.app;

import android.content.Context;

import com.game.hall.download.bean.AppBean;
import com.game.hall.download.download.conf.UGAppConf;


public class UGAppReportApp
{
	public UGAppReportApp()
	{
		;
	}
	
	/**
	 * 上报下载请求数据
	 * 
	 * @param app
	 * @param first "true"第一次上报
	 * @param context
	 */
	public static void upSendDown(AppBean app, final Context context, boolean first)
	{
		//再次下载的时候，先前已经上报的app要清除激活上报标志
//		SharedPreferences prefRun = context.getSharedPreferences(UGConstant.PREFERENCE_RUNING, Context.MODE_PRIVATE);
//		Editor editor = prefRun.edit();
//		editor.remove(app.getApppackagename()).commit();
				
//		final UGSendBean bean = new UGSendBean();
//		bean.setGameid(app.getGameid());
//		bean.setPackagename(app.getApppackagename());
//		bean.setRptkey(app.getRptkey());
//		bean.setClickstatus(UGConstant.EVENT_DOWN_REQ);
//		UGLog.e("---上报下载请求---rptKey=" + bean.getRptkey() + " gameid =" + bean.getGameid());
//
//		List<UGSendBean> list = new ArrayList<UGSendBean>();
//		list.add(bean);
//		UGSendInfo.getIntance().gameClick(null, context, list,first);
	}

	/**
	 * 上报下载成功
	 * 
	 * @param conf
	 * @param context
	 * @param first
	 */
	public static void upsendDownSucess(UGAppConf conf, final Context context, boolean first)
	{
//		UGSendBean appBean = new UGSendBean();
//		appBean.setGameid(conf.gameId);
//		appBean.setPackagename(conf.packageName);
//		appBean.setRptkey(conf.rptKey);
//		appBean.setClickstatus(UGConstant.EVENT_DOWN_SUC);
//		UGLog.e("---上报下载成功---rptKey = " + appBean.getRptkey() +  " gameid =" + appBean.getGameid());
//
//		List<UGSendBean> list = new ArrayList<UGSendBean>();
//		list.add(appBean);
//
//		UGSendInfo.getIntance().gameClick(null, context, list,first);
	}

	/**
	 * 上报升级点击数据
	 * 
	 * @param appBean
	 * @param context
	 * @param first
	 */
	public static void upSendUpdate(AppBean appBean, final Context context,boolean first)
	{
		//再次下载的时候，先前已经上报的app要清除激活上报标志
//		SharedPreferences prefRun = context.getSharedPreferences(UGConstant.PREFERENCE_RUNING, Context.MODE_PRIVATE);
//		Editor editor = prefRun.edit();
//		editor.remove(appBean.getApppackagename()).commit();
//
//		final UGSendBean bean = new UGSendBean();
//		bean.setGameid(appBean.getGameid());
//		bean.setPackagename(appBean.getApppackagename());
//		bean.setRptkey(appBean.getRptkey());
//		bean.setClickstatus(UGConstant.EVENT_DOWN_REQ);
//		UGLog.e("---上报升级---rptKey=" + bean.getRptkey() +  " gameid =" + bean.getGameid());
//		List<UGSendBean> list = new ArrayList<UGSendBean>();
//		list.add(bean);
//
//		UGSendInfo.getIntance().gameClick(null, context, list,first);
	}

	/**
	 * 上报安装
	 * 
	 * @param context
	 * @param conf
	 * @param first
	 */
	public static void upSendInstall(final Context context, UGAppConf conf,boolean first)
	{
//		UGSendBean appBean = new UGSendBean();
//		appBean.setGameid(conf.gameId);
//		appBean.setPackagename(conf.packageName);
//		appBean.setRptkey(conf.rptKey);
//		appBean.setClickstatus(UGConstant.EVENT_INSTALL_SUC);
//		UGLog.e("---上报安装成功---rptKey = " + appBean.getRptkey() + " gameid =" + appBean.getGameid());
//
//		List<UGSendBean> list = new ArrayList<UGSendBean>();
//		list.add(appBean);
//		UGSendInfo.getIntance().gameClick(null, context, list,first);
	}

	/**
	 * 上报打开点击数据
	 * @param appBean
	 * @param context
	 * @param first
	 */
	public static void upSendOpen(final AppBean appBean, final Context context,boolean first)
	{
//		final UGSendBean bean = new UGSendBean();
//		bean.setGameid(appBean.getGameid());
//		bean.setPackagename(appBean.getApppackagename());
//		bean.setRptkey(appBean.getRptkey());
//		bean.setClickstatus(UGConstant.EVENT_OPEN);
//		UGLog.e("---上报打开--- rptKey = " + bean.getRptkey() + " gameid =" + bean.getGameid());
//		List<UGSendBean> list = new ArrayList<UGSendBean>();
//		list.add(bean);
//
//		UGSendInfo.getIntance().gameClick(null, context, list, first);
	}
	
}
