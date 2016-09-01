package com.game.hall.download.util;

import java.io.File;

public class UGConstant {
	
    /** ****二次加密的key*/
    public static final String UXGAME_KEY = "23450000"; 
    // loading图存放路径
    public static final String imgDownload = File.separator + "ugame" + File.separator + "ugameSDK" + File.separator + "images" + File.separator;		
	
	/**
	 * @description 接口
	 * @version 1.0
	 * @author hzy
	 * @update 2014-10-11 上午10:55:57
	 */
	public interface URL {
		/** 游戏详情 */
		public static String		DETAILS_GAME		= "/gamestore/v10/gamedetail/getGameDetail.do";
		/** 游戏分享 */
		public static String		SEND_SHARE			= "/share/addShare.do";
		/** 游戏评论 */
		public static String		DETAILS_COMMENT		= "/gamestore/v10/gamedetail/getGameComment.do";
		/** 评论 */
		public static String		DETAILS_REVIEWS		= "/gamestore/v10/gamedetail/getGameComments.do";
		/** 提交评论 */
		public static String		SEND_COMMENT		= "/gamestore/v10/gamedetail/submitComment.do";
		/** 获取单个游戏列表的礼包 */
		public static String		DETAILS_GIFTS		= "gamestore/v10/gift/getSingleGameGiftList.do";
		/** 积分兑换商品首页列表 */
		public static String		GOODS_LIST		= "/gamestore/v10/integral/getExGoodsList.do";
		/** 积分兑换商品详情 */
		public static String		GOODS_SHOW		= "/gamestore/v10/integral/getExGoodsInfo.do";
		/** 积分抽奖商品首页列表 */
		public static String		DRAW_LIST		= "/gamestore/v10/integral/getDrawGoodsList.do";
		/** 积分抽奖商品详情 */
		public static String		DRAW_SHOW		= "/gamestore/v10/integral/getDrawGoodsInfo.do";
		/** 进入抽奖界面，获取抽奖信息 */
		public static String		DRAW_PAGE		= "/gamestore/v10/integral/getDrawInfo.do";
		/** 点击抽奖，返回抽奖结果 */
		public static String		DRAW_RULT		= "/gamestore/v10/integral/getDrawRuleIntro.do";
		/** 点击抽奖，返回抽奖结果 */
		public static String		DRAW_RESULT		= "/gamestore/v10/integral/getDrawResult.do";
		/** 获取商品兑换订单信息 */
		public static String		GOODS_ORDER_OBTAIN	= "/gamestore/v10/integral/getExchangeOrder.do";
		/** 保存商品兑换订单 */
		public static String		GOODS_ORDER_SAVE	= "/gamestore/v10/integral/saveExchangeOrder.do";
		/** 读取商品抽奖订单信息 */
		public static String		DRAW_ORDER_OBTAIN	= "/gamestore/v10/integral/getDrawOrder.do";
		/** 保存商品抽奖订单信息 */
		public static String		DRAW_ORDER_SAVE		= "/gamestore/v10/integral/saveDrawOrder.do";
		/** 积分介绍 */
		public static String		INTEGRAL_INTRO		= "/gamestore/v10/integral/getIntegralRuleIntro.do";
		/** 查看积分获得明细 */
		public static String		INTEGRAL_OBTAIN_DETAIL		= "/gamestore/v10/integral/getIntegralOutputDetail.do";
		/** 查看积分消费明细 */
		public static String		INTEGRAL_CONSUME_DETAIL		= "/gamestore/v10/integral/getIntegralExpendDetail.do";
		/** 用户中心--用户信息 */
		public static String		CENTER_USER		= "/gamestore/v10/manager/getUserInfo.do";
		/** 是否有新消息 */
		public static String		MSG_CHECK		= "/gamestort/v10/message/checkNewMessage.do";
		/** 获取新消息 */
		public static String		MSG_OBTAIN		= "/gamestore/v10/message/getNewMessageList.do";
		/** 获取个人信息 */
		public static String		PERSONAL_OBTAIN		= "/gamestore/v10/personal/getPersonalDatum.do";
		/** 保存个人信息 */	    
		public static String		PERSONAL_SAVE		= "/gamestore/v10/personal/saveUserInfo.do";
		
		/** 商品点击 */
		public static String		GOODS_CLICK	= "/gamestore/v10/rpt/goodsClickRpt.do";
		
		/** 首页推荐 */
		public static final String	HOME_RECOMMEND		= "/gamestore/v10/home/index.do";
		/** 检测新游戏 */
	    public static final String CHECK_NEW_GAME = "/gamestore/v10/home/checkNewGame.do";
	    /** 检测新活动 */
	    public static final String CHECK_NEW_ACT = "/gamestore/v10/home/checkNewActivity.do";
	    /** 活动中心 */
	    public static final String GET_ACT_LIST = "/gamestore/v10/activity/getWapActivityList.do";
	    /** 最新游戏列表 */
	    public static final String GET_NEWEST_LIST = "/gamestore/v10/home/getNewestGameList.do";
	    /** 排行列表 */
	    public static final String GET_RANKING_LIST = "/gamestore/v10/home/getRankingGameList.do";
	    /** 网游首页数据 */
	    public static final String GET_ONLINE_HOME = "/gamestore/v10/gamelist/getOnlineGameList.do";
	    /** 网游新服 */
	    public static final String GET_NEW_SERVER = "/gamestore/v10/gameserver/getGameNewServerList.do";
	    /** 网游活动 */
	    public static final String GET_ONLINE_ACT = "/gamestore/v10/information/getInformationList.do";
	    /** 网游热门礼包 */
	    public static final String GET_ONLINE_HOT_GIFTS = "/gamestore/v10/gift/getGameGiftList.do";
	    /** 网游我的礼包 */
	    public static final String GET_ONLINE_PERSONAL_GIFTS = "/gamestore/v10/gift/getUserGameGiftList.do";
	    /** 专题列表 */
	    public static final String GET_SUBJECT_LIST = "/gamestore/v10/subject/getSubjectGameList.do";
	    /** 分类列表 */
	    public static final String GET_SORT_LIST = "/gamestore/v10/category/getCategoryList.do";
	    /** 分类详情列表 */
	    public static final String GET_SORT_DETAIL_LIST = "/gamestore/v10/category/getGameList.do";
	    /** 风云榜关键字 */
	    public static final String GET_BILLBOARD_KEYWORD = "/gamestore/v10/gamesearch/getGameSearchKeywords.do";
	    /** 搜索结果 */
	    public static final String GET_SEARCH_RESULT = "/gamestore/v10/gamesearch/searchGameList.do";
	    /** 搜索联想关键字 */
	    public static final String GET_SEARCH_KEYWORD = "/gamestore/v10/gamesearch/searchGameList.do";
	    
	    /** tab点击或切换  */
		public static final String TAB_CLICK_RRT = "/gamestore/v10/rpt/tabClickRpt.do";
		 /** 游戏点击数据上报*/
	    public static final String GAME_CLICK_RPT = "/gamestore/v10/rpt/gameDataRpt.do";
	    /**轮播图上报入口*/
	    public static final String PIC_CLICK_RRT = "/gamestore/v10/rpt/picClickRpt.do";
	    /**引导栏上报入口*/
	    public static final String GUIDE_CLICK_RRT = "/gamestore/v10/rpt/guideClickRpt.do";
	    /**专题喜欢不喜欢上报*/
	    public static final String SUBJECT_LIKE_CLICK = "/gamestore/v10/rpt/chooseSubjectRpt.do";
	    /** 分类点击上报 */
	    public static final String CATE_CLICK_RPT = "/gamestore/v10/rpt/cateClickRpt.do";
	    /** 今日推荐下载点击上报 */
	    public static final String TODAY_RECOM_DOWN_CLICK = "/gamestore/v10/rpt/chooseDownRpt.do";
	}
	
	/**
	 * @description 常量
	 * @version 1.0
	 * @author hzy
	 * @update 2014-10-11 上午10:55:57
	 */
	public interface CONTANTS {
		// Intent变量
		public static String	GMAEID			= "gameId";
		public static String	GMAENAME		= "gameName";
		public static String	GMAERPTKEY		= "gameRptkey";
		public static String	BIGIMGURLS		= "bigImgUrls";
		public static String	BIGIMGPOSITION	= "bigImgPostion";
		public static String	WAPURL			= "wapUrl";
		public static String	WAPTITLE		= "wapTitle";
		public static String	WAPHIDETITLE	= "wapHideTitle";
		public static String	WAPBACKHOME		= "wapBackHome";
		public static String	GOODS_INFO		= "goodsInfo";
		public static String	GOODS_ID		= "goodsId";
		public static String	GOODS_NAME		= "goodsName";
		public static String	GOODS_TYPE		= "goodsType";		// 商品是抽奖还是兑换
		public static String	GOODS_RPTKEY	= "goodsRptkey";
		public static String	ORDER_ID		= "orderId";
		public static String	MY_INTEGRAL		= "myIntegral";
		public static String	PRIZES_SCORE	= "prizesIntegral";
		public static String	SUBJECT_ID		= "subjectId";
		public static String	SUBJECT_NAME	= "subjectName";
		public static String	COMMENT_CUE		= "commentCue";
		public static String	COMMENT_FINSH	= "commentFinsh";
		public static String	COMMENT_STAR	= "commentStar";
		public static String	COMMENT_CONTENT	= "commentContent";
		public static String	COMMENT_TIME	= "commentTime";
		public static String	GIFT_DATA		= "giftData";
		public static String CATE_ID ="cateId";     //分类详情页面参数
		public static String CATE_NAME ="cateName"; //分类详情页面参数
		public static String CATE_TYPE ="cateType"; //分类详情页面参数
		
	}
	
	/**
	 * @description 判断码
	 * @version 1.0
	 * @author hzy
	 * @update 2014-10-11 上午10:55:57
	 */
	public interface ResultCode {
		public static int	SUCCESS				= 100;	// 服务端返回的成功状态码
		/**
		 * code = 500 客服端提示"服务器网络繁忙,请稍后再试" code = other 客服端提示"当前加载不给力,请稍后再试"
		 */
		public static int	FAIL				= 500;	// 服务端返回的失败状态码,
														
		public static int	ERROR				= 1000; // 自定义--服务端返回json为空的时候
		public static int	JSONERROR			= 1001; // 自定义--json数据错误
		public static int	DECODEFAIL			= 1002; // 自定义--转义decode失败
		public static int	NODATA				= 1003; // 服务器响应成功，但是列表数据为空
														
		/**
		 * 页面判断码
		 */
		public static int	RESULT_OK			= 0;	// 自定义--页面数据正确
		public static int	RESULT_FAIL			= 1;	// 自定义--页面数据不正确
		public static int	RESULT_MORE_OK		= 2;	// 自定义--页面数据正确(特指非第一页)
		public static int	RESULT_MORE_FAIL	= 3;	// 自定义--页面数据不正确(特指非第一页)
	}
	
	public interface NetWork {
		public static int	NET_NO		= 0;
		public static int	NET_UNKNOWN	= 1;
		public static int	NET_2G		= 2;
		public static int	NET_3G		= 3;
		public static int	NET_WIFI	= 4;
		public static int	NET_4G		= 5;
	}
	
	public interface Encoding {
		public static String	UTF_8	= "UTF-8";
	}
	
	public static final float	STAR_BAR	= 10f;
	
	/** 引导栏 */
	public interface Guide {
		public static int	guide_new				= 10;	// 首页最新
		public static int	guide_order				= 11;	// 首页排行
		public static int	guide_online			= 12;	// 首页网游
		public static int	guide_act				= 13;	// 首页活动
		public static int	guide_online_gift		= 14;	// 网游礼包
		public static int	guide_online_act		= 15;	// 网游活动
		public static int	guide_online_new_server	= 16;	// 网游新服
	}
	
	/** 自定义的广播action */
	public interface IntentAction {
		public static String	CANCLE_LOADING_IMG	= "com.ugame.sample.gamestore.action.GONE";	// 隐藏loading图
		public static String	GIFT_CODE_IMG		= "com.ugame.sample.gamestore.action.giftcode"; // 隐藏loading图
		public static String	ACTION_EARN		= "com.ugame.sample.gamestore.action.earn"; // 登陆
	}
	
	/** 排行榜类型 0:新热榜 1:下载榜 2:网游榜 */
	public static final int		rank_new_hot			= 0;
	public static final int		rank_down				= 1;
	public static final int		rank_online				= 2;
	
	/** tabid定义 */
	public static final String	tab_recom				= "119";					// 推荐
	public static final String	tab_goods				= "";					// 商品兑换列表
	public static final String	tab_draw				= "";					// 商品抽奖列表
	public static final String tab_online = "125"; //网游
	public static final String tab_hot_gift = "135"; //热门礼包
	public static final String tab_my_gift = "136"; //热门礼包
	/*
	 * 119：推荐 120：分类 121：积分 122：管理 123：最新 124：排行 125：网游 126：活动 127：新热榜 128：下载榜
	 * 129：网游榜 130：积分商城 131：积分抽奖 132：礼包 133：活动 134：今日开服 135：热门礼包 136：我的礼包
	 */
	
	/**
	 * 轮播图跳转类型
	 */
	public static final String clicktype_detail = "1";  //详情 
    public static final String clicktype_big_img = "2"; //图片放大
    public static final String clicktype_wap = "3";     //进入网站
    public static final String clicktype_tab = "4";     //tabid
    public static final String clicktype_subject = "5"; //进入专题
    public static final String clicktype_exchange = "6";//商品兑换
    public static final String clicktype_lottery = "7"; //抽奖
	
	/** sharePre常量*/
	public static final String PREF_USER_LOGIN		= "pref_user_login"; //preference名字
    public static final String sp_userLogin_tid = "userLogin_tid"; //终端id
    public static final String sp_userLogin_username = "userLogin_username";//用户名
    public static final String sp_userLogin_m = "userLogin_m"; // 一些必备信息
    public static final String sp_userLogin_channelid = "userLogin_channelid";//channelId
    public static final String sp_userLogin_appid = "userLogin_appid"; //appid
    public static final String sp_userExit_flag = "exit_flag"; //退出标识
    public static final String sp_new_game = "newest_time"; //保存上次检查最新的更新时间
    public static final String sp_new_act = "act_time";   //保存上次检查活动的更新时间
    public static final String sp_gift = "gift_";         //每个账户对应的礼包id的前缀
    public static final String sp_subject = "subject_";   //每个账户对应的专题id的前缀
    public static final String sp_todayrecom_date = "todayrecom";//保存今日推荐弹出的时间
    public static final String sp_search_history = "searchhistory";//搜索历史记录保存
    public static final String sp_keyword_date = "keyword_date";//保存上次获取（搜索自动提示）关键字的时间
    
    public static final String PREF_MANAGE		= "pref_manage"; //preference名字
    public static final String sp_manage_exit = "manage_exit";//保存上次获取（搜索自动提示）关键字的时间
    public static final String sp_manage_loadimg = "manage_loadimg";//保存上次获取（搜索自动提示）关键字的时间
    public static final String sp_manage_msg_time = "manage_msg_time";//保存上次获取（搜索自动提示）关键字的时间
    public static final String sp_manage_msg_newStatus = "manage_msg_newStatus";//保存上次获取（搜索自动提示）关键字的时间
    public static final String sp_manage_msg_red = "manage_msg_red";//保存上次获取（搜索自动提示）关键字的时间
    
    /**
	 * preference保存当前扫描的进程
	 */
	public static final String PREFERENCE_RUNING = "pref_running";
	
	/**
	 * preference记录上次补报时间、扫描时间
	 */
	public static final String PREFERENCE_LOG_WATCH = "logWatchTime";
//	public static final String PREF_WATCH_TIME = "lastProcStartTime";
	public static final String PREF_LAST_BROAD_TIME = "lastBroadTime";
	public static final String PREF_LAST_SEND_TIME = "lastSendTime";
    
    /** 上报游戏数据时的操作类型*/
	public static final String EVENT_CLICK = "0";        // 点击事件
	public static final String EVENT_DOWN_REQ = "11";    // 下载请求
	public static final String EVENT_DOWN_SUC = "1";     // 下载成功
	public static final String EVENT_INSTALL_SUC = "2";  // 安装
	public static final String EVENT_OPEN = "3";         // 打开，
	
	/** result页面跳转码*/
	public static final int  DETAIL_REVIEW = 601;//详情页跳转评论页面
	public static final int  DETAIL_REVIEW_SUBMIT = 602;//跳转评论提交
	
	
}
