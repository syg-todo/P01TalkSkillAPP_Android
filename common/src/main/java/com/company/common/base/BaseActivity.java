package com.company.common.base;
/** *
 *此类是activity基类，所有activity必须继承此类，使用方法
 * 1：在getContentViewLayoutID()中设置activity布局文件
 * 2：getBundleExtras(Bundle extras)获取上个页面传来的数据，若没有传值，则不要重写该方法
 * 3：重写initView()初始化控件
 * 4：重写initData()初始化数据并将数据赋值到控件中，界面初始化时的网络请求，和数据库读取可以放在此方法中
 * 5：重写bindListener()绑定事件
 * 6：调用setCustomNavigationBar(int leftPic,String leftStr,String title,String rightStr,boolean isRightVisible)设置导航栏
 * 7：使用此基类后，无需重写onCreate()
 * 8：空页面和异常页面的使用：第一步在getLoadingTargetView()中设置要显示数据的控件，第二步若数据为空或异常，调用toggleShowEmpty或toggleShowError即可
 * 9：重写isEnableDoubleClickExit()设置该页面是否可以双击退出app，若返回true则可以双击退出
 * */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.company.common.utils.ToastUtils;
import com.company.common.view.VaryViewHelper;
import com.company.common.view.VaryViewHelperController;


/**
 * Created by 应震宇 on 2017/6/15.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    /**
     * context
     */
    protected Context mContext = null;

    /**
     * exit time
     */
    private long mExitTime = 0;

    private VaryViewHelperController varyViewHelperController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if(getContentViewLayoutID()!=0){
            setContentView(getContentViewLayoutID());
            //ButterKnife.bind(this);
        }
        if(varyViewHelperController == null){
            varyViewHelperController = new VaryViewHelperController(findViewById(getRootLayoutId()));
        }

        initView();
        initData();
        bindListener();
    }


    protected abstract int getRootLayoutId();


    protected abstract int getEmptyLayoutResId();

    protected abstract int getErrorLayoutResId();

    protected abstract int getLoadingLayoutResId();

    @Override
    public void showEmptyView(String msg) {
        varyViewHelperController.showEmpty(getEmptyLayoutResId(),msg);
    }


    @Override
    public void showErrorView(String msg) {
        varyViewHelperController.showNetworkError(getErrorLayoutResId(),null);
    }

    @Override
    public void showOriginView() {
        varyViewHelperController.restore();
    }

    @Override
    public void showLoadingView(){
        varyViewHelperController.showLoading(getLoadingLayoutResId());
    }

    /**
     *设置activity布局
     */

    protected abstract int getContentViewLayoutID();




    /**
     * init view
     *
     *
     */
    protected abstract void initView();


    /**
     * init data
     *
     *
     */
    protected abstract void initData();

    /**
     * bind listener
     *
     *
     */
    protected abstract void bindListener();


    /**
     * get bundle data
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void readyGoClearTop(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * show toast
     *
     * @param msg
     */
    @Override
    public void showToast(String msg) {

        if (null != msg && !TextUtils.isEmpty(msg)) {
            //Snackbar.make(getLoadingTargetView(), msg, Snackbar.LENGTH_SHORT).show();
            ToastUtils.showToast(mContext,msg);
        }
    }

    /**
     * show toast with custom gravity
     *
     * @param msg
     */
    protected void showToast(String msg, int gravity) {

        if (null != msg && !TextUtils.isEmpty(msg)) {
            //Snackbar.make(getLoadingTargetView(), msg, Snackbar.LENGTH_SHORT).show();

        }
    }


    /**
     * is Enable double click exit app
     *
     *
     */
    protected abstract boolean isEnableDoubleClickExit();

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
