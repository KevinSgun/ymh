package com.zitech.framework.utils;

import android.util.Log;


/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package com.zitech.personalaccount.utils
 * @Description: Log日志的自动生成的Tag：类名$方法名[call,行数]
 * @date 2016/5/3 14:21
 */
public class LogUtils {
    public static final String customTagPrefix = "zitech";

    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;

    private static String generateTag(StackTraceElement caller) {
        StringBuilder sb = new StringBuilder();
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        sb.append(customTagPrefix + ":")
                .append(callerClazzName)
                .append("[")
                .append(caller.getMethodName() + ",")
                .append(Integer.valueOf(caller.getLineNumber()))
                .append("]");
        return sb.toString();
    }

    public static void d(String content) {
        if (!(allowD))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!(allowD))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!(allowE))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!(allowE))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!(allowI))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!(allowI))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!(allowV))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!(allowV))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!(allowW))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!(allowW))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!(allowW))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.w(tag, tr);
    }

    public static void wtf(String content) {
        if (!(allowWtf))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!(allowWtf))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!(allowWtf))
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Log.wtf(tag, tr);
    }


    public static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[3];
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}