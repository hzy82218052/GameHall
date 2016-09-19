package com.game.hall.gamehall.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtil {
    // 用于匹配固定电话号码
    private final static String REGEX_FIXEDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
    // 用于获取固定电话中的区号
    private final static String REGEX_ZIPCODE = "^(010|02\\d|0[3-9]\\d{2})\\d{6,8}$";

    private final static String REGEX_ZERO_FIRST = "^0\\d*$";
    private final static String REGEX_WAYBILLNUMBER = "^\\d{3} \\d{3} \\d{3} \\d{3};?$";
    private final static String REGEX_CAPTCHA = "^\\d{6}$";

    private final static String CONTRACT_NAME_PATTERN = "^.*(`|~|!|@|#|\\$|%|\\^|&|\\*|\\(|\\)|_|-|\\+|=|\\{|\\}|\\[|\\]|;|:|\\.|,|/|\\?|'|\\||\"|\\\\)+.*$";

    private static Pattern PATTERN_FIXEDPHONE;
    private static Pattern PATTERN_ZIPCODE;
    private static Pattern PATTERN_ZERO_FIRST;
    private static Pattern PATTERN_WAYBILLNUMBER;

    private static long lastClickTime;

    /**
     * 验证手机号是否有效（大陆）
     */
    public static boolean isMobileValid(String mobile) {
        if (TextUtils.isEmpty(mobile) || !TextUtils.isDigitsOnly(mobile)) {
            return false;
        }

        return Pattern.matches("^(\\+86)?(1[3-9][0-9])\\d{8}$", mobile);
    }

    /**
     * 验证验证码是否有效
     *
     * @param captcha
     * @return
     */
    public static boolean isCaptchaValid(String captcha) {
        if (TextUtils.isEmpty(captcha) || !TextUtils.isDigitsOnly(captcha)) {
            return false;
        }
        return Pattern.matches(REGEX_CAPTCHA, captcha);
    }

    /**
     * 是否为12位运单号
     *
     * @param waybillNumber
     * @return
     */
    public static boolean IsWaybillNumber(String waybillNumber) {
        if (waybillNumber == null || waybillNumber.length() != 12) return false;
        Matcher match = PATTERN_WAYBILLNUMBER.matcher(waybillNumber);
        return match.matches();
    }

    /**
     * 判断是否为固定电话号码
     *
     * @param number 固定电话号码
     * @return
     */
    public static boolean isFixPhone(String number) {
        Matcher match = PATTERN_FIXEDPHONE.matcher(number.replace("-", ""));
        return match.matches();
    }

//    /**
//     * 判断是否0开头
//     *
//     * @param number
//     * @return
//     */
//    public static boolean isZeroFirst(String number) {
//        Matcher match = PATTERN_ZERO_FIRST.matcher(number);
//        return match.matches();
//    }

    /**
     * 获取星号电话
     *
     * @param mobile
     * @return
     */
    public static String getStarMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) return "";
        int length = mobile.length();
        if (length == 11) {
            StringBuilder builder = new StringBuilder();
            builder.append(mobile.substring(0, 3)).append("****").append(mobile.substring(7));
            return builder.toString();
        } else if (length > 7 && length < 11) {
            StringBuilder builder = new StringBuilder();
            builder.append(mobile.substring(0, 2)).append("****").append(mobile.substring(6));
            return builder.toString();
        } else {
            return mobile;
        }
    }

    /**
     * 验证邮箱规则
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) return false;

        String regex = "^([a-z0-9_\\.-]{1,50})@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        return Pattern.matches(regex, email);
    }

    /**
     * 判断固话是否合法
     *
     * @param fixPhone
     * @return
     */
    public static boolean isFixPhoneValid(String fixPhone) {
        if (TextUtils.isEmpty(fixPhone)) return false;

        String regex = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,5})?$";

        return Pattern.matches(regex, fixPhone);
    }

    /**
     * 获取固定号码号码中的区号
     *
     * @param strNumber
     * @return
     */
    public static String getZipFromFixphone(String strNumber) {
        Matcher matcher = PATTERN_ZIPCODE.matcher(strNumber);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    /**
     * 校正手机号码，比如： 去掉+86或特殊字符
     *
     * @param phoneNum
     * @return
     */
    public static String checkMobileNum(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) return "";

        Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}");
        Matcher m1 = p1.matcher(phoneNum);
        if (m1.matches()) {
            Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
            Matcher m2 = p2.matcher(phoneNum);
            StringBuffer sb = new StringBuffer();
            while (m2.find()) {
                m2.appendReplacement(sb, "");
            }
            m2.appendTail(sb);
            return sb.toString();
        } else {
            return phoneNum;
        }
    }

    /**
     * 过滤掉号码的非数字字符和"+86"
     *
     * @param phoneNum
     * @return
     */
    public static String filterMobileNum(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) return "";

        return checkMobileNum(phoneNum.replaceAll("[^0-9]", ""));
    }

    /**
     * 过滤掉非数字的字符
     *
     * @param text
     * @return
     */
    public static String filterNum(String text) {
        if (TextUtils.isEmpty(text)) return "";

        return text.replaceAll("[^0-9]", "");
    }

//    /**
//     * 过滤掉指定特殊字符, 留下数字、字母、汉字和有效字符
//     *
//     * @param text
//     * @return
//     */
//    public static String filterCharacterText(String text) {
//        if (TextUtils.isEmpty(text)) return "";
//
//        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(text);
//        return m.replaceAll("").trim();
//    }

    /**
     * 过滤掉特殊字符, 留下数字、字母、汉字
     *
     * @param text
     * @return
     */
    public static String filterCharacter(String text) {
        if (TextUtils.isEmpty(text)) return "";

        return text.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5]", "");

    }


//    /**
//     * 删除数组中指定下标元素
//     *
//     * @param src
//     * @param index
//     * @return
//     */
//    public static String[] changeArray(String[] src, int index) {
//        String[] dest = new String[src.length - 1];
//        System.arraycopy(src, 0, dest, 0, index);
//        System.arraycopy(src, index + 1, dest, index, dest.length - index);
//        return dest;
//    }

//    /**
//     * 删除数组中指定范围下标元素
//     *
//     * @param src
//     * @param fromIndex
//     * @param toIndex
//     * @return
//     */
//    public static String[] changeArray(String[] src, int fromIndex, int toIndex) {
//        String[] dest = new String[src.length - (toIndex - fromIndex + 1)];
//        System.arraycopy(src, 0, dest, 0, fromIndex);
//        System.arraycopy(src, toIndex + 1, dest, fromIndex, dest.length - fromIndex);
//        return dest;
//    }

    public static String getLocaleLanguage() {
        Locale l = Locale.getDefault();
        return String.format("%s-%s", l.getLanguage(), l.getCountry());
    }

    /**
     * 当前手机语言是否简体
     *
     * @return
     */
    public static boolean isZhCn() {
        String lan = getLocaleLanguage();
        // zh-CN表示简体中文
        return "zh-CN".equals(lan);
    }

    /**
     * 当前手机语言是否繁体
     *
     * @return
     */
    public static boolean isZhTw() {
        String lan = getLocaleLanguage();
        // zh-TW表示繁体中文
        return "zh-TW".equals(lan);
    }

    /**
     * 当前手机语言是否英文
     *
     * @return
     */
    public static boolean isEnglish() {
        String lan = getLocaleLanguage();
        // en-US(也可能是en-XX)表示英语
        return !TextUtils.isEmpty(lan) && lan.startsWith("en-");
    }

    /**
     * 判断当前是什么语言，code参考后台文档
     *
     * @return
     */
    public static String getLocaleLanCode() {
        if (isEnglish()) return "en";
        else if (isZhTw()) return "tc";
        else if (isZhCn()) return "sc";
        else return "en";
    }


    /**
     * 对double数据进行取精度.
     */
    public static double doubleValue(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(6, 6);// scale:精度位数(保留的小数位数). roundingMode: 精度取值方式
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    /**
     * 对double数据进行取精度.
     *
     * @param value
     * @return
     */
    public static double doubleValue(String value) {
        if (TextUtils.isEmpty(value))

            return 0;

        double dValue = 0;
        try {
            dValue = Double.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }

        return doubleValue(dValue);
    }


    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

//    /**
//     * 恢复光标
//     */
//    public static void recoverSelection(EditText editText) {
//        Editable editable = editText.getText();
//        String str = editable.toString().trim();
//        int selEndIndex = Selection.getSelectionEnd(editable);
//        // 截取新字符串
//        String newStr = str.substring(0, str.length() - 1);
//        editText.setText(newStr);
//        editable = editText.getText();
//
//        // 新字符串的长度
//        int newLen = editable.length();
//        // 旧光标位置超过字符串长度
//        if (selEndIndex > newLen) {
//            selEndIndex = editable.length();
//        }
//        // 设置新光标所在的位置
//        Selection.setSelection(editable, selEndIndex);
//    }


    /**
     * 隐藏输入法
     *
     * @param view
     */
    public static void hideInput(View view) {
        if (view == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0); // 隐藏
    }

    /**
     * 显示输入法
     *
     * @param view
     */
    public static void showInput(View view) {
        if (view == null) return;
        if (view instanceof EditText)
            view.setFocusable(true);
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 对double数据进行取精度.
     *
     * @param value        double数据.
     * @param scale        精度位数(保留的小数位数).
     * @param roundingMode 精度取值方式.
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

//    /**
//     * 推送消息免打扰设置
//     *
//     * @param alertType
//     */
//    public static void updatePushNotificationBlockSetting(Context context,
//                                                          String alertType) {
//        if (Constants.Value.ALL_MESSAGE_ALERT
//                .equals(alertType)) {
//            // 全天接收
//            if (JPushInterface.isPushStopped(context)) {
//                JPushInterface.resumePush(context);
//            }
//            JPushInterface.setPushTime(context, null, 0, 0);
//        } else if (Constants.Value.PART_MESSAGE_ALERT
//                .equals(alertType)) {
//            // 8-22点接收
//            if (JPushInterface.isPushStopped(context)) {
//                JPushInterface.resumePush(context);
//            }
//            Set<Integer> days = new HashSet<Integer>();
//            days.add(0);
//            days.add(1);
//            days.add(2);
//            days.add(3);
//            days.add(4);
//            days.add(5);
//            days.add(6);
//            JPushInterface.setPushTime(context, days, 8, 22);
//        } else if (Constants.Value.NO_MESSAGE_ALERT
//                .equals(alertType)) {
//            // 不接收
//            if (!JPushInterface.isPushStopped(context)) {
//                JPushInterface.stopPush(context);
//            }
//        }
//    }

    static {
        PATTERN_FIXEDPHONE = Pattern.compile(REGEX_FIXEDPHONE);
        PATTERN_ZIPCODE = Pattern.compile(REGEX_ZIPCODE);
        PATTERN_ZERO_FIRST = Pattern.compile(REGEX_ZERO_FIRST);
        PATTERN_WAYBILLNUMBER = Pattern.compile(REGEX_WAYBILLNUMBER);
    }

//    /**
//     * 推送消息声音和震动设置
//     *
//     * @param voice true表示开启
//     * @param shake true表示开启
//     */
//    public static void updatePushNotificationVoiceAndShakeSetting(
//            Context context, boolean voice, boolean shake) {
//        // 定制声音、震动、闪灯等 Notification 样式。
//         BasicPushNotificationBuilder builder = new
//         BasicPushNotificationBuilder(
//         context);
//
////        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
////                context, R.layout.view_customer_notitfication, R.id.icon,
////                R.id.title, R.id.text); // 指定定制的 Notification Layout
//        //builder.statusBarDrawable = R.drawable.ic_launcher; // 指定最顶层状态栏小图标
//        //builder.layoutIconDrawable = R.drawable.ic_launcher; // 指定下拉状态栏时显示的通知图标
//        if (voice) {
//            if (shake)
//                builder.notificationDefaults = Notification.DEFAULT_SOUND
//                        | Notification.DEFAULT_VIBRATE;
//            else
//                builder.notificationDefaults = Notification.DEFAULT_SOUND;
//        } else {
//            if (shake)
//                builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
//            else
//                builder.notificationDefaults = ~Notification.DEFAULT_SOUND
//                        & ~Notification.DEFAULT_VIBRATE;
//        }
//        JPushInterface.setDefaultPushNotificationBuilder(builder);
//    }
//
//    public static void setPushRegId(Context context){
//        String regId = PreferenceHelper.getRegistrationId(context);
//        if(TextUtils.isEmpty(regId)){
//            regId = JPushInterface.getRegistrationID(context);
//            PreferenceHelper.saveRegistrationId(context,regId);
//        }
//        if(!TextUtils.isEmpty(regId)){
//            UploadJpushRegistrationIdNetHelper netHelper = new UploadJpushRegistrationIdNetHelper(context);
//            netHelper.setRegistrationId(regId);
//            netHelper.execute();
//            //7.3版本整合到后台调用UCMP
//            UCMPUploadJpushRegistrationIdNetHelper ucmpNetHelper = new UCMPUploadJpushRegistrationIdNetHelper(context);
//            ucmpNetHelper.setRegistrationId(regId);
//            ucmpNetHelper.execute();
//        }
//    }


//    public static String getRelativelyLength(String str1, String str2) {
//        String tempName = TextUtils.isEmpty(str1) ? "" : str1;
//        String tempName2 = TextUtils.isEmpty(str2) ? "" : str2;
//        return tempName2.length() > tempName.length() ? tempName2 : tempName;
//    }

    // spanTime 以分钟为单位
    public static List<String> timeSegment(String startTime, String endTime,
                                           int spanMinuTime) {
        if(TextUtils.isEmpty(startTime)) {
            return Collections.emptyList();
        }
        List<String> dataList = new ArrayList<String>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            // 分割的时间，以毫秒为单位
            long segSpanTime = spanMinuTime * 60 * 1000;
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);
            long segNum = 0;// 按照指定时间，可以分成多少段
            long totalSpanTime = 0;
            // 判断时间段是否有跨天的，eg 08:00-02:00 当天08:00到次日的02:00
            if (startDate.compareTo(endDate) > 0) {
//                totalSpanTime = endDate.getTime() + 24 * 60 * 60 * 1000
//                        - startDate.getTime();
                return Collections.emptyList();
            } else {
                totalSpanTime = endDate.getTime() - startDate.getTime();
            }
            // (totalSpanTime%segSpanTime>0?1:0)用于判断时间段正好整除
            segNum = (totalSpanTime / segSpanTime)
                    + (totalSpanTime % segSpanTime > 0 ? 1 : 0);
            String tmpStr = "";
            long begainTime = startDate.getTime();
            for (int i = 0; i <= segNum; i++) {
                tmpStr = format.format(new Date(begainTime + segSpanTime * i));
                if (i == segNum
                        && (begainTime + segSpanTime * i) > endDate.getTime()) {
                    tmpStr = endTime;
                }
                dataList.add(tmpStr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            return dataList;
        }
    }

//    public static boolean isProdTimeAvailable(String endTime) {
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        // 分割的时间，以毫秒为单位
//        try {
//            Date endDate = format.parse(endTime);
//            Date startDate = format.parse(DateUtil.getCurrentTime(format));
//            if (startDate.compareTo(endDate) > 0) {
//                return false;
//            }
//            return true;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    /**
//     * 控件退出动画
//     */
//    public static void pushOutAnim(final View view, int tranY, int durTime) {
//        if (view.getTag(R.id.anim_tag) != null && view.getTag(R.id.anim_tag) instanceof AnimatorSet) {
//            ((AnimatorSet) view.getTag(R.id.anim_tag)).cancel();
//        }
//
//        //去除之前动画
//        AnimatorSet animSetOut = null;//打印终端列表收缩
//        if (animSetOut == null) {
//            //位移动画
//            ObjectAnimator animTranOut = ObjectAnimator.ofInt(view, "translationY", tranY, 0);
//
//            animTranOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//
//                    view.getLayoutParams().height = (int) animation.getAnimatedValue();
//                    view.requestLayout();
//                }
//            });
//            animTranOut.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                    view.setVisibility(View.GONE);
//                }
//            });
//            ObjectAnimator animAlphaOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
//            animSetOut = new AnimatorSet();
//            animSetOut.playTogether(animTranOut, animAlphaOut);
//            animSetOut.setDuration(durTime);
//        }
//
//        animSetOut.start();
//        //保存，重复点击时将前一个动画移除
//        view.setTag(R.id.anim_tag, animSetOut);
//    }
//
//    /**
//     * 控件进入动画
//     */
//    public static void pushInAnim(final View view, int tranY, int durTime) {
//
//        if (view.getTag(R.id.anim_tag) != null && view.getTag(R.id.anim_tag) instanceof AnimatorSet) {
//            ((AnimatorSet) view.getTag(R.id.anim_tag)).cancel();
//        }
//        //tranY<=0则认为控件高度获取失败，重新获取
//        if (tranY <= 0) {
//            tranY = getViewHeight(view);
//        }
//        AnimatorSet animSetIn = null;//打印终端列表展开
//        ObjectAnimator animTranIn = ObjectAnimator.ofInt(view, "translationY", 0, tranY);
//        animTranIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                view.getLayoutParams().height = (int) animation.getAnimatedValue();
//                view.requestLayout();
//            }
//        });
//        animTranIn.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                view.setVisibility(View.VISIBLE);
//            }
//        });
//        ObjectAnimator animAlphaIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
//        animSetIn = new AnimatorSet();
//        animSetIn.playTogether(animTranIn, animAlphaIn);
//        animSetIn.setDuration(durTime);
//        animSetIn.start();
//        view.setTag(R.id.anim_tag, animSetIn);
//    }


    /**
     * 计算并获取view的高度
     */
    public static int getViewHeight(View view) {
        try {
            Method m = view.getClass().getDeclaredMethod("onMeasure", int.class,
                    int.class);
            m.setAccessible(true);
            m.invoke(view, View.MeasureSpec.makeMeasureSpec(
                    ((View) view.getParent()).getMeasuredWidth(),
                    View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED));
        } catch (Exception e) {

        }
        return view.getMeasuredHeight();

    }


//    /**
//     * 根据下单渠道判断显示类型
//     * @param context
//     * @param mediaCode
//     * @param channelCode
//     * @return
//     */
//    public static String getOrderTypeName(Context context, String mediaCode, String channelCode) {
//        String sourceName = "";
//        if (Constants.OrderMediaCode.APP_ANDROID.equals(mediaCode) || Constants.OrderMediaCode.APP_IOS.equals(mediaCode) || Constants.OrderMediaCode.APP_ANDROID.equals(channelCode) || Constants.OrderMediaCode.APP_IOS.equals(channelCode)) {
//            //ios与android
//            sourceName = context.getString(R.string.order_media_app);
//        } else if (Constants.OrderMediaCode.WEIXIN.equals(mediaCode) || Constants.OrderMediaCode.WENXIN.equals(mediaCode) || "wx".equals(channelCode)) {
//            //微信
//            sourceName = context.getString(R.string.order_media_app);
//        } else if (Constants.OrderMediaCode.MEMBER_PC.equals(mediaCode) || Constants.OrderMediaCode.MEMBER_MOBILE.equals(mediaCode)
//                || Constants.OrderMediaCode.MEMBER_0.equals(mediaCode) || Constants.OrderMediaCode.MEMBER_1.equals(mediaCode)
//                || Constants.OrderMediaCode.MEMBER_ZNZD_0.equals(mediaCode) || Constants.OrderMediaCode.MEMBER_ZNZD_1.equals(mediaCode)
//                || Constants.OrderMediaCode.MEMBER_PC.equals(channelCode) || Constants.OrderMediaCode.MEMBER_MOBILE.equals(channelCode)
//                ) {
//            //会员
//            sourceName = context.getString(R.string.order_media_mem);
//        } else if (Constants.OrderMediaCode.OFFICE_PC.equals(mediaCode) || Constants.OrderMediaCode.OFFICE_MOBILE.equals(mediaCode)
//                || Constants.OrderMediaCode.OFFICE_PC.equals(channelCode) || Constants.OrderMediaCode.OFFICE_MOBILE.equals(channelCode)
//                ) {
//            //官网
//            sourceName = context.getString(R.string.order_media_office);
//        } else if (Constants.OrderMediaCode.BAIDU.equals(mediaCode) || Constants.OrderMediaCode.BAIDU_QING.equals(mediaCode)
//                ) {
//            //百度
//            sourceName = context.getString(R.string.order_media_baidu);
//        } else if (Constants.OrderMediaCode.ZHAOSHANG.equals(mediaCode)) {
//            sourceName = context.getString(R.string.order_media_zhaoshang);
//        } else if (Constants.OrderMediaCode.SHUNXINBAO.equals(mediaCode) || Constants.OrderMediaCode.SHUNXINBAO.equals(channelCode)) {
//            sourceName = context.getString(R.string.order_media_shunxinbao);
//        } else if (Constants.OrderMediaCode.A95338.equals(mediaCode) || Constants.OrderMediaCode.A95338.equals(channelCode)) {
//            sourceName = context.getString(R.string.order_media_95338);
//        } else {
//            sourceName = context.getString(R.string.order_media_app);
//        }
//        return sourceName;
//    }
//
//    /**
//     * 判断是否能够显示打印运单按钮
//     * @param info
//     * @return
//     */
//    public static boolean isShowPrintWaybill(OrderInfo info) {
//        String channelCode = "";
//        String mediaCode = "";
//        if (!TextUtils.isEmpty(info.channelCode))
//            channelCode = info.channelCode;
//        if (!TextUtils.isEmpty(info.mediaCode))
//            mediaCode = info.mediaCode;
//
//        if ((!TextUtils.isEmpty(info.orderNo) && (info.orderNo.startsWith("MEM") || info.orderNo.startsWith("MAS")))//会员下的订单，后台接口暂不支持生成二维码
//                || info.orderNo.startsWith("DG") || info.orderNo.startsWith("SZ")//95338下单不支持打印，后台接口暂不支持生成二维码
//                || !"1".equals(info.getIsEWayBill())//非电子运单不支持生成二维码
//                || info.orderState.equals("6") || info.orderState.equals("7")//取消寄件与异常收件不支持打印
//                ) {
//            return false;
//        } else if (TextUtils.equals(Constants.OrderMediaCode.MEMBER_PC, channelCode) || TextUtils.equals(Constants.OrderMediaCode.MEMBER_MOBILE, channelCode)//会员下的订单
//                || TextUtils.equals(Constants.OrderMediaCode.MEMBER_PC, mediaCode) || TextUtils.equals(Constants.OrderMediaCode.MEMBER_MOBILE, mediaCode)//会员下的订单
//                || TextUtils.equals(Constants.OrderMediaCode.MEMBER_0, mediaCode) || TextUtils.equals(Constants.OrderMediaCode.MEMBER_1, mediaCode)//会员下的订单
//                || TextUtils.equals(Constants.OrderMediaCode.MEMBER_ZNZD_0, mediaCode) || TextUtils.equals(Constants.OrderMediaCode.MEMBER_ZNZD_1, mediaCode)//会员下的订单
//                || TextUtils.equals(Constants.OrderMediaCode.A95338, channelCode) || TextUtils.equals(Constants.OrderMediaCode.A95338, mediaCode)//95338下单
//                ) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 是否可以进行终端打印
//     * @param expressType 下单类型
//     */
//    public static boolean isShowTerminalPrint(String expressType) {
//        if(TextUtils.isEmpty(expressType)) {
//            return false;
//        }
//        // 终端寄，顺手寄有终端，和所有服务点自寄
//        if(expressType.equals(Constants.ExpressType.TERMINAL) || expressType.equals(Constants.ExpressType.CONVENIENT_TERMINAL) ||
//                expressType.equals(Constants.ExpressType.SERVICE))
//            return true;
//        return false;
//    }

    public static boolean isContractNameValid(String name) {
        if(!TextUtils.isEmpty(name)) {
            return !Pattern.matches(CONTRACT_NAME_PATTERN, name);
        }
        return true;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 1000) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

//    /**
//     * 汉字转换位汉语拼音首字母，英文字符不变
//     * @param chines 汉字
//     * @return 拼音
//     */
//    public static char converterToFirstSpell(String chines){
//        String pinyinName = "";
//        char[] nameChar = chines.toCharArray();
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        for (int i = 0; i < nameChar.length; i++) {
//            if (nameChar[i] > 128) {
//                try {
//                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
//                } catch (BadHanyuPinyinOutputFormatCombination e) {
//                    e.printStackTrace();
//                }
//            }else{
//                pinyinName += nameChar[i];
//            }
//        }
////        LogUtil.getLogger().d("CommonUtil", pinyinName);
//        return pinyinName.toCharArray()[0];
//    }
//
//    public static String updateTodayTime(String day, String startTime) {
//        String[] currentTime = DateUtil.getCurrentTime(DateUtil.DATE_FORMAT_5).split(" ");
//        String[] currTime = currentTime[1].split(":");
//        int currHour = Integer.parseInt(currTime[0]);//当前小时
//        int currMinute = Integer.parseInt(currTime[1]);//当前分钟
//        String[] start = startTime.split(":");
//        int startHour = Integer.parseInt(start[0]);//开始小时
//        int startMinute = Integer.parseInt(start[1]);//开始分钟
//
//        if(currentTime[0].equals(day)) {
//            if (currHour * 60 + currMinute + 30 < startHour * 60 + startMinute) {
//                return startTime;
//            } else if (currMinute == 0) {
//                return currHour + ":30";
//            } else if (currMinute > 0 && currMinute <= 30) {
//                return (currHour + 1) + ":00";
//            } else if (currMinute > 30) {
//                return (currHour + 1) + ":30";
//            }
//        } else {
//            return startTime;
//        }
//
//        return null;
//    }
//
//    public static String getProductCode(String prodCode) {
//        if(TextUtils.isEmpty(prodCode)) {
//            return "";
//        }
//        if(Constants.ProdTypeCode.STANDARD.equals(prodCode)) {
//            return "S1";
//        } else if(Constants.ProdTypeCode.SPECIAL.equals(prodCode)) {
//            return "S2";
//        } else if(Constants.ProdTypeCode.MORNING.equals(prodCode)) {
//            return "T2";
//        } else if(Constants.ProdTypeCode.TODAY_20.equals(prodCode)) {
//            return "T1";
//        } else if(Constants.ProdTypeCode.TODAY_22.equals(prodCode)) {
//            return "T11";
//        }  else {
//            return "";
//        }
//    }

    /**
     * 获取当前起始时间
     * @return
     */
    public static String getStartTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 判断是否符合IP电话格式
     * @return
     */
    public static boolean isOthersValid(String others) {
        if(TextUtils.isEmpty(others)) {
            return false;
        }
        others = others.replace("-", "");
        if(others.startsWith("400") && others.length() == 10) {
            return true;// 400电话
        } else if(isMobileValid(others)) {
            return true;
        }
        return false;
    }

    /**
     * 判断电话是否以IP电话打头
     * @return
     */
    private static boolean isStartWithIpNum(String others) {
        return others.startsWith("17951") || others.startsWith("12593") ||
                others.startsWith("17900") || others.startsWith("17901") ||
                others.startsWith("17908") || others.startsWith("17909") ||
                others.startsWith("17911") || others.startsWith("10193");
    }

    public static String filterIpNum(String phone) {
        if(phone.startsWith("17951") || phone.startsWith("12593") ||
                phone.startsWith("17900") || phone.startsWith("17901") ||
                phone.startsWith("17908") || phone.startsWith("17909") ||
                phone.startsWith("17911") || phone.startsWith("10193")) {
            return phone.substring(5);
        }
        return phone;
    }

    /**
     * 动态设置listview显示的条目数量
     *
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, int count) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < count; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (count - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        if(listAdapter.getCount() > count) {
            params.height += 80;
        }
        listView.setLayoutParams(params);
    }

}