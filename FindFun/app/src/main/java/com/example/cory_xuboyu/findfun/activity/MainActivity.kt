package com.example.cory_xuboyu.findfun.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Message
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.utils.FileUtils
import com.blankj.utilcode.utils.LogUtils
import com.blankj.utilcode.utils.SPUtils
import com.bumptech.glide.Glide
import com.example.cory_xuboyu.findfun.R
import com.example.cory_xuboyu.findfun.commons.ActivityUtils
import com.example.cory_xuboyu.findfun.fragment.*
import com.roughike.bottombar.BottomBar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_dialog_pic_selector.view.*
import kotlinx.android.synthetic.main.nav_header.view.*
import java.io.File
import java.lang.ref.WeakReference

/**
 * 作用:主界面
 * Created by xuboyu on 2018/3/26.
 * e-mail:boyu274572505@qq.com
 */

class MainActivity : AppCompatActivity() {
    private var newsFragment: NewsFragment? = null //新闻界面
    private var jokeFragment: JokeFragment? = null //段子界面
    private var aboutFragment: AboutFragment? = null //关于界面
    private var todayFragment: TodayFragment? = null //历史上的今天界面
    private var gifFragment: GifFragment? = null //动图界面

    private var currentFragment: Fragment? = null

    private var utils: ActivityUtils? = null
    private var bottomBar: BottomBar? = null
    private var builder: AlertDialog.Builder? = null

    private var mSPUtils: SPUtils? = null

    private var mIconImage: ImageView? = null
    private var mDialog: BottomSheetDialog? = null
    private var mHandler: MyHandler? = null
    private var mDirSize = ""

    internal inner class MyHandler(activity: MainActivity) : android.os.Handler() {
        var mActivity: WeakReference<MainActivity>

        init {
            mActivity = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            val theActivity = mActivity.get()
            if (theActivity == null || theActivity.isFinishing) {
                return
            }
            // 消息处理
            when (msg.what) {
                SUCESS -> utils!!.showToast("清理成功")
                FAILD -> {
                    utils!!.showToast("清理失败")
                }
                else -> {
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取并设置主题
        val util = SPUtils("theme_id")
        val theme_id = util.getInt("theme_id", R.style.AppTheme)
        LogUtils.e("theme_id--------->",theme_id)
        setTheme(theme_id)
        //如果sdk版本大于4.4,则设置状态栏透明化 会导致首页状态栏减少
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        //设置软键盘的模式为适应屏幕模式
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setContentView(R.layout.activity_main)
        retrieveFragment()

        utils = ActivityUtils(this)
        mSPUtils = SPUtils("head")
        mHandler = MyHandler(this)

        /**
         * 侧滑菜单，设置头像
         */
        mIconImage = nv_left!!.getHeaderView(0).person_head_image as CircleImageView
        val ivBmp = nv_left!!.getHeaderView(0).iv_head_bg as ImageView
        if (!mSPUtils!!.getBoolean("has_head",false)) {
            Glide.with(this)
                    .load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3197867592,81431014&fm=27&gp=0.jpg")
                    .placeholder(R.drawable.head)//头像加载前显示的图片
                    .error(R.drawable.head)//头像加载失败的图片
                    .centerCrop()//缩放图像填充到ImageView
                    .into(mIconImage!!)
        }else {
            mIconImage!!.setImageBitmap(BitmapFactory.decodeFile(TEMP_PATH))
        }

        Glide.with(this@MainActivity)
                .load(R.drawable.back)
                .crossFade()
                .centerCrop()
                .into(ivBmp)

        /**
         * 头像设置
         */
        mIconImage!!.setOnClickListener{
            mDialog = BottomSheetDialog(this@MainActivity)
            val view = View.inflate(this@MainActivity,
                    R.layout.bottom_dialog_pic_selector,null)
            val xj = view.tv_xiangji as TextView
            val xc = view.tv_xiangce as TextView
            xj.setOnClickListener(listener)
            xc.setOnClickListener(listener)
            mDialog!!.setContentView(view)
            mDialog!!.setCancelable(true)
            mDialog!!.setCanceledOnTouchOutside(true)
            mDialog!!.show()
        }

        /**
         * 底部TAB 点击事件
         */
        bottomBar = bBar as BottomBar
        bottomBar!!.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tab_news -> {
                    LogUtils.i("setOnTabSelectListener")
                    if (newsFragment == null) newsFragment = NewsFragment()
                    switchFragment(newsFragment!!)
                    nv_left!!.setCheckedItem(R.id.nav_news)
                    closeDrawerLayout()
                }
                R.id.tab_joke -> {
                    nv_left!!.setCheckedItem(R.id.nav_duanzi)
                    if (jokeFragment == null) jokeFragment = JokeFragment()
                    switchFragment(jokeFragment!!)
                    closeDrawerLayout()
                }
                R.id.tab_today -> {
                    nv_left!!.setCheckedItem(R.id.nav_today_of_history)
                    if (todayFragment == null) todayFragment = TodayFragment()
                    switchFragment(todayFragment!!)
                    closeDrawerLayout()
                }
                R.id.tab_img -> {
                    nv_left!!.setCheckedItem(R.id.nav_img)
                    if (gifFragment == null) gifFragment = GifFragment()
                    switchFragment(gifFragment!!)
                    closeDrawerLayout()
                }
                R.id.tab_about -> {
                    nv_left!!.setCheckedItem(R.id.nav_other)
                    if (aboutFragment == null) aboutFragment = AboutFragment()
                    switchFragment(aboutFragment!!)
                    closeDrawerLayout()
                }
            }
        }

        /**
         * 左侧 侧滑菜单 设置选择事件
         */
        nv_left!!.setCheckedItem(R.id.nav_news)
        nv_left!!.setNavigationItemSelectedListener { item ->
            nv_left!!.setCheckedItem(item.itemId)
            dl_activity_main!!.closeDrawers()
            when (item.itemId) {
                R.id.nav_news -> bottomBar!!.selectTabAtPosition(0, true)
                R.id.nav_duanzi -> bottomBar!!.selectTabAtPosition(1, true)
                R.id.nav_today_of_history -> bottomBar!!.selectTabAtPosition(2, true)
                R.id.nav_other -> bottomBar!!.selectTabAtPosition(3, true)
                R.id.nav_clear_cache -> clearCache()
                R.id.nav_change_theme -> alertChangeTheme()
                R.id.nav_night -> changeTheme(9)
                R.id.nav_day -> {
                    changeTheme(0)
                    utils!!.showToast("切换成功")
                }
                else -> {
                }
            }
            false
        }

    }

    /**
     * 找回FragmentManager中存储的Fragment
     */
    private fun retrieveFragment() {
        val manager = supportFragmentManager
        newsFragment = manager.findFragmentByTag("NewsFragment") as NewsFragment?
        jokeFragment = manager.findFragmentByTag("JokeFragment") as JokeFragment?
        todayFragment = manager.findFragmentByTag("TodayFragment") as TodayFragment?
        aboutFragment = manager.findFragmentByTag("AboutFragment") as AboutFragment?
        gifFragment = manager.findFragmentByTag("GifFragment") as GifFragment?
    }

    /**
     * 切换Fragment的显示
     *
     * @param target 要切换的 Fragment
     */
    private fun switchFragment(target: Fragment) {
        // 如果当前的fragment 就是要替换的fragment 就直接return
        if (currentFragment === target) return
        // 获得 Fragment 事务
        val transaction = supportFragmentManager.beginTransaction()
        // 如果当前Fragment不为空，则隐藏当前的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        // 如果要显示的Fragment 已经添加了，那么直接 show
        if (target.isAdded) {
            transaction.show(target)
        } else {
            // 如果要显示的Fragment没有添加，就添加进去
            transaction.add(R.id.fl_content, target, target.javaClass.name)
        }
        // 事务进行提交
        transaction.commit()
        //并将要显示的Fragment 设为当前的 Fragment
        currentFragment = target
    }

    /**
     * 关闭左侧 侧滑菜单
     */
    private fun closeDrawerLayout() {
        if (dl_activity_main!!.isDrawerOpen(Gravity.LEFT)) {
            dl_activity_main!!.closeDrawers()
        }
    }

    internal var lastTime: Long = 0

    /**
     * 2秒内连续点击 back 键，退出应用
     */
    override fun onBackPressed() {
        // 判断侧滑菜单是否开启，如果开启则先关闭
        if (dl_activity_main!!.isDrawerOpen(Gravity.LEFT)) {
            dl_activity_main!!.closeDrawers()
            return
        }
        // 判断当前fragment是不是新闻fragment，如果不是先退到新闻fragment
        if (currentFragment !== newsFragment) {
            bottomBar!!.selectTabAtPosition(0)
            return
        }
        val curTime = System.currentTimeMillis()
        if (curTime - lastTime > 2000) {
            utils!!.showToast("再按一次退出应用")
            lastTime = curTime
        } else {
            super.onBackPressed()
        }
    }

    private fun changeTheme(index: Int) {
        val themes = intArrayOf(R.style.AppTheme, R.style.AppTheme_Blue,
                R.style.AppTheme_Green, R.style.AppTheme_Orange,
                R.style.AppTheme_Pink, R.style.AppTheme_Sky,
                R.style.AppTheme_Purple, R.style.AppTheme_PP,
                R.style.AppTheme_Yellow, R.style.AppTheme_Night)
        val utils = SPUtils("theme_id")
        utils.putInt("theme_id", themes[index])
        val intent = intent
        overridePendingTransition(0, 0)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    /**
     * 动态换肤
     */
    private fun alertChangeTheme() {
        val view = View.inflate(this, R.layout.item_change_theme, null)
        builder = AlertDialog.Builder(this).setView(view)
        builder!!.show()
        view.findViewById(R.id.tv_red).setOnClickListener(listener)
        view.findViewById(R.id.tv_green).setOnClickListener(listener)
        view.findViewById(R.id.tv_blue).setOnClickListener(listener)
        view.findViewById(R.id.tv_orange).setOnClickListener(listener)
        view.findViewById(R.id.tv_pink).setOnClickListener(listener)
        view.findViewById(R.id.tv_sky).setOnClickListener(listener)
        view.findViewById(R.id.tv_purple).setOnClickListener(listener)
        view.findViewById(R.id.tv_pp).setOnClickListener(listener)
        view.findViewById(R.id.tv_yellow).setOnClickListener(listener)

    }

    private val listener = View.OnClickListener { v ->
        when (v.id) {
            R.id.tv_red -> changeTheme(0)
            R.id.tv_blue -> changeTheme(1)
            R.id.tv_green -> changeTheme(2)
            R.id.tv_orange -> changeTheme(3)
            R.id.tv_pink -> changeTheme(4)
            R.id.tv_sky -> changeTheme(5)
            R.id.tv_purple -> changeTheme(6)
            R.id.tv_pp -> changeTheme(7)
            R.id.tv_yellow -> changeTheme(8)
            R.id.tv_xiangji -> {
                //相机
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                // 指定调用相机拍照后照片存储路径
                val imageUri = Uri.parse(FILE_PATH)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, 1000)
            }
            R.id.tv_xiangce -> {
                //相册
                intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(intent, 1001)
            }


            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                1000 -> {
                    val temp = File(TEMP_PATH)
                    startPhotoZoom(Uri.fromFile(temp))
                }
                1001 -> {
                    val temp1 = File(TEMP_PATH)
                    startPhotoZoom(Uri.fromFile(temp1))
                }
                1002 -> if (data != null) {
                    val extras = data.extras
                    if (extras != null) {
                        val bmp = extras.getParcelable<Bitmap>("data")
                        Log.i("--->", "onActivityResult: ")
                        mIconImage!!.setImageBitmap(bmp)
                        mSPUtils!!.putBoolean("has_head", true)
                        if (mDialog != null && mDialog!!.isShowing) {
                            mDialog!!.dismiss()
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    /**
     * 图片裁剪
     */
    private fun startPhotoZoom(uri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // crop 为true 是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", "true")

        // aspect 是宽高比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)

        // output 是裁剪图片的宽高
        intent.putExtra("outputX", 100)
        intent.putExtra("outputY", 100)
        intent.putExtra("return-data", true)
        intent.putExtra("noFaceDetection", true)
        startActivityForResult(intent, 1002)
    }

    /**
     * 清除缓存
     */
    private fun clearCache() {
        mDirSize = FileUtils.getDirSize(cacheDir)
        AlertDialog.Builder(this@MainActivity).setTitle("确定要清理缓存")
                .setMessage("缓存大小：" + mDirSize)
                .setPositiveButton("清理"
                ) { dialog, which ->
                    Thread(Runnable {
                        FileUtils.deleteDir(cacheDir)
                        mDirSize = FileUtils.getDirSize(cacheDir)
                        mHandler!!.sendEmptyMessage(SUCESS)
                    }).start()
                }
                .setNegativeButton("取消", null)
                .show()
    }

    companion object {
        private val TAG ="MainActivity------>"

        val SUCESS = 0
        val FAILD = 1

        val FILE_PATH = "file://" +
                Environment.getExternalStorageDirectory().path +
                "/xuboyu" + "/image_cache" +
                "/camera.jpg"

        val TEMP_PATH = Environment.getExternalStorageDirectory().path +
                "/xuboyu" + "/image_cache" +
                "/camera.jpg"
    }
}
