package com.tuodanhuashu.app.huashu.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tuodanhuashu.app.home.bean.HomeAdvertisingBean;

public class MutiTypeBean implements MultiItemEntity{
        public static final int COMMON = 1;
        public static final int NO_VIP = 2;
        public static final int AD = 3;
        private int itemType = 1;
        public TalkSkillListItemBean talkSkillItemBean;

        public HomeAdvertisingBean advertisingBean;

    public MutiTypeBean(int itemType) {
        this.itemType = itemType;
    }

    public MutiTypeBean(int itemType,TalkSkillListItemBean talkSkillItemBean) {
        this.itemType = itemType;
        this.talkSkillItemBean = talkSkillItemBean;
    }

    public MutiTypeBean(int itemType, HomeAdvertisingBean advertisingBean) {
        this.itemType = itemType;
        this.advertisingBean = advertisingBean;
    }

    @Override
        public int getItemType() {
            return itemType;
        }
}
