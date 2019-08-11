package com.viewpagertext.Lrc;

/**
 * 歌词拖动时候的监听类
 *
 * 当歌词被用户上下拖动的时候回调该方法
 */


public interface ILrcViewListener {
    /**
     * 当歌词被用户上下拖动的时候回调该方法
     */
    void onLrcSeeked(int newPosition, LrcRow row);
}