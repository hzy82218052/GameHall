package com.game.hall.download.download.conf;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.game.hall.download.download.app.UGHttpClient;


public class DownThread implements Runnable{
	private UGAppConf conf;
	private Context context;
	private Handler handler;
	private boolean isRunning = true;
	
	public DownThread(UGAppConf conf,Context context,Handler handler){
		this.conf = conf;
		this.context = context;
		this.handler = handler;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public UGAppConf getConf() {
		return conf;
	}

	@Override
	public void run() {
		if(isRunning){
			UGHttpClient.startDown(context, conf, new UGAppConf.DownLoadTaskCallBack() {
				@Override
				public void onProgressUpdate(Integer values) {
					Message msg = handler.obtainMessage();
					if(values == -1){
						isRunning = false;
						msg.what = UGDownloadNotice.MSG_ERROR;
					}else if(values == 100){
						isRunning = false;
						msg.what = UGDownloadNotice.MSG_FINNISH;
					}else{
						isRunning = true;
						msg.what = UGDownloadNotice.MSG_UPDATE;
					}
					msg.obj = conf;
					handler.sendMessage(msg);
				}
			},this);
		} 
	}
	
}
