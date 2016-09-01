package com.game.hall.download.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.game.hall.download.download.conf.UGAppConf;

import java.util.ArrayList;
import java.util.List;


public class UGAppConfMgmt
{ 
	public static long insert(Context context, UGAppConf conf)
	{
		UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, UGDatabaseDefine.DataBase.TB_NAMES, UGDatabaseDefine.DataBase.FIELD_NAMES,
				UGDatabaseDefine.DataBase.FIELD_TYPES);

		ContentValues cv = new ContentValues();
		cv.put(UGDatabaseDefine.APPCONF.GAMEID, conf.gameId);
		cv.put(UGDatabaseDefine.APPCONF.FILENAME, conf.fileName);
		cv.put(UGDatabaseDefine.APPCONF.FILESIZE, conf.fileSize);
		cv.put(UGDatabaseDefine.APPCONF.DOWNLOADSIZE, conf.downloadSize);
		cv.put(UGDatabaseDefine.APPCONF.PERCENT, conf.percent);
		cv.put(UGDatabaseDefine.APPCONF.FILEDIRECTORY, conf.fileDirectory);
		cv.put(UGDatabaseDefine.APPCONF.STATE, conf.state);
		cv.put(UGDatabaseDefine.APPCONF.URL, conf.url);
		cv.put(UGDatabaseDefine.APPCONF.PACKAGENAME, conf.packageName);
		cv.put(UGDatabaseDefine.APPCONF.VERSIONCODE, conf.versionCode);
		cv.put(UGDatabaseDefine.APPCONF.APPNAME, conf.appName);
		cv.put(UGDatabaseDefine.APPCONF.APPICON, conf.appIcon);
		cv.put(UGDatabaseDefine.APPCONF.APPRPTKEY, conf.rptKey);

		return helper.insert(UGDatabaseDefine.APPCONF.TB_NAME, cv);
	}
	
	public static void inserts(Context context, List<UGAppConf> confs)
	{
		if (confs == null || confs.isEmpty())
		{
			return;
		}
		
		UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, 
				UGDatabaseDefine.DataBase.TB_NAMES, UGDatabaseDefine.DataBase.FIELD_NAMES,UGDatabaseDefine.DataBase.FIELD_TYPES);

		ArrayList<ContentValues> cvs = new ArrayList<ContentValues>();
		for (UGAppConf conf : confs)
		{
			ContentValues cv = new ContentValues(); 
			cv.put(UGDatabaseDefine.APPCONF.GAMEID, conf.gameId);
			cv.put(UGDatabaseDefine.APPCONF.FILENAME, conf.fileName);
			cv.put(UGDatabaseDefine.APPCONF.FILESIZE, conf.fileSize);
			cv.put(UGDatabaseDefine.APPCONF.DOWNLOADSIZE, conf.downloadSize);
			cv.put(UGDatabaseDefine.APPCONF.PERCENT, conf.percent);
			cv.put(UGDatabaseDefine.APPCONF.FILEDIRECTORY, conf.fileDirectory);
			cv.put(UGDatabaseDefine.APPCONF.STATE, conf.state);
			cv.put(UGDatabaseDefine.APPCONF.URL, conf.url);
			cv.put(UGDatabaseDefine.APPCONF.PACKAGENAME, conf.packageName);
			cv.put(UGDatabaseDefine.APPCONF.VERSIONCODE, conf.versionCode);
			cv.put(UGDatabaseDefine.APPCONF.APPNAME, conf.appName);
			cv.put(UGDatabaseDefine.APPCONF.APPICON, conf.appIcon);
			cv.put(UGDatabaseDefine.APPCONF.APPRPTKEY, conf.rptKey);
			cvs.add(cv);
		}

		helper.inserts(UGDatabaseDefine.APPCONF.TB_NAME, cvs);
	}
 
	public static int delete(Context context, String[] whereArgs, String[] whereValues)
	{
		if (whereArgs == null || whereValues == null || whereArgs.length != whereValues.length)
		{
			return 0;
		}
		
		try
		{
			UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, UGDatabaseDefine.DataBase.TB_NAMES,
					UGDatabaseDefine.DataBase.FIELD_NAMES, UGDatabaseDefine.DataBase.FIELD_TYPES);
			String where = "";
			for (int i = 0; i < whereArgs.length - 1; i++)
			{
				where += whereArgs[i];
				where += "=? and ";
			}
			where += whereArgs[whereArgs.length - 1];
			where += "=?";
			return helper.delete(UGDatabaseDefine.APPCONF.TB_NAME, where, whereValues);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 删除多条数据
	 * @param context
	 * @param whereArgs
	 * @param whereValues
	 */
	public static void deletes(Context context, ArrayList<String[]> whereArgs, ArrayList<String[]> whereValues)
	{
		if (whereArgs == null || whereValues == null || whereArgs.isEmpty() || whereValues.isEmpty())
		{
			return ;
		}
		
		int len = whereArgs.size();
		if (len != whereValues.size())
		{
			return;
		}
		
		try
		{
			UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, UGDatabaseDefine.DataBase.TB_NAMES,
					UGDatabaseDefine.DataBase.FIELD_NAMES, UGDatabaseDefine.DataBase.FIELD_TYPES);
			 
			ArrayList<String> wheres = new ArrayList<String>();
			for (int i = 0; i < len; i++)
			{
				String[] args = whereArgs.get(i);
				String where = "";
				for (int j = 0; j < args.length - 1; j++)
				{
					where += args[j];
					where += "=? and ";
				}
				where += args[args.length - 1];
				where += "=?";
				wheres.add(where);
			}
			
			helper.deletes(UGDatabaseDefine.APPCONF.TB_NAME, wheres, whereValues); 
		}
		catch (Exception e)
		{
			e.printStackTrace(); 
		}
	}
 
	public static boolean deleteAll(Context context)
	{
		UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, UGDatabaseDefine.DataBase.TB_NAMES, UGDatabaseDefine.DataBase.FIELD_NAMES,
				UGDatabaseDefine.DataBase.FIELD_TYPES);
		return helper.execSQL("delete from " + UGDatabaseDefine.APPCONF.TB_NAME);
	}
 
	public static int update(Context context, UGAppConf conf, String[] whereArgs, String[] whereValues)
	{
		if (whereArgs == null || whereValues == null || whereArgs.length != whereValues.length)
		{
			return 0;
		}

		try
		{
			String where = "";
			for (int i = 0; i < whereArgs.length - 1; i++)
			{
				where += whereArgs[i];
				where += "=? and ";
			}
			where += whereArgs[whereArgs.length - 1];
			where += "=?";
			return update(context, conf, where, whereValues);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
 
	public static List<UGAppConf> getConfs(Context context, String[] whereArgs, String[] whereValues)
	{
		List<UGAppConf> infos = new ArrayList<UGAppConf>();
		if (whereArgs == null || whereValues == null || whereArgs.length != whereValues.length)
		{
			return infos;
		}

		try
		{
			String where = "";
			for (int i = 0; i < whereArgs.length - 1; i++)
			{
				where += whereArgs[i];
				where += "=? and ";
			}
			where += whereArgs[whereArgs.length - 1];
			where += "=?";
			
			return getDonwloadInfo(context, where, whereValues, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return infos;
		}
	}

	private static List<UGAppConf> getDonwloadInfo(Context context, String selection, String[] selectionArgs, String groupBy)
	{
		List<UGAppConf> infos = new ArrayList<UGAppConf>();
		UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, UGDatabaseDefine.DataBase.TB_NAMES, UGDatabaseDefine.DataBase.FIELD_NAMES,
				UGDatabaseDefine.DataBase.FIELD_TYPES);
		Cursor cursor = helper.select(UGDatabaseDefine.APPCONF.TB_NAME, UGDatabaseDefine.APPCONF.FILED_NAMES, selection, selectionArgs, groupBy, null, null);
		try
		{
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext())
			{
				UGAppConf Info = new UGAppConf();
				// Info.id = cursor.getLong(0);
				Info.gameId = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.GAMEID));
				Info.fileName = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.FILENAME)); 
				Info.fileSize = cursor.getLong(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.FILESIZE));
				Info.downloadSize = cursor.getLong(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.DOWNLOADSIZE));
				Info.percent = cursor.getInt(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.PERCENT));
				Info.fileDirectory = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.FILEDIRECTORY));
				Info.state = cursor.getInt(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.STATE));
				Info.packageName = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.PACKAGENAME));
				Info.versionCode = Integer.parseInt(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.VERSIONCODE)));
				Info.url = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.URL));
				Info.appName = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.APPNAME));
				Info.appIcon = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.APPICON));
				Info.rptKey = cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.APPRPTKEY));
				infos.add(Info);
//				Log.i("db: ", Info.packageName + " " + Info.versionCode + " " + Info.state + " " + Info.percent);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (cursor != null)
				{
					cursor.close();
				}
				
				if (helper != null)
				{
					helper.close();
				}
			}
			catch (Exception e)
			{ 
				e.printStackTrace();
			}
		}

		return infos;
	}

	private static int update(Context context, UGAppConf conf, String where, String[] whereValues)
	{
		UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, UGDatabaseDefine.DataBase.TB_NAMES, UGDatabaseDefine.DataBase.FIELD_NAMES,
				UGDatabaseDefine.DataBase.FIELD_TYPES);

		ContentValues cv = new ContentValues();
		if (conf.gameId != null)
		{
			cv.put(UGDatabaseDefine.APPCONF.GAMEID, conf.gameId);
		}
		if (conf.fileName != null)
		{
			cv.put(UGDatabaseDefine.APPCONF.FILENAME, conf.fileName);
		}
		if (conf.fileSize > 0)
		{
			cv.put(UGDatabaseDefine.APPCONF.FILESIZE, conf.fileSize);
		}
		if (conf.downloadSize > 0)
		{
			cv.put(UGDatabaseDefine.APPCONF.DOWNLOADSIZE, conf.downloadSize);
		}
		if (conf.percent > 0)
		{
			cv.put(UGDatabaseDefine.APPCONF.PERCENT, conf.percent);
		}
		if (conf.fileDirectory != null)
		{
			cv.put(UGDatabaseDefine.APPCONF.FILEDIRECTORY, conf.fileDirectory);
		}
		if (conf.url != null)
		{
			cv.put(UGDatabaseDefine.APPCONF.URL, conf.url);
		}
		if (conf.packageName != null)
		{
			cv.put(UGDatabaseDefine.APPCONF.PACKAGENAME, conf.packageName);
		}
		if (conf.versionCode > 0)
		{
			cv.put(UGDatabaseDefine.APPCONF.VERSIONCODE, String.valueOf(conf.versionCode));
		}
		
		return helper.update(UGDatabaseDefine.APPCONF.TB_NAME, cv, where, whereValues);
	}
	
	
	/**
	public static List<AppBean> getPacknameDMData(Context context,String selection, String[] selectionArgs, String groupBy){
		List<AppBean> appList = new ArrayList<AppBean>();
		UGDatabaseHelper helper = new UGDatabaseHelper(context, UGDatabaseDefine.DataBase.DB_NAME, UGDatabaseDefine.DataBase.DB_VERSION, UGDatabaseDefine.DataBase.TB_NAMES, UGDatabaseDefine.DataBase.FIELD_NAMES,
				UGDatabaseDefine.DataBase.FIELD_TYPES);
		Cursor cursor = helper.select(UGDatabaseDefine.APPCONF.TB_NAME, UGDatabaseDefine.APPCONF.FILED_NAMES, selection, selectionArgs, groupBy, null, null);
		AppBean appBean = null;
		try {
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
				appBean = new AppBean();
//				appBean.setSetupstatus(cursor.getInt(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.STATE)));
				appBean.setGameid(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.APPID)));
				appBean.setAppname(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.APPNAME)));
				appBean.setApppackagename(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.PACKAGENAME)));
				appBean.setAppicon(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.APPICON)));
				appBean.setApplink(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.URL)));
				appBean.setAppsize(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.FILESIZE)));
				appBean.setRptkey(cursor.getString(cursor.getColumnIndex(UGDatabaseDefine.APPCONF.APPRPTKEY)));
				appList.add(appBean);
			}
		} catch (Exception e)
		{
			appList = null;
		}
		finally
		{
			try
			{
				if (cursor != null)
				{
					cursor.close();
				}
				
				if (helper != null)
				{
					helper.close();
				}
			}
			catch (Exception e)
			{ 
				e.printStackTrace();
			}
		}

		return appList;
	}
	**/
}
