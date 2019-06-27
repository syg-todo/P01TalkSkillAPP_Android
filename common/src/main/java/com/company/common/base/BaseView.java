package com.company.common.base;

public interface BaseView {


    void showToast(String msg);

    void showErrorView(String msg);

    void showEmptyView(String msg);

    void showOriginView();

    public void showLoadingView();


    public void showLoadingDialog();

    public void cancalLoadingDialog();
}
