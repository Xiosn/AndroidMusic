package com.viewpagertext.Lrc;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//=========================================================================================================


//    ####DefaultLrcBuilder歌词解析构造器
//
//        DefaultLrcBuilder实现ILrcBuilder接口，List getLrcRows(String rawLrc)方法会循环地读取歌词的每一行，
//        然后调用LrcRow类的List createRows(String standardLrcLine)方法，得到解析每一行歌词之后的LrcRow集合，
//        再将每一行得到LrcRow集合中得到的LrcRow实体加入一个总 的到LrcRow集合rows中去，然后将rows集合根据歌词行的时间排序，
//        得到排序后的LrcRow集合，该集合就是最终的解析歌词后的内容了。


//=========================================================================================================




/**
 * 解析歌词，得到LrcRow的集合
 */
public class DefaultLrcBuilder implements ILrcBuilder {
    static final String TAG = "DefaultLrcBuilder";

    public List<LrcRow> getLrcRows(String rawLrc) {
        Log.d(TAG,"getLrcRows by rawString");
        if(rawLrc == null || rawLrc.length() == 0){
            Log.e(TAG,"getLrcRows rawLrc null or empty");
            return null;
        }
        StringReader reader = new StringReader(rawLrc);
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        List<LrcRow> rows = new ArrayList<LrcRow>();
        try{
            //循环地读取歌词的每一行
            do{
                line = br.readLine();
                /**
                 一行歌词只有一个时间的  例如：徐佳莹   《我好想你》
                 [01:15.33]我好想你 好想你

                 一行歌词有多个时间的  例如：草蜢 《失恋战线联盟》
                 [02:34.14][01:07.00]当你我不小心又想起她
                 [02:45.69][02:42.20][02:37.69][01:10.60]就在记忆里画一个叉
                 **/
                Log.d(TAG,"lrc raw line: " + line);
                if(line != null && line.length() > 0){
                    //解析每一行歌词 得到每行歌词的集合，因为有些歌词重复有多个时间，就可以解析出多个歌词行来
                    List<LrcRow> lrcRows = LrcRow.createRows(line);
                    if(lrcRows != null && lrcRows.size() > 0){
                        for(LrcRow row : lrcRows){
                            rows.add(row);
                        }
                    }
                }
            }while(line != null);

            if( rows.size() > 0 ){
                // 根据歌词行的时间排序
                Collections.sort(rows);
                if(rows!=null&&rows.size()>0){
                    for(LrcRow lrcRow:rows){
                        Log.d(TAG, "lrcRow:" + lrcRow.toString());
                    }
                }
            }
        }catch(Exception e){
            Log.e(TAG,"parse exceptioned:" + e.getMessage());
            return null;
        }finally{
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();
        }
        return rows;
    }
}