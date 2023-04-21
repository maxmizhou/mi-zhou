package com.mi.zhou.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/14
 */
public interface DownloadService {

    String getSourceHtml(String url, Map<String, Object> headers);


    Map<String, List<String>> parseHtml(String sourceHtml, String ParsePath) ;


    //下载视频
    void downloadVideo(String url, String fileName) ;

}
