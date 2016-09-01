package com.game.hall.download.bean;


import com.game.hall.download.download.conf.UGAppConf;
import com.game.hall.download.download.conf.UGAppConfManager;

public class AppBean {
	private String gameid;       //游戏id(唯一)
	private String appicon;      //游戏图标
	private String applink;      //游戏apk下载地址
	private String appname;      //游戏名
	private String appversion;   //应用版本
	private String apppackagename;  //应用包名
	private long appcode;           //应用的版本号(AndroidManifest.xml中的版本号)
	private String slogan;       //应用的简介
	private String appsize;      // 应用的大小
	private String score;        //赠送积分
	private String appdownloadnum;  //下载数
	private String rptkey;       //游戏位置(数据上报使用)

	private UGAppConf appConf;
	
	public String getGameid() {
		return gameid;
	}
	public void setGameid(String gameid) {
		this.gameid = gameid;
	}
	public String getAppicon() {
		return appicon;
	}
	public void setAppicon(String appicon) {
		this.appicon = appicon;
	}
	public String getApplink() {
		return applink;
	}
	public void setApplink(String applink) {
		this.applink = applink;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getAppversion() {
		return appversion;
	}
	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}
	public String getApppackagename() {
		return apppackagename;
	}
	public void setApppackagename(String apppackagename) {
		this.apppackagename = apppackagename;
	}
	public long getAppcode() {
		return appcode;
	}
	public void setAppcode(long appcode) {
		this.appcode = appcode;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getAppsize() {
		return appsize;
	}
	public void setAppsize(String appsize) {
		this.appsize = appsize;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getAppdownloadnum() {
		return appdownloadnum;
	}
	public void setAppdownloadnum(String appdownloadnum) {
		this.appdownloadnum = appdownloadnum;
	}
	public String getRptkey() {
		return rptkey;
	}
	public void setRptkey(String rptkey) {
		this.rptkey = rptkey;
	}

	public UGAppConf getAppConf(){
		if (appConf == null) {
			try {
//				appConf = UGAppConfManager.getManager().register(this);
			} catch (Exception e) {
				// TODO: handle exception
				appConf = null;
			}
		}
		return appConf;
	}
	public void setAppConf(UGAppConf conf){
		this.appConf = conf;
	}
	
	
}
