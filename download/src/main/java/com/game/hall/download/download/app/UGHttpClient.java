package com.game.hall.download.download.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.game.hall.download.download.conf.DownThread;
import com.game.hall.download.download.conf.UGAppConf;
import com.game.hall.download.util.MD5;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.SocketException;

import javax.net.ssl.SSLHandshakeException;

public class UGHttpClient {
	private static final int BUFFER_SIZE = 1024 * 4;
	private static final String TAG = "*** DOWNLOAD_UGHttpClient ***";

	/** 内存文件路径 */
	public UGHttpClient() {
		;
	}

	public synchronized static DefaultHttpClient getHttpClient(Context mContext) {

		HttpParams params = new BasicHttpParams();
		ConnectivityManager connMgr = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) {
			if (activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				String proxyHost = android.net.Proxy.getDefaultHost();
				if (proxyHost != null) {
					HttpHost httpHost = new HttpHost(proxyHost,
							android.net.Proxy.getDefaultPort());
					params.setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
				}
			}
		} else {
			return null;
		}

		// 设置一些基本参数
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(params, true);
		// 超时设置
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(params, 5 * 1000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, 60 * 1000);
		ConnManagerParams.setMaxConnectionsPerRoute(params,
				new ConnPerRouteBean(30));
		ConnManagerParams.setMaxTotalConnections(params, 200);
		HttpProtocolParams.setUseExpectContinue(params, true);
		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		// schReg.register(new Scheme("https", SSLSocketFactory
		// .getSocketFactory(), 443));

		// 使用线程安全的连接管理来创建HttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient(
				new ThreadSafeClientConnManager(params, schReg), params);
		httpClient.setHttpRequestRetryHandler(requestRetryHandler);

		return httpClient;
	}

	// public static void startDown(Context context, UGAppConf conf,
	// DownLoadTaskCallBack callback)
	// {
	// long curPosition = 0;
	//
	// InputStream is = null;
	// BufferedInputStream bis = null;
	// RandomAccessFile fos = null;
	// byte[] buf = new byte[BUFFER_SIZE];
	// HttpResponse response = null;
	// HttpGet get = null;
	// try
	// {
	// fos = initFile(context, conf);
	// if (fos == null)
	// {
	// return;
	// }
	//
	// HttpClient client = getHttpClient(context);
	// get = new HttpGet(conf.url);
	// get.setHeader("User-Agent", "jzad_sdk_android");
	// get.setHeader("Referer", conf.url);
	// get.setHeader("Range", "bytes=" + conf.downloadSize + "-");
	//
	// response = client.execute(get);
	// int code = response.getStatusLine().getStatusCode();
	// //Content-Length
	// Header[] headers = response.getHeaders("Content-Length");
	// long contentLength = 0;
	// if(headers.length > 0)
	// contentLength = Long.valueOf(headers[0].getValue());
	// if (code != HttpStatus.SC_OK && code != 206)
	// {
	// return;
	// }
	// conf.fileSize = conf.downloadSize +
	// response.getEntity().getContentLength() - 1;
	//
	// Log.i("httpclient: ", String.valueOf(contentLength) +
	// "_"+String.valueOf(conf.fileSize) + "_" + conf.downloadSize);
	// is = response.getEntity().getContent();
	// bis = new BufferedInputStream(is);
	//
	// int index = 0;
	// int len = 0;
	// while ((len = bis.read(buf, 0, BUFFER_SIZE)) != -1)
	// {
	// if (conf.state != UGAppConf.STATE_DOWNING || conf.isExit)
	// {
	// return;
	// }
	//
	// // Log.i("httpclient: ", String.valueOf(conf.fileSize) + "___" +
	// conf.downloadSize);
	// fos.write(buf, 0, len);
	// curPosition += len;
	// if (curPosition > conf.fileSize)
	// {
	// conf.downloadSize += len - (curPosition - conf.fileSize) + 1;
	// }
	// else
	// {
	// conf.downloadSize += len;
	// }
	// conf.percent = (int) ((conf.downloadSize * 100) / conf.fileSize);
	// if (conf.percent > index)
	// {
	// callback.onProgressUpdate(conf.percent);
	// }
	// index = conf.percent;
	//
	// }
	// is.close();
	// }
	// catch (Exception e)
	// {
	// callback.onProgressUpdate(conf.percent);
	// }
	// finally
	// {
	// try
	// {
	// if (bis != null)
	// {
	// bis.close();
	// }
	// if (fos != null)
	// {
	// fos.close();
	// }
	// if (get != null)
	// {
	// get.abort();
	// }
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }

	public static void startDown(Context context, UGAppConf conf,
								 UGAppConf.DownLoadTaskCallBack callback, DownThread dh) {
		long startPosition = conf.downloadSize;
		long curPosition = 0;
		Log.e("wxue","---startDown()---开始下载起点--" + startPosition);
     
		InputStream is = null;
		BufferedInputStream bis = null;
		RandomAccessFile fos = null;
		byte[] buf = new byte[BUFFER_SIZE];
		HttpResponse response = null;
		HttpGet get = null;
		try {
			fos = initFile(context, conf,startPosition);
			if (fos == null) {
				return;
			}

			HttpClient client = getHttpClient(context);
			HttpHead httpHead = new HttpHead(conf.url);
			if(!dh.isRunning()){
				return;
			}
			response = client.execute(httpHead);
			// 获取HTTP状态码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return;
			}

			// Content-Length
			Header[] headers = response.getHeaders("Content-Length");
			if (headers.length > 0)
				conf.fileSize = Long.valueOf(headers[0].getValue()) - 1;

			httpHead.abort();
			if (conf.fileSize <= 0) {
				return;
			}

			get = new HttpGet(conf.url);
			get.setHeader("User-Agent", "jzad_sdk_android");
			get.setHeader("Referer", conf.url);
			get.setHeader("Range", "bytes=" + startPosition + "-");
			
			if(!dh.isRunning()){
				return;
			}
			response = client.execute(get);
			int code = response.getStatusLine().getStatusCode();
			if (code != HttpStatus.SC_OK && code != 206) {
				return;
			}

			is = response.getEntity().getContent();
			bis = new BufferedInputStream(is);
			Log.e("wxue", "---code = " + code + " conf.state = " + conf.state
					+ " running = " + dh.isRunning());
			int index = 0;
			int len = 0;
			while ((len = bis.read(buf, 0, BUFFER_SIZE)) != -1) {
				if (conf.state != UGAppConf.STATE_DOWNING || !dh.isRunning()) {
					Log.e("wxue", "---暂停下载---dh = " + dh);
					return;
				}

				fos.write(buf, 0, len);
				curPosition += len;
				if (curPosition > conf.fileSize) {
					Log.e("wxue", "---curPosition > conf.fileSize");
					conf.downloadSize += len - (curPosition - conf.fileSize)
							+ 1;
				} else {
					conf.downloadSize += len;
				}
				conf.percent = (int) ((conf.downloadSize * 100) / conf.fileSize);
				if (conf.percent > index) {
					callback.onProgressUpdate(conf.percent);
				}
				index = conf.percent;
			}
			is.close();

		} catch (Exception e) {
			callback.onProgressUpdate(-1);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (get != null) {
					get.abort();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static RandomAccessFile initFile(Context context, UGAppConf conf, long startPostion) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			conf.fileDirectory = File.separator + "data" + File.separator
					+ "data" + File.separator + context.getPackageName()
					+ File.separator + "files" + File.separator;
		} else {
			conf.fileDirectory = Environment.getExternalStorageDirectory()
					.getPath()
					+ File.separator
					+ "ugame"
					+ File.separator
					+ "ugameSDK"
					+ File.separator
					+ "downloads"
					+ File.separator;
		}

		conf.fileName = MD5.getMD5(conf.url) + ".apk";
		conf.fileDirectory += conf.fileName;

		RandomAccessFile randFile = null;
		File file = new File(conf.fileDirectory);

		try {
			if (file.exists() && startPostion == 0 && file.length() > 0) {
				file.delete();
			}

			if (!file.exists()) {
				String path = file.getPath();
				path = path.substring(0, path.lastIndexOf("/") + 1);
				File p = new File(path);
				if (!p.exists()) {
					p.mkdirs();
				}
				file.createNewFile();

			}
			randFile = new RandomAccessFile(file, "rw");
			randFile.seek(startPostion);

			Runtime.getRuntime().exec("chmod 644 " + file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return randFile;
	}

	/**
	 * 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		@Override
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试N次
			if (executionCount >= 3) {
				// 如果超过最大重试次数，那么就不要继续了
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// 如果服务器丢掉了连接，那么就重试
				return true;
			}

			if (exception instanceof SocketException) {
				// 如果服务器丢掉了连接，那么就重试
				return true;
			}

			if (exception instanceof SSLHandshakeException) {
				// 不要重试SSL握手异常
				return false;
			}

			if (exception instanceof Exception) {
				return true;
			}

			return false;
		}
	};
}
