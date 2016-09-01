package com.game.hall.download.download.conf;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.game.hall.download.util.UGLog;
import com.game.hall.download.download.app.UGScanThread;
import com.game.hall.download.download.db.UGAppConfMgmt;
import com.game.hall.download.download.db.UGDatabaseDefine;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UGAppConf implements Serializable {
    private static final long serialVersionUID = -329784592230144674L;
    /**
     * 初始状态
     */
    public static final int STATE_INIT = 200;
    /**
     * 等待
     */
    public static final int STATE_WAIT = 201;
    /**
     * 下载中
     */
    public static final int STATE_DOWNING = 202;
    /**
     * 暂停
     */
    public static final int STATE_PAUSE = 203;
    /**
     * 升级
     */
    public static final int STATE_UPDATE = 204;
    /**
     * 下载完成
     */
    public static final int STATE_FINISH = 205;
    /**
     * 已安装
     */
    public static final int STATE_INSTALL = 206;
    /**
     * 下载状态
     */
    public int state = STATE_INIT;

    public String gameId = "";
    /**
     * 版本名称
     */
    public String versionName = "";
    /**
     * 版本号
     */
    public int versionCode = 0;
    /**
     * 包名
     */
    public String packageName = "";
    /**
     * 下载地 址
     */
    public String url = "";
    /**
     * md5加密后的文件名
     */
    public String fileName = "";
    /**
     * 文件大小
     */
    public long fileSize = 0;
    /**
     * 已经下载大小
     */
    public long downloadSize = 0;
    /**
     * 完成百分比
     */
    public int percent = 0;
    /**
     * apk文件保存路径
     */
    public String fileDirectory = "";

    /**
     * apk名字
     */
    public String appName = "";
    /**
     * apk图标
     */
    public String appIcon = "";
    /**
     * apk的rptkey
     */
    public String rptKey = "";

    /**
     * 附带参数
     */
    public Object extra = null;
    public boolean isExit = false;

    private ArrayList<UGOnChangeListenner> mChangeListenners = new ArrayList<UGOnChangeListenner>();

    //	private DownloadTask					mDownTask			= null;
    public UGAppConf() {
        ;
    }

    /**
     * 下载
     *
     * @param context
     */
    public synchronized void downApp(Context context) {
        downApp(context, 0);
    }

    /**
     * 暂停下载
     *
     * @param context
     */
    public synchronized void pauseApp(Context context) {
        Log.e("wxue", "---UGAppConf.pauseApp()----");
        this.isExit = false;
        // 检查当前状态
        switch (state) {
            case STATE_INIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInit(), this);
                break;
            case STATE_WAIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getWait(), this);
                break;
            case STATE_PAUSE:
                return;
            case STATE_DOWNING:
                //取消下载线程
//				this.isExit = true;
//				if (mDownTask != null)
//				{
//					mDownTask.cancel(true);
//					mDownTask = null;
//				} 
                DownManager dm = DownManager.getInstance(context);
                dm.pauseTask(this);
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getDowning(), this);
                break;
            case STATE_UPDATE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getUpdate(), this);
                break;
            case STATE_FINISH:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getFinish(), this);
                break;
            case STATE_INSTALL:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInstall(), this);
                break;
        }
        //设置当前状态为暂停
        this.state = STATE_PAUSE;
        //添加至暂停列表中
        UGAppConfManager.getManager().getPause().add(this);

//		UGAppConfManager.getManager().updateNotify();
        //保存至数据库, 数据库有则更新、没有则插入
        UGAppConfMgmt.delete(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE},
                new String[]{packageName, String.valueOf(versionCode)});

        UGAppConfMgmt.insert(context, this);
        //通知监听者
        notifyChange(this, this.state);
        //从等待列表中选取一个下载
        List<UGAppConf> confs = UGAppConfManager.getManager().getWait();
        if (confs.size() > 0) {
            confs.get(0).downApp(context, 1);
        }
    }

    /**
     * 删除下载
     *
     * @param context
     */
    public synchronized void removeApp(Context context) {
        this.isExit = false;
        // 检查当前状态
        switch (state) {
            case STATE_INIT:
                return;
            case STATE_WAIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getWait(), this);
                break;
            case STATE_PAUSE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getPause(), this);
                break;
            case STATE_DOWNING:
//				this.isExit = true;
//				if (mDownTask != null)
//				{
//					mDownTask.cancel(true);
//					mDownTask = null;
//				} 
                DownManager dm = DownManager.getInstance(context);
                dm.deleteTask(this.url);
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getDowning(), this);
                break;
            case STATE_UPDATE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getUpdate(), this);
                break;
            case STATE_FINISH:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getFinish(), this);
                break;
            case STATE_INSTALL:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInstall(), this);
                break;
        }
        // 1、 删除文件
        if (!TextUtils.isEmpty(this.fileDirectory)) {
            try {
                File file = new File(this.fileDirectory);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 2、 删除数据库文件
        UGAppConfMgmt.delete(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE},
                new String[]{packageName, String.valueOf(versionCode)});

        //3、重置状态
        this.state = STATE_INIT;
        this.percent = 0;
        this.downloadSize = 0;
        this.fileName = "";
        this.fileDirectory = "";

        UGAppConfManager.getManager().getInit().add(this);

//		UGAppConfManager.getManager().updateNotify();
        // 4、检查是否有等待任务
        List<UGAppConf> confs = UGAppConfManager.getManager().getWait();
        if (confs.size() > 0) {
            confs.get(0).downApp(context, 1);
        }
        // 5、通知改变
        this.notifyChange(this, this.state);
    }

    /**
     * 安装 apk
     *
     * @param context
     */
    public synchronized void installApp(Context context) {
        if (this.state != STATE_FINISH) {
            return;
        }

        // 下载完之后弹出安装界面
        File file = new File(this.fileDirectory);
        if (!file.exists()) {
            removeApp(context);
            return;
        }
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "游戏安装包已损坏或不存在，请重新下载安装！", Toast.LENGTH_LONG).show();
            removeApp(context);
        }
    }

    /**
     * 打开apk
     *
     * @param context
     */
    public synchronized void openApp(Context context) {
        if (state != STATE_INSTALL) {
            return;
        }

        try {
            Intent intent = new Intent();
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "游戏已损坏或不存在，请重新下载安装！", Toast.LENGTH_LONG).show();
            removeApp(context);
        }
    }

    /***
     * 判断是否为完整的apk
     *
     * @param context
     * @return
     */
    public synchronized boolean isApk(Context context) {
        boolean flag = false;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(this.fileDirectory, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    synchronized void downApp(Context context, int type) {
        Log.e("wxue", "---UGAppConf.downApp()----");
        this.isExit = false;
        // 检查是否有网络
        if (!isConnected(context) && type == 0) {
            Toast.makeText(context, "网络连接有问题，请检查网络设置！", Toast.LENGTH_LONG).show();
            return;
        }
        // 检查sdcard是否可用
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && type == 0) {
            Toast.makeText(context, "sdcard不可用！", Toast.LENGTH_LONG).show();
            return;
        }
        // 检查url地址是否为空
        if (TextUtils.isEmpty(this.url) && type == 0) {
            Toast.makeText(context, "下载地址不可用！", Toast.LENGTH_LONG).show();
            return;
        }

        // 检查当前状态
        switch (state) {
            case STATE_INIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInit(), this);
                break;
            case STATE_WAIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getWait(), this);
                break;
            case STATE_PAUSE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getPause(), this);
                break;
            case STATE_DOWNING:
                return;
            case STATE_UPDATE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getUpdate(), this);
                break;
            case STATE_FINISH:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getFinish(), this);
                break;
            case STATE_INSTALL:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInstall(), this);
                break;
        }

        List<UGAppConf> confs = UGAppConfManager.getManager().getDowning();
        // 判断是否超过最大下载限制
        if (confs.size() >= UGAppConfManager.MAX_DOWN_NUM) {
            waitApp(context);
            return;
        }

        // 将下载加入下载任务队列
        DownManager dm = DownManager.getInstance(context);
        if (!dm.hasTask(url)) {
            Log.e("wxue", "---UGAppConf.downApp()---任务不存在---");
            dm.addTask(this);
        } else {
            if (state == STATE_WAIT) {
                DownManager.getInstance(context).startWaitTask();
            } else if (state == STATE_PAUSE) {
                DownManager.getInstance(context).continueTask(this.url);
            }
        }

//		mDownTask = new DownloadTask(context);
//		mDownTask.execute(this);

        this.state = STATE_DOWNING;
        // 保存至数据库, 数据库有则更新、没有则插入
        UGAppConfMgmt.delete(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE}, new String[]{packageName, String.valueOf(versionCode)});
        UGAppConfMgmt.insert(context, this);

        //添加到下载列表中
        UGAppConfManager.getManager().getDowning().add(this);
        notifyChange(this, this.state);

    }

    /**
     * 下载等待
     *
     * @param context
     */
    private synchronized void waitApp(Context context) {

        this.isExit = false;
        // 检查当前状态
        switch (state) {
            case STATE_INIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInit(), this);
                break;
            case STATE_WAIT:
                return;
            case STATE_PAUSE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getPause(), this);
                break;
            case STATE_DOWNING:

//				this.isExit = true;
                //取消下载线程
//				if (mDownTask != null)
//				{
//					mDownTask.cancel(true);
//					mDownTask = null;
//				} 
                DownManager.getInstance(context).pauseTask(this);
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getDowning(), this);
                break;
            case STATE_UPDATE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getUpdate(), this);
                break;
            case STATE_FINISH:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getFinish(), this);
                break;
            case STATE_INSTALL:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInstall(), this);
                break;
        }


        // 放入队列等待下载
        DownManager dm = DownManager.getInstance(context);
        if (!dm.hasTask(url)) {
            dm.addTask(this);
        } else {
            if (state == STATE_PAUSE) {
                // 放入下载队列
                dm.continueTask(url);
            }
        }

        this.state = STATE_WAIT;
        // 保存至数据库, 数据库有则更新、没有则插入
        UGAppConfMgmt.delete(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE}, new String[]{packageName, String.valueOf(versionCode)});
        UGAppConfMgmt.insert(context, this);
        //添加至等待列表
        UGAppConfManager.getManager().getWait().add(this);
        notifyChange(this, this.state);
    }

    /**
     * 下载完毕
     *
     * @param context
     */
    public synchronized void finishedApp(Context context) {

        this.isExit = false;
        // 检查当前状态
        switch (state) {
            case STATE_INIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInit(), this);
                break;
            case STATE_WAIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getWait(), this);
                break;
            case STATE_PAUSE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getPause(), this);
                break;
            case STATE_DOWNING:

                this.isExit = true;
                //取消下载线程
//				if (mDownTask != null)
//				{
//					mDownTask.cancel(true);
//					mDownTask = null;
//				}
                DownManager dm = DownManager.getInstance(context);
                dm.deleteTask(this.url);
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getDowning(), this);
                break;
            case STATE_UPDATE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getUpdate(), this);
                break;
            case STATE_FINISH:
                return;
            case STATE_INSTALL:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInstall(), this);
                break;
        }

        this.state = STATE_FINISH;
        notifyChange(this, this.state);

        //保存至数据库, 数据库有则更新、没有则插入
        UGAppConfMgmt.delete(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE},
                new String[]{packageName, String.valueOf(versionCode)});
        UGAppConfMgmt.insert(context, this);
        //添加至已下载列表
        UGAppConfManager.getManager().getFinish().add(this);

        // 下载完之后弹出安装界面
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            File file = new File(this.fileDirectory);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
//			e.printStackTrace();
        }

        // 4、检查是否有等待任务
        List<UGAppConf> confs = UGAppConfManager.getManager().getWait();
        if (confs.size() > 0) {
            confs.get(0).downApp(context, 1);
        }
    }

    /**
     * 安装完毕
     */
    synchronized void installedApp(Context context) {

        this.isExit = false;
        // 检查当前状态
        switch (state) {
            case STATE_INIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getInit(), this);
                break;
            case STATE_WAIT:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getWait(), this);
                break;
            case STATE_PAUSE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getPause(), this);
                break;
            case STATE_DOWNING:
                // 取消下载线程
                this.isExit = true;
//				if (mDownTask != null)
//				{
//					mDownTask.cancel(true);
//					mDownTask = null;
//				}
                DownManager.getInstance(context).deleteTask(this.url);

                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getDowning(), this);
                break;
            case STATE_UPDATE:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getUpdate(), this);
                break;
            case STATE_FINISH:
                UGAppConfManager.getManager().removeConf(UGAppConfManager.getManager().getFinish(), this);
                break;
            case STATE_INSTALL:
                return;
        }

        this.state = STATE_INSTALL;
        notifyChange(this, this.state);

        //添加至已安装列表
        UGAppConfManager.getManager().getInstall().add(this);

        if (this.percent == 100) { //是目前应用下载的就做如下操作
            //启动一个线程，5秒后去检测是否打开
            UGLog.e("---即将开启内部扫描线程----");
            new UGScanThread(context, packageName, rptKey, gameId).start();

            //删除数据库保存的文件
            UGAppConfMgmt.delete(context, new String[]{UGDatabaseDefine.APPCONF.PACKAGENAME, UGDatabaseDefine.APPCONF.VERSIONCODE},
                    new String[]{packageName, String.valueOf(versionCode)});
            UGAppConfMgmt.insert(context, this);
        }
    }

    synchronized void exitDowing(Context context) {
        if (this.state != STATE_DOWNING) {
            return;
        }

        this.state = STATE_PAUSE;
        //取消下载线程
        this.isExit = true;
//		if (mDownTask != null)
//		{
//			mDownTask.cancel(true);
//			mDownTask = null;
//		}
        DownManager.getInstance(context).deleteTask(this.url);
    }

    public synchronized void addChangeListenner(UGOnChangeListenner changeListenner) {
        if (changeListenner == null) {
            return;
        }

        if (mChangeListenners.contains(changeListenner)) {
            return;
        }

        mChangeListenners.add(changeListenner);
    }

    public synchronized void removeChangeListenner(UGOnChangeListenner changeListenner) {
        if (changeListenner == null) {
            return;
        }

        if (mChangeListenners.contains(changeListenner)) {
            mChangeListenners.remove(changeListenner);
        }
    }

    public interface UGOnChangeListenner {
        public void onChange(UGAppConf conf, int state);
    }

//	private class DownloadTask extends AsyncTask<UGAppConf, Integer, Object>
//	{
//		private UGAppConf	conf;
//		private Context	context; 
//
//		public DownloadTask(Context context)
//		{
//			this.context = context; 
//		}
//
//		@Override
//		protected Object doInBackground(UGAppConf... params)
//		{
//			if (params != null)
//			{
//				this.conf = params[0];
//			}
//			UGHttpClient.startDown(context, conf, new DownLoadTaskCallBack()
//			{
//				@Override
//				public void onProgressUpdate(Integer values)
//				{
//					publishProgress(values);
//				}
//			});
//
//			return null;
//		}
//
//		@Override
//		protected void onProgressUpdate(Integer... values)
//		{
//			if (!isConnected(context))
//			{
//				pauseApp(context);
//				return;
//			}
//			
//			if (values[0] == 100)
//			{
//				finishedApp(context);
//				//上报下载成功
//				UGAppReportApp.upsendDownSucess(conf,context);
//			} 
//			else
//			{
//				notifyChange(conf, conf.state);
//			}
//		}
//
//	}

    public void notifyChange(UGAppConf conf, int state) {
        if (mChangeListenners != null) {
            for (int i = 0; i < mChangeListenners.size(); i++) {
                mChangeListenners.get(i).onChange(this, state);
            }
        }
    }

    public interface DownLoadTaskCallBack {
        public void onProgressUpdate(Integer values);
    }

    private boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return info.isAvailable();
        }

        return false;
    }

}
