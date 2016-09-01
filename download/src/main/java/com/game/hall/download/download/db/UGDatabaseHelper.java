package com.game.hall.download.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UGDatabaseHelper extends SQLiteOpenHelper
{ 
	private String[] tb_names; // 数据表名称
	private String[][] field_names; // 数据表字段名称
	private String[][] field_types; // 数据表字段类型 

	public UGDatabaseHelper(Context context, String db_name, int db_version, String[] tb_names, String[][] field_names, String[][] field_types)
	{
		super(context, db_name, null, db_version);
		
		this.tb_names = tb_names;
		this.field_names = field_names;
		this.field_types = field_types; 
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		for (int i = 0; i < tb_names.length; i++)
		{
			String createSQL = "CREATE TABLE IF NOT EXISTS ";
			createSQL += tb_names[i];
			createSQL += " (";
			for (int j = 0; j < field_names[i].length; j++)
			{
				createSQL += field_names[i][j] + " " + field_types[i][j] + ",";
			}
			createSQL = createSQL.substring(0, createSQL.length() - 1) + ")";
			db.execSQL(createSQL);
		} 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		for (int i = 0; i < tb_names.length; i++)
		{
			String dropSQL = "DROP TABLE IF EXISTS " + tb_names[i];
			db.execSQL(dropSQL);
		}
		onCreate(db);
	}
	
	/**
	 * 获取数据库版本
	 * @return
	 */
	public int getVersion()
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			int version = db.getVersion();
			db.close(); 
			return version;
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 查询数据
	 * 
	 * @param table 查询的 table name
	 * @param fieldNames 查询的数据的字段名称
	 * @param selection 查询条件字符串，如：field1 = ? and field2 = ?
	 * @param selectionArgs 查询的条件的值，如：["a","b"]
	 * @param groupBy groupBy后面的字符串，如：field1,field2
	 * @param having having后面的字符串
	 * @param orderBy orderBy后面的字符串
	 * @return Cursor 包含了取得数据的值
	 */
	public Cursor select(String table, String[] fieldNames, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
	{
		SQLiteDatabase db = this.getReadableDatabase(); 
		
		return db.query(table, fieldNames, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	/**
	 * 更新数据
	 * 
	 * @param tableName 更新数据的table name
	 * @param cv 更新数据的字段键值对
	 * @param where 更新数据的条件
	 * @param whereValues 更新数据的条件值
	 * @return int 更新的笔数
	 */
	public int update(String tableName, ContentValues cv, String where, String[] whereValues)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			int count = db.update(tableName, cv, where, whereValues);
			db.close();

			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 添加数据
	 * 
	 * @param tableName 添加数据的table name
	 * @param cv 添加数据的键值对
	 * @return long id 添加成功后的id
	 */
	public long insert(String tableName, ContentValues cv)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			long id = db.insert(tableName, null, cv);
			db.close();
			return id;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 添加多条数据
	 * 
	 * @param tableName 添加数据的table name
	 * @param cvs 添加数据的键值对 
	 */
	
	public void inserts(String tableName, ArrayList<ContentValues> cvs)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
//			long id = db.insert(tableName, null, cv);
			for (ContentValues cv : cvs)
			{
				db.insert(tableName, null, cv);
			}
			
			db.close(); 
		}
		catch (Exception e)
		{
			e.printStackTrace(); 
		}
	}
	
	/**
	 * 删除数据
	 * 
	 * @param tableName 删除数据的table name
	 * @param where 删除数据的条件
	 * @param whereValues 删除数据的条件值
	 * @return int 删除的笔数
	 */
	public int delete(String tableName, String where, String[] whereValues)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			int count = db.delete(tableName, where, whereValues);
			db.close();
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 删除多条数据
	 * 
	 * @param tableName 删除数据的table name
	 * @param wheres 删除数据的条件集
	 * @param whereValues 删除数据的条件值集 
	 */
	public void deletes(String tableName, ArrayList<String> wheres, ArrayList<String[]> whereValues)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			int len = wheres.size();
			for (int i = 0; i < len; i++)
			{
				int count = db.delete(tableName, wheres.get(i), whereValues.get(i));
//				Log.i("delete: ", String.valueOf(count));
			} 
			db.close(); 
		}
		catch (Exception e)
		{
			e.printStackTrace(); 
		}
	}

	/**
	 * 执行数据库的SQL语句
	 * 
	 * @param sql 语句
	 */
	public boolean execSQL(String sql)
	{
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			db.execSQL(sql);
			db.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	} 

	/**
	 * 关闭数据库
	 */
	@Override
	public void close()
	{
		super.close();
	}
}
