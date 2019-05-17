package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.CourseClassBean;
import com.tuodanhuashu.app.course.bean.CourseWithBannerBean;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.course.biz.CourseListBiz;
import com.tuodanhuashu.app.course.view.CourseListView;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.List;

public class CourseListPresenter extends BasePresenter {
    private Context context;

    private CourseListView courseListView;

    private CourseListBiz courseListBiz;

    private static final int TAG_KEYWORDS = 1;

    private static final int TAG_COURSE_SORTING = 2;
    private static final int TAG_CLASS_LIST = 3;

    private static final int TAG_COURSE_COMMUNITY = 4;
    private static final int TAB_BANNER_COMMUNITY = 5;

    private static final int TAG_BANNER_PRIVATE = 6;
    private static final int TAG_MASTER_LIST = 7;
    private static final int TAG_COURSE_PRIVATE = 8;

    private static final int TAG_MY_LIST = 4;

    public CourseListPresenter(Context context, CourseListView courseListView) {
        this.context = context;
        this.courseListView = courseListView;
        courseListBiz = new CourseListBiz(context, this);
    }

    //获取课程分类
    public void requestCourseClassList() {
        courseListBiz.requestCourseClassList(TAG_CLASS_LIST);
    }

    //根据分类获取课程列表
    public void requestCourseListByClassId(String class_id, String page, String page_size) {
        courseListBiz.requestCourseListByClassId(TAG_COURSE_SORTING, class_id, page, page_size);
    }

    //获取社群课列表 包含Banner信息
    public void requestCommunityCourseList(String page, String page_size) {
        courseListBiz.requestCourseCommunityList(TAG_COURSE_COMMUNITY, page, page_size);
    }

    //获取私教老师列表
    public void requestMasterList() {
        courseListBiz.requestMasterList(TAG_MASTER_LIST);
    }

    //根据masterid获取课程列表 包含Banner信息
    public void requestCourseListByMaterId(String master_id, String page, String page_size) {
        courseListBiz.requestCourseListByMasterId(TAG_COURSE_PRIVATE, master_id, page, page_size);
    }


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_CLASS_LIST:
                List<CourseClassBean> courseClassList = JsonUtils.getJsonToList(serverResponse.getData(), CourseClassBean.class);
                courseListView.getClassListSuccess(courseClassList);
                break;
            case TAG_COURSE_SORTING:
                List<HomeCourseBean> courseBeanList = JsonUtils.getJsonToList(serverResponse.getData(), HomeCourseBean.class);
                courseListView.getCourseListSuccess(courseBeanList);
                break;

            case TAG_COURSE_COMMUNITY:
                CourseWithBannerBean courseWithBannerBean = JsonUtils.getJsonToBean(serverResponse.getData(), CourseWithBannerBean.class);
                courseListView.getCourseWithBannerSuccess(courseWithBannerBean);
                break;
            case TAG_MASTER_LIST:
                List<MasterBean> masterBeanList = JsonUtils.getJsonToList(serverResponse.getData(), MasterBean.class);
                courseListView.getMasterListSuccess(masterBeanList);

                break;

            case TAG_COURSE_PRIVATE:
                CourseWithBannerBean courseListByMasterId = JsonUtils.getJsonToBean(serverResponse.getData(), CourseWithBannerBean.class);
                courseListView.getCourseWithBannerSuccess(courseListByMasterId);
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
