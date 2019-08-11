package com.viewpagertext.constructor;

/**
 * name:小龙虾
 * time:2019.5.4
 * type:定义用来传输的数据的类型,用于发现页传递数据歌单详情页
 * GitHub:https://github.com/greenrobot/EventBus
 */

public class MessageEvent {
    public String findGridItemName;

    public MessageEvent(String findGridItemName) {
        this.findGridItemName = findGridItemName;
    }

    public String getFindGridItemName() {
        return findGridItemName;
    }

}
