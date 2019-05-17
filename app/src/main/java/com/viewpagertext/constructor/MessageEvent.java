package com.viewpagertext.constructor;

import android.graphics.Bitmap;

/**
 * name:小龙虾
 * time:2019.5.4
 * type:定义用来传输的数据的类型
 * GitHub:https://github.com/greenrobot/EventBus
 */

public class MessageEvent {
    public String findGridItemName,detailSongName,singer;
    public Bitmap image;

    public MessageEvent(String findGridItemName, Bitmap image,String detailSongName,String singer) {
        this.findGridItemName = findGridItemName;
        this.image=image;
        this.detailSongName=detailSongName;
        this.singer=singer;
    }

    public String getFindGridItemName() {
        return findGridItemName;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDetailSongName() {
        return detailSongName;
    }

    public String getSinger() {
        return singer;
    }
}
