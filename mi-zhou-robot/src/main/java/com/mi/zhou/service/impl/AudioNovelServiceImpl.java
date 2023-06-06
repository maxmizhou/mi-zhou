package com.mi.zhou.service.impl;

import com.mi.zhou.service.AudioNovelService;
import com.mi.zhou.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/10
 */
@Slf4j
public class AudioNovelServiceImpl implements AudioNovelService {
    String str = "D:\\project\\zlj\\mi-zhou\\mi-zhou-robot\\src\\main\\resources\\audio\\";

    String header = ":authority: m.ting275.com\n" +
            ":method: GET\n" +
            ":path: /book/16.html\n" +
            ":scheme: https\n" +
            "accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
            "accept-encoding: gzip, deflate, br\n" +
            "accept-language: zh-CN,zh;q=0.9,en;q=0.8\n" +
            "cache-control: no-cache\n" +
            "cookie: Hm_lvt_3fa0077fd7d807269fab93e71a0b26ab=1683680236; _gcl_au=1.1.1829137980.1683680248; Hm_lvt_75da8e027ebe3480c9b1beaaff92026a=1683680392; __gads=ID=1497889fc4d0b46a-22706ef7a5e0007e:T=1683688059:RT=1683688059:S=ALNI_MbGqQkyvrZ50Ryl4n7yCURQAplQ1Q; __gpi=UID=00000c03e0220bcf:T=1683688059:RT=1683688059:S=ALNI_Mbx1ztL80HAMqV8oD3Af_r9F2RBRw; Hm_lpvt_3fa0077fd7d807269fab93e71a0b26ab=1683695818; __cf_bm=IYKRi7IaQhCU2ZE8Oti2z5fYFraNtS4n6MdyaGRmsQc-1683696573-0-AYs0ZVNDJ6FanoB0+c/W/3GVgiTWjFKkcEBNI8iKSGmsLgHtUxBa6pJK7zaB7paSUxn5QuUinKVKk8t0xLGAg57Hy3pd7B2j1gHoUCZUYGPd; Hm_lpvt_75da8e027ebe3480c9b1beaaff92026a=1683698215\n" +
            "pragma: no-cache\n" +
            "sec-ch-ua: \"Chromium\";v=\"112\", \"Google Chrome\";v=\"112\", \"Not:A-Brand\";v=\"99\"\n" +
            "sec-ch-ua-mobile: ?0\n" +
            "sec-ch-ua-platform: \"Windows\"\n" +
            "sec-fetch-dest: document\n" +
            "sec-fetch-mode: navigate\n" +
            "sec-fetch-site: none\n" +
            "sec-fetch-user: ?1\n" +
            "upgrade-insecure-requests: 1\n" +
            "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";


  static   String header2 = ":authority: m.ting275.com\n" +
            ":method: GET\n" +
            ":path: /play/16/9570.html\n" +
            ":scheme: https\n" +
            "accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
            "accept-encoding: gzip, deflate, br\n" +
            "accept-language: zh-CN,zh;q=0.9,en;q=0.8\n" +
            "cache-control: no-cache\n" +
            "cookie: Hm_lvt_3fa0077fd7d807269fab93e71a0b26ab=1683680236; _gcl_au=1.1.1829137980.1683680248; Hm_lvt_75da8e027ebe3480c9b1beaaff92026a=1683680392; __gads=ID=1497889fc4d0b46a-22706ef7a5e0007e:T=1683688059:RT=1683688059:S=ALNI_MbGqQkyvrZ50Ryl4n7yCURQAplQ1Q; __gpi=UID=00000c03e0220bcf:T=1683688059:RT=1683688059:S=ALNI_Mbx1ztL80HAMqV8oD3Af_r9F2RBRw; Hm_lpvt_3fa0077fd7d807269fab93e71a0b26ab=1683695818; Hm_lpvt_75da8e027ebe3480c9b1beaaff92026a=1683701007; __cf_bm=MAmFLwsEEwkjkl7GvvpTe1Yqycc_8_WuLJjkbzU08Ow-1683701009-0-AebNn8EvyzS/Q5+K1JnVtIGQu2b6zbt5ELnVVOLe4Ntetmve9QJhAmJSxVw+az8dFPsM3FrbrhplR7uhRoTQLV/rOaCHif5yDP+eDBL1r2zg\n" +
            "pragma: no-cache\n" +
            "referer: https://m.ting275.com/play/16/9569.html\n" +
            "sec-ch-ua: \"Chromium\";v=\"112\", \"Google Chrome\";v=\"112\", \"Not:A-Brand\";v=\"99\"\n" +
            "sec-ch-ua-mobile: ?1\n" +
            "sec-ch-ua-platform: \"Android\"\n" +
            "sec-fetch-dest: document\n" +
            "sec-fetch-mode: navigate\n" +
            "sec-fetch-site: same-origin\n" +
            "upgrade-insecure-requests: 1\n" +
            "user-agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36";


    @Override
    public void downloadAudioNovel(String url) throws IOException, ParseException {
        Map<String, Object> headers = this.getHeaderMap(header);
        String fileNameMd5 = DigestUtils.md5DigestAsHex(url.getBytes());
        File file = new File(str + fileNameMd5 + "/source.txt");
        String sourceHtml = null;
        if ((!file.exists())) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
            sourceHtml = HttpUtil.get(url, headers, new HashMap<>());
            FileUtils.writeStringToFile(file, sourceHtml, Charset.defaultCharset());
        }
        if (!StringUtils.hasLength(sourceHtml)) {
            sourceHtml = FileUtils.readFileToString(file, Charset.defaultCharset());
        }
        sourceHtml = regexValue(sourceHtml.replace("\r\n", ""), "<div class=\"book-list clearfix\">(.*?)</div>");
        sourceHtml = sourceHtml.trim();
        File downloaded = new File(str + fileNameMd5 + "/downloaded.txt");
        List<String> lines = new ArrayList<>();
        if ((!downloaded.exists()) && file.createNewFile()) {
            lines = FileUtils.readLines(downloaded, Charset.defaultCharset());
        }
        String[] split = sourceHtml.split("                    ");
        for (String str : split) {
            String href = "https://m.ting275.com" + regexValue(str, "<a.*?href=\"([^\"]*?)\".*?>");
            String title = regexValue(str, "<a.*?title=\"([^\"]*?)\".*?>");
            String id = regexValue(str, "<a.*?>(.*?)</a>");
            if (lines.contains(href)) {
                continue;
            }
            if (downloadAudio(href, title, id)) {
                lines.add(href);
                break;
            }
        }
        FileUtils.writeLines(downloaded, lines, false);

    }

    private boolean downloadAudio(String href, String title, String id) throws IOException, ParseException {
        String sourceHtml = HttpUtil.get(href, this.getHeaderMap(header2), new HashMap<>());
        System.out.println("sourceHtml = " + sourceHtml);


        return false;
    }

    private String regexValue(String sourceHtml, String regex) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(sourceHtml);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return sourceHtml;
    }

    private Map<String, Object> getHeaderMap(String header) {
        HashMap<String, Object> headers = new HashMap<>();
        String[] split = header.split("\n");
        boolean startWith = false;
        for (String headerStr : split) {
            if (headerStr.startsWith(":")) {
                startWith = true;
                headerStr = headerStr.substring(1);
            }
            int indexOf = headerStr.indexOf(":");
            String headerKey = headerStr.substring(0, indexOf).trim();
            String headerValue = headerStr.substring(indexOf + 1).trim();
            if (startWith) {
                startWith = false;
                headerKey = ":" + headerKey;
            }
            headers.put(headerKey, headerValue);
        }
        return headers;
    }


    public static void main(String[] args) throws IOException, ParseException {
        AudioNovelServiceImpl service = new AudioNovelServiceImpl();
        //service.downloadAudioNovel("https://m.ting275.com/book/16.html");

        String sourceHtml = HttpUtil.get("https://m.ting275.com/play/16/8749.html", service.getHeaderMap(header2), new HashMap<>());
        System.out.println("sourceHtml = " + sourceHtml);


    }


}
