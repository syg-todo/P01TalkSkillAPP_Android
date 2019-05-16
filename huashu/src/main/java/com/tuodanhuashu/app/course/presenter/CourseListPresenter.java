package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.CourseClassBean;
import com.tuodanhuashu.app.course.biz.CourseListBiz;
import com.tuodanhuashu.app.course.view.CourseListView;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.List;

public class CourseListPresenter extends BasePresenter {
    private Context context;

    private CourseListView courseListView;

    private CourseListBiz courseListBiz;

    private static final int TAG_KEYWORDS = 1;

    private static final int TAG_CLASS = 2;
    private static final int TAG_CLASS_LIST = 3;

    private static final int TAG_MY_LIST = 4;

    public CourseListPresenter(Context context, CourseListView courseListView) {
        this.context = context;
        this.courseListView = courseListView;
        courseListBiz = new CourseListBiz(context,this);
    }

//
//    public void requestAticleListByKeywords(String key_words,String page,String page_size){
//        courseListBiz.requestArticleListByKeywords(TAG_KEYWORDS,key_words,page,page_size);
//    }
//
//    public void requestArticleListByCatId(String cat_id,String page,String page_size){
//        courseListBiz.requestArticleListByCatId(TAG_CLASS,cat_id,page,page_size);
//    }
//
//    public void requestNewArticleList(String page,String page_size){
//        courseListBiz.requestNewArticle(TAG_KEYWORDS,page,page_size);
//    }
//
//    public void requestChoiceArticleList(String page,String page_size){
//        courseListBiz.requestChoicesList(TAG_KEYWORDS,page,page_size);
//    }

//    public void requestMyList(String page,String page_size){
//        courseListBiz.requestMyList(TAG_MY_LIST,page,page_size);
//    }

    public void requestCourseClassList(){
        courseListBiz.requestCourseClassList(TAG_CLASS_LIST);
    }

    public void requestCourseListByClassId(String class_id,String page,String page_size){
        courseListBiz.requestCourseListByClassId(TAG_CLASS,class_id,page,page_size);
    }


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_CLASS_LIST:
                List<CourseClassBean> courseClassList = JsonUtils.getJsonToList(serverResponse.getData(),CourseClassBean.class);
                courseListView.getClassListSuccess(courseClassList);
                break;
            case TAG_CLASS:
                List<HomeCourseBean> courseBeanList = JsonUtils.getJsonToList(serverResponse.getData(),HomeCourseBean.class);
                courseListView.getCourseListSuccess(courseBeanList);
                break;
//            default:
//                List<ArticleListItemBean> articleListItemBeanList = JsonUtils.getJsonToList(serverResponse.getData(),ArticleListItemBean.class);
//                zhuanLanListView.getArticleListSuccess(articleListItemBeanList);
//                break;
        }

    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        courseListView.getCourseListFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return courseListView;
    }
}
