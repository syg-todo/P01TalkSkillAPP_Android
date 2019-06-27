package com.company.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.common.utils.StringUtils;
import com.company.common.utils.ToastUtils;
import com.company.common.view.VaryViewHelperController;

public abstract class BaseFragment extends Fragment implements BaseView{

    protected Context mContext;

    protected static String TAG_LOG;

    private VaryViewHelperController varyViewHelperController;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG_LOG = this.getClass().getSimpleName();
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(varyViewHelperController == null){
            varyViewHelperController = new VaryViewHelperController(view.findViewById(getRootLayoutId()));
        }
        initView(view);
        initData();
        bindListener();
    }



    protected abstract int getContentViewLayoutID();

    protected abstract int getRootLayoutId();


    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract void bindListener();

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
    public void showLoadingView(){
        varyViewHelperController.showLoading(getLoadingLayoutResId());
    }

    @Override
    public void showOriginView() {
        varyViewHelperController.restore();
    }

    /**
     * get the support fragment manager
     *
     * @return
     */
    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }


    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(mContext, clazz);
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
        Intent intent = new Intent(mContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoClearTop(Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * show toast
     *
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        if (StringUtils.isEmpty(msg)) {
            ToastUtils.showToast(mContext, msg);
        }
    }


}
