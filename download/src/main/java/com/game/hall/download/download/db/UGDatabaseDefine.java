package com.game.hall.download.download.db;

public final class UGDatabaseDefine
{ 
	public static final class APPCONF
	{
		public static final String ID = "ID";
		public static final String GAMEID = "gameId";
		public static final String FILENAME = "fileName";
		public static final String FILESIZE = "fileSize";
		public static final String DOWNLOADSIZE = "downloadSize";
		public static final String PERCENT = "percent";
		public static final String FILEDIRECTORY = "fileDirectory"; 
		public static final String STATE = "state"; 
		public static final String PACKAGENAME = "packageName";		 
		public static final String VERSIONCODE = "versionCode";	
		public static final String URL = "url";
		public static final String APPICON = "appIcon";
		public static final String APPNAME = "appName";
		public static final String APPRPTKEY = "appRptKey";
		static final String DATA0 = "DATA0";
		static final String DATA1 = "DATA1";
		static final String DATA2 = "DATA2";
		static final String DATA3 = "DATA3";
		static final String DATA4 = "DATA4";
		static final String DATA5 = "DATA5";
		
		public static final String TB_NAME = "APPCONF";
		public static final String[] FILED_NAMES = {
			ID,
			GAMEID,
			FILENAME,
			FILESIZE,
			DOWNLOADSIZE,
			PERCENT,
			FILEDIRECTORY, 
			STATE,
			PACKAGENAME,
			VERSIONCODE,
			URL,
			APPICON,
			APPNAME,
			APPRPTKEY,
			
			DATA0,
			DATA1,
			DATA2,
			DATA3,
			DATA4,
			DATA5,
		};
		public static final String[] FIELD_TYPES = {
			 "INTEGER PRIMARY KEY AUTOINCREMENT", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR",
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR",
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR",
			 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
		}; 
	
	}
	
	public static final class APPDOWN
	{
		public static final String ID = "ID";
		public static final String THREADID = "threadId";
		public static final String STARTPOS = "startPos";
		public static final String ENDPOS = "endPos";
		public static final String COMPLETE = "completeSize";
		public static final String URL = "url";
		
		static final String DATA0 = "DATA0";
		static final String DATA1 = "DATA1";
		static final String DATA2 = "DATA2";
		static final String DATA3 = "DATA3";
		static final String DATA4 = "DATA4";
		
		public static final String TB_NAME = "APPDOWN";
		public static final String[] FILED_NAMES = {
			ID,
			THREADID,
			STARTPOS,
			ENDPOS,
			COMPLETE,
			URL,
			DATA0,
			DATA1,
			DATA2,
			DATA3,
			DATA4
		};
		public static final String[] FIELD_TYPES = {
			 "INTEGER PRIMARY KEY AUTOINCREMENT", 
			 "VARCHAR", 
			 "VARCHAR",
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR", 
			 "VARCHAR"
		}; 
	
	}
	
	public static final class DataBase
	{
		public static final String DB_NAME = "AppConf.db";
		
		
		/**
		 * 2014-10-23版本中：定的 DB_VERSION = 1
         */
		public static final int DB_VERSION = 1; 
		
		public static final String[] TB_NAMES = {
			APPCONF.TB_NAME,  APPDOWN.TB_NAME
		};
		public static final String[][] FIELD_NAMES = {
			APPCONF.FILED_NAMES, APPDOWN.FILED_NAMES
		};
		public static final String[][] FIELD_TYPES = {
			APPCONF.FIELD_TYPES, APPDOWN.FIELD_TYPES
		}; 
	}
}
