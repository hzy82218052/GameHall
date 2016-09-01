package com.game.hall.download.util;

public class UGLog
{
	public static boolean SHOW_LOG = true;// 不需要显示日志时，将该值设为false
	public final static String LOGTAG = "UXGame_6.0";

	public static void v(String log)
	{
		if (log == null)
		{
			return;
		}
		
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.v(LOGTAG, log);
	}

	public static void d(String log)
	{
		if (log == null)
		{
			return;
		}
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.d(LOGTAG, log);
	}

	public static void d(String tag, String log)
	{
		if (log == null)
		{
			return;
		}
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.d(tag, log); 
	}

	public static void i(String log)
	{
		if (log == null)
		{
			return;
		}
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.i(LOGTAG, log);
	}

	public static void e(String log)
	{
		if (log == null)
		{
			return;
		}
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.e(LOGTAG, log); 
	} 

	public static void v(String tag, String log)
	{
		if (log == null)
		{
			return;
		}
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.v(tag, log);
	}

	public static void i(String tag, String log)
	{
		if (log == null)
		{
			return;
		}
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.i(tag, log); 
	}

	public static void e(String tag, String log)
	{
		if (log == null)
		{
			return;
		}
		if (!SHOW_LOG)
		{
			return;
		}
		
		android.util.Log.e(tag, log); 
	} 
}