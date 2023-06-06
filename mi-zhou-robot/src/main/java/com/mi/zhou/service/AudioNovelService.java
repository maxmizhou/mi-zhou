package com.mi.zhou.service;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/10
 */
public interface AudioNovelService {


    public void downloadAudioNovel(String url) throws IOException, ParseException;
}
