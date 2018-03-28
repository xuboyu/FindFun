package com.example.cory_xuboyu.findfun.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.example.cory_xuboyu.findfun.R;
import com.example.cory_xuboyu.findfun.databinding.FragmentAboutBinding;
import com.example.cory_xuboyu.findfun.net.AboutMouddle;
import com.xiawei.webviewlib.WebViewActivity;

/**
 * 作用：关于界面
 * Created by xuboyu on 2018/3/26.
 * e-mail:boyu274572505@qq.com
 */

public class AboutFragment extends Fragment {
    private AboutMouddle mAboutModule;
    private FragmentAboutBinding mAboutBinding;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, null, false);
        View view = mAboutBinding.getRoot();

        mAboutModule = new AboutMouddle();
        mAboutModule.setImgUrl(getString(R.string.about_background));
        mAboutBinding.setAbout(mAboutModule);
        mAboutBinding.setPresenter(new Presenter());

        return view;
    }

    public class Presenter{
        public void onAboutClick(View view) {
            String text = ((TextView) view).getText().toString();
            LogUtils.e("xby-->" + text + "被点击了");
            String url  = mAboutModule.getString(text);
            WebViewActivity.startUrl(AboutFragment.this.getActivity(), url);
        }
    }
}
