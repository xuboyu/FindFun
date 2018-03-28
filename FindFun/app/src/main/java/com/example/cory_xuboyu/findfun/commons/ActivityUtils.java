package com.example.cory_xuboyu.findfun.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;

import java.lang.ref.WeakReference;

/**
 * 作用：此类包含与Activity相关的一些常用方法，例如startActivity和showToast等等
 * Created by xuboyu on 2018/3/26.
 * e-mail:boyu274572505@qq.com
 */

@SuppressWarnings("unused") public class ActivityUtils {
    /**
     * 使用弱引用。避免不恰当地持有Activity或者Fragment的引用
     * 持有Activity的引用会阻止Activity的内存回收，增加OOM的风险
     */
    private WeakReference<Activity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;

    private Toast toast;

    public ActivityUtils(Activity activity) {
        activityWeakReference = new WeakReference<Activity>(activity);
    }

    public ActivityUtils(Fragment fragment) {
        fragmentWeakReference = new WeakReference<Fragment>(fragment);
    }

    /**
     * 通过弱引用获取Activity对象，需检查是否返回null
     */
    private
    @Nullable
    Activity getActivity(){
        if (activityWeakReference != null) return activityWeakReference.get();
        if (fragmentWeakReference != null){
            Fragment fragment = fragmentWeakReference.get();
            return fragment == null ? null : fragment.getActivity();
        }
        return null;
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    public void showToast(String msg){
        Activity activity = getActivity();
        if(activity != null) {
            if (toast == null) toast = Toast.makeText(activity,msg,Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        }
    }

    @SuppressWarnings ("SameParameterValue")
    public void showToast(@StringRes int resId) {
        Activity activity = getActivity();
        if (activity != null) {
            String msg = activity.getString(resId);
            showToast(msg);
        }
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity,clazz);
        activity.startActivity(intent);
    }

    /**
     * Android没有一个官方的API来检索状态栏高度。
     * 这只是接收器的方式来破解，可能无法在某些设备上工作。
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight(){
        Activity activity = getActivity();
        if (activity == null) return 0;
        Resources resources = getActivity().getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelOffset(resourceId);
        }
        LogUtils.e("状态栏高度----->", + result);
        return result;
    }

    /**
     * 获取屏幕高度
     *
     * @return Screen Height
     */
    public int getScreenHeight() {
        Activity activity = getActivity();
        if (activity == null) return 0;
        //获取屏幕密度
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return Screen Height
     */
    public int getScreenWidth() {
        Activity activity = getActivity();
        if (activity == null) return 0;
        //获取屏幕密度
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 关闭/隐藏键盘
     */
    public void hideSoftKeyboard() {
        Activity activity = getActivity();
        if (activity == null) return;
        //获取当前activity中获得焦点的view
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    /**
     * 启动浏览器
     */
    public void StartBrowser(String url){
        if (getActivity() == null) return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        getActivity().startActivity(intent);
    }
}
