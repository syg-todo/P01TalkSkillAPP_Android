package com.tuodanhuashu.app.huashu.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.huashu.bean.TalkSkillResultBean;

public interface TalkSkillListView extends BaseView {

    public void getTalkListSuccess(TalkSkillResultBean talkSkillResultBean);

    public void getTalkListFail(String msg);

    public void getMijiSuccess(String url);
}
