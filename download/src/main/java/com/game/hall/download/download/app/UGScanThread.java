package com.game.hall.download.download.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

import com.game.hall.download.util.UGLog;
import com.game.hall.download.bean.AppBean;

import java.util.List;

public class UGScanThread extends Thread{
    private final int SCAN_INTERVAL = 30 * 1000;
	private final int SCAN_TIME = 3 * 60 * 1000;
	private Context mContext;
	private boolean flag = true;
	private String packageName;
	private String gameId;
	private String rptKey;
	
	public UGScanThread(Context context,String pName,String rptKey,String gameId) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.packageName = pName;
		this.gameId = gameId;
		this.rptKey = rptKey;
	}
	
	@Override
	public void run() {
		int count = 0;
		while(flag){
			if(appIsRun()){
				UGLog.e("---内部扫描检测到appd打开---pkgName = " + packageName);
				AppBean appBean = new AppBean();
				appBean.setApppackagename(packageName);
				appBean.setGameid(gameId);
				appBean.setRptkey(rptKey);
				UGAppReportApp.upSendOpen(appBean, mContext, true);
				
//				SharedPreferences prefRun = mContext.getSharedPreferences(
//						UGConstant.PREFERENCE_RUNING,
//						Context.MODE_PRIVATE);
//				Editor editor = prefRun.edit();
//				editor.putInt(packageName, 0);
//				editor.commit();
				flag = false;
			}else{
				try {
					Thread.sleep(SCAN_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count += SCAN_INTERVAL;
				if(count >= SCAN_TIME){
					flag = false;
				}
			}
		}
		UGLog.e("---内部扫描线程终止--");
	}
	
	/**
	 * 检查
	 * @return
	 */
	private boolean appIsRun(){
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appsInfos = am.getRunningAppProcesses();// 获取运行的程序

		if (appsInfos != null && appsInfos.size() > 0) {
			for (RunningAppProcessInfo runInfo : appsInfos) {
				if(runInfo.processName.equals(packageName)){
					return true;
				}
			}
		}
		return false;
	}
	
}
