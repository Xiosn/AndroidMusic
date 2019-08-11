package com.viewpagertext.constructor;

/**
 * name:小龙虾
 * time:2019.6.14
 * type:定义用来传输的数据的类型,用于歌单详情页传递数据网络到播放页
 * GitHub:https://github.com/greenrobot/EventBus
 */

public class ListToPlayEvent {
    private String Pic;
    private String Name;
    private String Singer;
    private String Lrc;
    private String url;
    private String time;

    public ListToPlayEvent(String Pic,String Name,String Singer,String Lrc,String url,String time){
        this.Pic=Pic;
        this.Name=Name;
        this.Singer=Singer;
        this.Lrc=Lrc;
        this.url=url;
        this.time=time;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        Singer = singer;
    }

    public String getLrc() {
        return Lrc;
    }

    public void setLrc(String lrc) {
        Lrc = lrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
