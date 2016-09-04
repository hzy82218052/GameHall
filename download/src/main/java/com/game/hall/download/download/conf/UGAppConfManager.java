package com.game.hall.download.download.conf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.game.hall.download.bean.AppBean;
import com.game.hall.download.download.app.UGAppReportApp;
import com.game.hall.download.download.db.UGAppConfMgmt;
import com.game.hall.download.download.db.UGDatabaseDefine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UGAppConfManager {
    private static UGAppConfManager mManager = null;
    public static final int MAX_DOWN_NUM = 2;
    /**
     * 字典序列 key为"包，版本号"
     */
    private Map<String, UGAppConf> mAppDirector = new HashMap<String, UGAppConf>();
    /**
     * 初始列表
     */
    private List<UGAppConf> mInitList = new ArrayList<UGAppConf>();
    /**
     * 等待下载列表
     */
    private List<UGAppConf> mWaitList = new ArrayList<UGAppConf>();
    /**
     * 正在下载列表
     */
    private List<UGAppConf> mDowningList = new ArrayList<UGAppConf>();
    /**
     * 暂停下载列表
     */
    private List<UGAppConf> mPauseList = new ArrayList<UGAppConf>();
    /**
     * 升级下载列表
     */
    private List<UGAppConf> mUpdateList = new ArrayList<UGAppConf>();
    /**
     * 已下载列表
     */
    private List<UGAppConf> mFinishList = new ArrayList<UGAppConf>();
    /**
     * 已安装列表
     */
    private List<UGAppConf> mInstallList = new ArrayList<UGAppConf>();

    private PackageReceiver mReceiver = new PackageReceiver();
    private boolean mInited = false;

    private static String TAG = "AppConfManager";

//	private ArrayList<OnChangeUpdate> dmListeners = new ArrayList<UGAppConfManager.OnChangeUpdate>();
//	public void setOnChangeUpdate(OnChangeUpdate dmListener){
//		getManager().dmListeners.add(dmListener);
//	}
//	public void removeOnChangeUpdate(OnChangeUpdate dmListener){
//		getManager().dmListeners.remove(dmListener);
//	}

//	public interface OnChangeUpdate{
//		void onChangeUpdate();
//	}

    private UGAppConfManager() {
        ;
    }

    public synchronized static UGAppConfManager getManager() {
        if (mManager == null) {
//			Log.i(TAG, "getManager");
            mManager = new UGAppConfManager();
        }

        return mManager;
    }

    /**
     * 初始化
     *
     * @param context
     * @param isStartPause "true"启动上次退出时暂停的下载任务
     */
    public synchronized void init(Context context, boolean isStartPause) {
        if (mInited) {
            return;
        }

//		Log.i(TAG, "init");
        // 注册安装和卸载的监听
        IntentFilter filter = new IntentFilter();
        filter.setPriority(100);
        filter.addDataScheme("package");
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        context.getApplicationContext().registerReceiver(mReceiver, filter);

        initWait(context);
        initDowning(context);
        initPause(context);
        initFinish(context);
        initInstall(context);
        if (isStartPause) {
            startPause(context);
        }
//		updateNotify();
        mInited = true;
    }


    /**
     * 注册下载监听
     */
    public UGAppConf register(AppBean appBean) {
//		if (appBean == null || TextUtils.isEmpty(appBean.getApppackagename()) || appBean.getAppcode() <= 0 ||
//				TextUtils.isEmpty(appBean.getApplink()) || Long.parseLong(appBean.getAppsize()) < 0 ||
//				TextUtils.isEmpty(appBean.getRptkey()))
        if (appBean == null || TextUtils.isEmpty(appBean.getApppackagename()) || appBean.getAppcode() <= 0 ||
                TextUtils.isEmpty(appBean.getApplink()) || Long.parseLong(appBean.getAppsize()) < 0 ||
                TextUtils.isEmpty(appBean.getRptkey()))

        {
            return null;
        }

        String key = appBean.getApppackagename() + "," + appBean.getAppcode();
        UGAppConf conf = null;
        if (mAppDirector.containsKey(key)) {
            conf = mAppDirector.get(key);
            conf.versionName = appBean.getAppversion();
            conf.url = appBean.getApplink();
            conf.fileSize = Long.parseLong(appBean.getAppsize());
            conf.gameId = appBean.getGameid();
            conf.appName = appBean.getAppname();
            conf.appIcon = appBean.getAppicon();
            conf.rptKey = appBean.getRptkey();
        } else {
            boolean flag = false;
            /** 判断是否为升级 */
            Iterator<UGAppConf> iter = mInstallList.iterator();
            while (iter.hasNext()) {
                conf = iter.next();
                if (appBean.getApppackagename().equals(conf.packageName) && appBean.getAppcode() > conf.versionCode) {
                    flag = true;
                    break;
                }
            }

            conf = new UGAppConf();
            conf.gameId = appBean.getGameid();
            conf.packageName = appBean.getApppackagename();
            conf.versionName = appBean.getAppversion();
            conf.versionCode = (int) appBean.getAppcode();
            conf.url = appBean.getApplink();
            conf.fileSize = Long.parseLong(appBean.getAppsize());
            conf.appName = appBean.getAppname();
            conf.appIcon = appBean.getAppicon();
            conf.rptKey = appBean.getRptkey();
            if (!flag) {
                conf.state = UGAppConf.STATE_INIT;
                mInitList.add(conf);
            } else {
                conf.state = UGAppConf.STATE_UPDATE;
                mUpdateList.add(conf);
//				updateNotify();
            }
            mAppDirector.put(key, conf);
        }
        return conf;
    }

    public UGAppConf register(String gameId, String packageName, String versionName, int versionCode, String url, long fileSize, Object extObj) {
        if (TextUtils.isEmpty(packageName) || versionCode <= 0 || TextUtils.isEmpty(url) || fileSize < 0) {
            return null;
        }

        String key = packageName + "," + versionCode;
        UGAppConf conf = null;
        if (mAppDirector.containsKey(key)) {
            conf = mAppDirector.get(key);
            conf.versionName = versionName;
            conf.url = url;
            conf.fileSize = fileSize;
            conf.gameId = gameId;
        } else {
            boolean flag = false;
            /** 判断是否为升级 */
            Iterator<UGAppConf> iter = mInstallList.iterator();
            while (iter.hasNext()) {
                conf = iter.next();
                if (packageName.equals(conf.packageName) && versionCode > conf.versionCode) {
                    flag = true;
                    break;
                }
            }

            conf = new UGAppConf();
            conf.gameId = gameId;
            conf.packageName = packageName;
            conf.versionName = versionName;
            conf.versionCode = versionCode;
            conf.url = url;
            conf.fileSize = fileSize;
            if (!flag) {
                conf.state = UGAppConf.STATE_INIT;
                mInitList.add(conf);
            } else {
                conf.state = UGAppConf.STATE_UPDATE;
                mUpdateList.add(conf);
//				updateNotify();
            }
            mAppDirector.put(key, conf);
        }
        return conf;
    }


    /**
     * 退出时调用
     *
     * @param context
     */
    public void exit(Context context) {
        if (mInited) {
//			Log.i(TAG, "exit");
            try {
                context.getApplicationContext().unregisterReceiver(mReceiver);
                saveDowing(context.getApplicationContext());
                DownManager.getInstance(context).shutDown();  // 不允许在提交任务
//				stopReport(context);
                mInited = false;
                mManager = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 进入平台 开启暂停下载的app
     *
     * @param context
     */
    private void startPause(Context context) {
        List<String> keys = new ArrayList<String>();
        UGAppConf conf = null;
        Iterator<UGAppConf> iter = mPauseList.iterator();
        while (iter.hasNext()) {
            conf = iter.next();
            keys.add(conf.packageName + "," + conf.versionCode);
        }

        for (String key : keys) {
            conf = mAppDirector.get(key);
            conf.downApp(context, 1);
        }
    }


    private void initWait(Context context) {
        List<UGAppConf> confs = UGAppConfMgmt.getConfs(context, new String[]{UGDatabaseDefine.APPCONF.STATE},
                new String[]{String.valueOf(UGAppConf.STATE_WAIT)});

        if (confs == null || confs.isEmpty()) {
            return;
        }

        mWaitList.clear();

        Iterator<UGAppConf> iter = confs.iterator();
        while (iter.hasNext()) {
            UGAppConf conf = iter.next();
            mWaitList.add(conf);
            mAppDirector.put(conf.packageName + "," + conf.versionCode, conf);
        }
    }

    private void initDowning(Context context) {
        List<UGAppConf> confs = UGAppConfMgmt.getConfs(context, new String[]{UGDatabaseDefine.APPCONF.STATE},
                new String[]{String.valueOf(UGAppConf.STATE_DOWNING)});

        if (confs == null || confs.isEmpty()) {
            return;
        }

        mDowningList.clear();

        Iterator<UGAppConf> iter = confs.iterator();
        while (iter.hasNext()) {
            UGAppConf conf = iter.next();
            mDowningList.add(conf);
            mAppDirector.put(conf.packageName + "," + conf.versionCode, conf);
        }
    }

    private void initPause(Context context) {
        List<UGAppConf> confs = UGAppConfMgmt.getConfs(context, new String[]{UGDatabaseDefine.APPCONF.STATE},
                new String[]{String.valueOf(UGAppConf.STATE_PAUSE)});

        if (confs == null || confs.isEmpty()) {
            return;
        }

        mPauseList.clear();

        Iterator<UGAppConf> iter = confs.iterator();
        while (iter.hasNext()) {
            UGAppConf conf = iter.next();

            mPauseList.add(conf);
            mAppDirector.put(conf.packageName + "," + conf.versionCode, conf);
//			Log.i(TAG, "initPause " + conf.packageName + " " + conf.versionCode 
//					+ " " + conf.state + " " + conf.percent);
        }
    }

    private void initFinish(Context context) {
        List<UGAppConf> confs = UGAppConfMgmt.getConfs(context, new String[]{UGDatabaseDefine.APPCONF.STATE},
                new String[]{String.valueOf(UGAppConf.STATE_FINISH)});

        if (confs == null || confs.isEmpty()) {
            return;
        }

        mFinishList.clear();
        Iterator<UGAppConf> iter = confs.iterator();
        while (iter.hasNext()) {
            UGAppConf conf = iter.next();

            mFinishList.add(conf);
            mAppDirector.put(conf.packageName + "," + conf.versionCode, conf);
        }
    }

    private void initInstall(Context context) {
        /*查找数据中已安装记录*/
        List<UGAppConf> installList = UGAppConfMgmt.getConfs(context, new String[]{UGDatabaseDefine.APPCONF.STATE},
                new String[]{String.valueOf(UGAppConf.STATE_INSTALL)});
		
		/*扫描本地安装程序*/
        PackageManager manager = null;
        List<ApplicationInfo> apps = null;
        try {
            manager = context.getApplicationContext().getPackageManager();
            apps = manager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (apps == null || apps.isEmpty()) {
            return;
        }

        String key = "";
        mInstallList.clear();
        for (Iterator<ApplicationInfo> mapIter = apps.iterator(); mapIter.hasNext(); ) {
            ApplicationInfo appInfo = mapIter.next();
            //ApplicationInfo.FLAG_UPDATED_SYSTEM_APP 表示是系统程序，但用户更新过，也算是用户安装的程序
            if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
                    || (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    && !appInfo.packageName.equals(context.getPackageName())) {
                UGAppConf conf = new UGAppConf();
                try {
                    conf.packageName = appInfo.packageName;
                    conf.versionName = manager.getPackageInfo(appInfo.packageName, 0).versionName;
                    conf.versionCode = manager.getPackageInfo(appInfo.packageName, 0).versionCode;

                    key = conf.packageName + "," + conf.versionCode;

                    for (UGAppConf conf1 : installList) {
                        if (key.equals(conf1.packageName + "," + conf1.versionCode)) {
                            conf = conf1;
                            break;
                        }
                    }

                    conf.state = UGAppConf.STATE_INSTALL;
                    mInstallList.add(conf);
                    mAppDirector.put(key, conf);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private synchronized void saveDowing(Context context) {
        ArrayList<String[]> whereArgs = new ArrayList<String[]>();
        ArrayList<String[]> whereValues = new ArrayList<String[]>();
        for (int i = 0; i < mDowningList.size(); i++) {
            UGAppConf conf = mDowningList.get(i);
            conf.exitDowing(context);
            whereArgs.add(new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE});
            whereValues.add(new String[]{conf.packageName, String.valueOf(conf.versionCode)});
        }

        UGAppConfMgmt.deletes(context, whereArgs, whereValues);
        UGAppConfMgmt.inserts(context, mDowningList);
    }


    private class PackageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String packageName = intent.getDataString().substring(8, intent.getDataString().length());
                String key = null;
                if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                    PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);
                    key = packageName + "," + pi.versionCode;
                    if (mAppDirector.containsKey(key)) {
                        UGAppConf conf = mAppDirector.get(key);
                        conf.installedApp(context);
                        UGAppReportApp.upSendInstall(context, conf, true);
                    }
                } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                    for (UGAppConf conf : mInstallList) {
                        if (packageName.equals(conf.packageName)) {
                            key = conf.packageName + "," + conf.versionCode;
                            break;
                        }
                    }
                    if (!TextUtils.isEmpty(key) && mAppDirector.containsKey(key)) {
                        UGAppConf conf = mAppDirector.get(key);
                        removeInstall(context, conf);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 卸载app调用
    private synchronized void removeInstall(Context context, UGAppConf conf) {
        if (conf.state != UGAppConf.STATE_INSTALL) {
            //如果app没有安装，删除下载
            conf.removeApp(context);
            return;
        }

        List<UGAppConf> confs = UGAppConfMgmt.getConfs(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE
                , UGDatabaseDefine.APPCONF.STATE}, new String[]{conf.packageName, String.valueOf(conf.versionCode), String.valueOf(conf.state)});
        if (confs.size() == 0) {
            conf.removeApp(context);
            return;
        }

        UGAppConf conf1 = confs.get(0);
        if (TextUtils.isEmpty(conf1.fileDirectory)) {
            conf.removeApp(context);
            return;
        }

        if (!new File(conf1.fileDirectory).exists() || !conf1.isApk(context)) {
            conf.removeApp(context);
            return;
        }

        conf.state = UGAppConf.STATE_FINISH;
        conf.notifyChange(conf, UGAppConf.STATE_FINISH);
        UGAppConfMgmt.delete(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE},
                new String[]{conf.packageName, String.valueOf(conf.versionCode)});
        UGAppConfMgmt.insert(context, conf);  //数据库中先删除原来安装的记录，然后插入下载完成的记录
    }

//	/**
//	 * 终止定时扫描
//	 * @param context
//	 */
//	public void stopReport(Context context)
//	{ 
//		Iterator<String> iter = mAppDirector.keySet().iterator();
//		String key = "";
//		UGAppConf conf = null;
//		while (iter.hasNext())
//		{
//			key = iter.next();
//			conf = mAppDirector.get(key); 
//			if (conf.appId > 0)
//			{
//				UGAppReportApp.stopReport(context, (int) conf.appId, conf.packageName);
//			}
//		}
//	} 

    public synchronized List<UGAppConf> getInit() {
        return mInitList;
    }

    public synchronized List<UGAppConf> getWait() {
        return mWaitList;
    }

    public synchronized List<UGAppConf> getDowning() {
        return mDowningList;
    }

    public synchronized List<UGAppConf> getPause() {
        return mPauseList;
    }

    public synchronized List<UGAppConf> getUpdate() {
        return mUpdateList;
    }

    public synchronized List<UGAppConf> getFinish() {
        return mFinishList;
    }

    public synchronized List<UGAppConf> getInstall() {
        return mInstallList;
    }

    public synchronized void removeConf(List<UGAppConf> list, UGAppConf conf) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == conf) {
                index = i;
            }
        }
        if (index > -1) {
            list.remove(index);
        }
    }

    public Map<String, UGAppConf> getAppDirector() {
        return mAppDirector;
    }

    public boolean ismInited() {
        return mInited;
    }
}
