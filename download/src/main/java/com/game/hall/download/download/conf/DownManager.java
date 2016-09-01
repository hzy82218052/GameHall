package com.game.hall.download.download.conf;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.game.hall.download.download.app.UGAppReportApp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownManager {
	private static DownManager mDowManager;
	private ExecutorService mThreadPool;
	private static final int MAX_TASK_COUNT = 100;
	private static final int MAX_DOWNLOAD_THREAD_COUNT = 2;
	private Context mContext;
	private TaskQueue mTaskQueue;
	private List<DownThread> mDownloadingTasks;
	private List<DownThread> mPausingTasks;
	

	public static DownManager getInstance(Context context) {
		if (mDowManager == null) {
			synchronized (DownManager.class) {
				if (mDowManager == null) {
					mDowManager = new DownManager(context);
				}
			}
		}
		return mDowManager;
	}

	private DownManager(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mThreadPool = Executors.newCachedThreadPool(); 
		
		mTaskQueue = new TaskQueue();
		mDownloadingTasks = new ArrayList<DownThread>();
		mPausingTasks = new ArrayList<DownThread>();
	}

	public int getQueueTaskCount() {

		return mTaskQueue.size();
	}

	public int getDownloadingTaskCount() {

		return mDownloadingTasks.size();
	}

	public int getPausingTaskCount() {

		return mPausingTasks.size();
	}

	public int getTotalTaskCount() {

		return getQueueTaskCount() + getDownloadingTaskCount()
				+ getPausingTaskCount();
	}
	
	public void executeTak() {
		DownThread task = mTaskQueue.poll();
		if (task != null) {
			mDownloadingTasks.add(task);
			mThreadPool.submit(task);
		}
	}
	
	public void shutDown(){
		mDowManager = null;
		mThreadPool.shutdown();
	}
	
	public boolean hasTask(String url) {
		DownThread task;
		for (int i = 0; i < mDownloadingTasks.size(); i++) {
			task = mDownloadingTasks.get(i);
			if (task.getConf().url.equals(url)) {
				return true;
			}
		}

		for (int i = 0; i < mPausingTasks.size(); i++) {
			task = mPausingTasks.get(i);
			if (task.getConf().url.equals(url)) {
				return true;
			}
		}

		for (int i = 0; i < mTaskQueue.size(); i++) {
			task = mTaskQueue.get(i);
			if (task.getConf().url.equals(url)) {
				return true;
			}
		}
		return false;
	}

	public boolean isPausing(String url) {
		DownThread task;
		for (int i = 0; i < mPausingTasks.size(); i++) {
			task = mPausingTasks.get(i);
			if (task.getConf().url.equals(url)) {
				return true;
			}
		}
		return false;
	}

	public boolean isDowning(String url) {
		DownThread task;
		for (int i = 0; i < mDownloadingTasks.size(); i++) {
			task = mDownloadingTasks.get(i);
			if (task.getConf().url.equals(url)) {
				return true;
			}
		}
		return false;
	}

	public boolean isWaiting(String url) {
		DownThread task;
		for (int i = 0; i < mTaskQueue.size(); i++) {
			task = mTaskQueue.get(i);
			if (task.getConf().url.equals(url)) {
				return true;
			}
		}
		return false;
	}

	public void addTask(UGAppConf conf) {
		if (getTotalTaskCount() >= MAX_TASK_COUNT) {
			Toast.makeText(mContext, "任务列表已满", Toast.LENGTH_LONG).show();
			return;
		}
		DownThread task = newDownloadTask(conf);
		Log.v("wxue", "---添加一个新的任务task = " + task + "\n" + " url = " + conf.url);
		addTask(task);
	}

	private void addTask(DownThread task) {
		Log.e("xuewei","---addTask()---mThreadPool = " + mThreadPool);
		mTaskQueue.offer(task);
		executeTak();
	}
	
	public void startWaitTask(){
		Log.v("wxue","---DownManager.startWaitTask()---");
		executeTak();
	}

	public synchronized void pauseTask(UGAppConf conf) {
		Log.v("wxue", "---DownManager.pauseTask()---");
		DownThread task;
		for (int i = 0; i < mDownloadingTasks.size(); i++) {
			task = mDownloadingTasks.get(i);
			if (task != null && task.getConf().url.equals(conf.url)) {
				Log.v("wxue", "---暂停下载列表中的任务---task = " + task);
				pauseTask(task, conf);
			}
		}
	}

	public synchronized void pauseTask(DownThread task, UGAppConf conf) {
		if (task != null) {
			task.setRunning(false);
			mDownloadingTasks.remove(task);
			task = newDownloadTask(conf);
			mPausingTasks.add(task);
		}
	}

	public synchronized void deleteTask(String url) {
		DownThread task;
		for (int i = 0; i < mDownloadingTasks.size(); i++) {
			task = mDownloadingTasks.get(i);
			if (task != null && task.getConf().url.equals(url)) {
//				task.onCancelled();
				completeTask(task);
				return;
			}
		}
		for (int i = 0; i < mTaskQueue.size(); i++) {
			task = mTaskQueue.get(i);
			if (task != null && task.getConf().url.equals(url)) {
				mTaskQueue.remove(task);
			}
		}
		for (int i = 0; i < mPausingTasks.size(); i++) {
			task = mPausingTasks.get(i);
			if (task != null && task.getConf().url.equals(url)) {
				mPausingTasks.remove(task);
			}
		}
	}

	public synchronized void continueTask(String url) {
		Log.v("wxue", "---DownManager.continueTask()---");
		DownThread task;
		for (int i = 0; i < mPausingTasks.size(); i++) {
			task = mPausingTasks.get(i);
			if (task != null && task.getConf().url.equals(url)) {
				Log.v("wxue", "---继续下载暂停列表中的任务---" + task);
				continueTask(task);
			}

		}
	}

	public synchronized void continueTask(DownThread task) {
		if (task != null) {
			mPausingTasks.remove(task);
			mTaskQueue.offer(task);
			executeTak();
		}
	}

	public synchronized void completeTask(DownThread task) {
		if (mDownloadingTasks.contains(task)) {
			task.setRunning(false);
			mDownloadingTasks.remove(task);
		}
	}

	/**
	 * Create a new download task
	 * 
	 * @param conf
	 * @return
	 */
	private DownThread newDownloadTask(UGAppConf conf) {

		return new DownThread(conf, mContext, mHandler);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			UGAppConf conf = (UGAppConf) msg.obj;
			switch (msg.what) {
			case UGDownloadNotice.MSG_FINNISH:
				// 上报下载成功
				conf.finishedApp(mContext);
				UGAppReportApp.upsendDownSucess(conf, mContext,true);
				break;
			case UGDownloadNotice.MSG_ERROR:
				conf.pauseApp(mContext);
				break;
			case UGDownloadNotice.MSG_UPDATE:
				conf.notifyChange(conf, conf.state);
				break;
			default:
				break;
			}
		};
	};
	
	private class TaskQueue {
		private Queue<DownThread> taskQueue;

		public TaskQueue() {

			taskQueue = new LinkedList<DownThread>();
		}

		public void offer(DownThread task) {

			taskQueue.offer(task); // 将任务插入队列
		}

		public DownThread poll() {
			DownThread task;
//			while (mDownloadingTasks.size() >= MAX_DOWNLOAD_THREAD_COUNT
//					|| (task = taskQueue.poll()) == null) {
//				try {
//					Thread.sleep(1000); // sleep
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			 if (mDownloadingTasks.size() >= MAX_DOWNLOAD_THREAD_COUNT) {
			 task = null;
			 } else {
			 task = taskQueue.poll();
			 }
			return task;
		}

		public DownThread get(int position) {

			if (position >= size()) {
				return null;
			}
			return ((LinkedList<DownThread>) taskQueue).get(position);
		}

		public int size() {

			return taskQueue.size();
		}

		@SuppressWarnings("unused")
		public boolean remove(int position) {

			return taskQueue.remove(get(position));
		}

		public boolean remove(DownThread task) {

			return taskQueue.remove(task);
		}
	}
}
