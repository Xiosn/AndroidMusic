package com.viewpagertext.json;

import java.util.List;

public class HotVideo {

    /**
     * code : 200
     * message : 成功!
     * result : [{"sid":"29615971","text":"狗子：姥姥我不想睡 姥姥：不，你想","type":"video","thumbnail":"http://wimg.spriteapp.cn/picture/2019/0625/93ff2854-96f0-11e9-8d8e-1866daeb0df1_wpd.jpg","video":"http://uvideo.spriteapp.cn/video/2019/0625/93ff2854-96f0-11e9-8d8e-1866daeb0df1_wpd.mp4","images":null,"up":"62","down":"6","forward":"1","comment":"1","uid":"20746659","name":"楚歌悲兮","header":"http://wimg.spriteapp.cn/profile/large/2019/03/26/5c99f6d9c6b72_mini.jpg","top_comments_content":null,"top_comments_voiceuri":null,"top_comments_uid":null,"top_comments_name":null,"top_comments_header":null,"passtime":"2019-06-26 02:53:01"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * sid : 29615971
         * text : 狗子：姥姥我不想睡 姥姥：不，你想
         * type : video
         * thumbnail : http://wimg.spriteapp.cn/picture/2019/0625/93ff2854-96f0-11e9-8d8e-1866daeb0df1_wpd.jpg
         * video : http://uvideo.spriteapp.cn/video/2019/0625/93ff2854-96f0-11e9-8d8e-1866daeb0df1_wpd.mp4
         * images : null
         * up : 62
         * down : 6
         * forward : 1
         * comment : 1
         * uid : 20746659
         * name : 楚歌悲兮
         * header : http://wimg.spriteapp.cn/profile/large/2019/03/26/5c99f6d9c6b72_mini.jpg
         * top_comments_content : null
         * top_comments_voiceuri : null
         * top_comments_uid : null
         * top_comments_name : null
         * top_comments_header : null
         * passtime : 2019-06-26 02:53:01
         */

        private String sid;
        private String text;
        private String type;
        private String thumbnail;
        private String video;
        private Object images;
        private String up;
        private String down;
        private String forward;
        private String comment;
        private String uid;
        private String name;
        private String header;
        private Object top_comments_content;
        private Object top_comments_voiceuri;
        private Object top_comments_uid;
        private Object top_comments_name;
        private Object top_comments_header;
        private String passtime;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getDown() {
            return down;
        }

        public void setDown(String down) {
            this.down = down;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public Object getTop_comments_content() {
            return top_comments_content;
        }

        public void setTop_comments_content(Object top_comments_content) {
            this.top_comments_content = top_comments_content;
        }

        public Object getTop_comments_voiceuri() {
            return top_comments_voiceuri;
        }

        public void setTop_comments_voiceuri(Object top_comments_voiceuri) {
            this.top_comments_voiceuri = top_comments_voiceuri;
        }

        public Object getTop_comments_uid() {
            return top_comments_uid;
        }

        public void setTop_comments_uid(Object top_comments_uid) {
            this.top_comments_uid = top_comments_uid;
        }

        public Object getTop_comments_name() {
            return top_comments_name;
        }

        public void setTop_comments_name(Object top_comments_name) {
            this.top_comments_name = top_comments_name;
        }

        public Object getTop_comments_header() {
            return top_comments_header;
        }

        public void setTop_comments_header(Object top_comments_header) {
            this.top_comments_header = top_comments_header;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }
    }
}
