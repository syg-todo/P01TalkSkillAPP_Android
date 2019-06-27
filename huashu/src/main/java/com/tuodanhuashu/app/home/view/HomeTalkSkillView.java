package com.tuodanhuashu.app.home.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.home.bean.HomeTalkSkillPageBean;

public interface HomeTalkSkillView extends BaseView {

    public void getDataSuccess(HomeTalkSkillPageBean homeTalkSkillPageBean);

    public void getDataFail(String msg);
}
