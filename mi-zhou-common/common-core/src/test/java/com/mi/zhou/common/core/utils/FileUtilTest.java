package com.mi.zhou.common.core.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/18
 */
public class FileUtilTest {
    private final static Logger log = LoggerFactory.getLogger(FileUtilTest.class);


    @Test
    public void list() {
        List<File> list = FileUtil.list("D:\\data\\", "*.xml");
        for (File file : list) {
            log.info("file:{}", file.getName());
        }
    }

    @Test
    public void getFileName() {
        String fullName = "D:/data/applogs/xxl-job/xxl-job-admin.log";
        String fileName = FileUtil.getFilename(fullName);
        String tempDirPath = FileUtil.getTempDirPath();
        log.info("fileName:{}", fileName);
        log.info("tempDirPath:{}", tempDirPath);
    }


    @Test
    public void read() {
        String fullName = "D:/data/applogs/xxl-job/xxl-job-admin.log";
        String s = FileUtil.readToString(new File(fullName));
        log.info("s:{}", s);
        FileUtil.readLines(fullName).forEach(log::info);

    }


    @Test
    public void move() {
        String sourcePath = new File("D:/project/gitee/mica/mica-core/src/main/java/net/dreamlu/mica/core").getPath();
        String destPath = new File("F:/DDD/core").getPath();
        String oldPackage = "net.dreamlu.mica.core";
        String newPackage = "com.mi.zhou.common.core";
        String oldClassName = "Mica";
        String newClassName = "Mi";
        String oldAuthor = "@author L.cm";
        String newAuthor = "@author Mi";


        List<File> list = FileUtil.list(sourcePath);
        for (File file : list) {
            String oldFile = file.getPath();
            String newFile = StringUtil.replace(oldFile, sourcePath, destPath);
            newFile = newFile.replace(oldClassName, newClassName);
            File newFileF = FileUtil.createFile(newFile);
            List<String> lines = FileUtil.readLines(file);
            for (String line : lines) {
                if (line.contains(oldPackage)) {
                    line = line.replace(oldPackage, newPackage);
                }
                if (line.contains(oldAuthor)) {
                    line = line.replace(oldAuthor, newAuthor);
                }
                if (line.contains(oldClassName)) {
                    line = line.replace(oldClassName, newClassName);
                }
                FileUtil.writeToFile(newFileF, line, Charset.defaultCharset(), true);
                FileUtil.writeToFile(newFileF, "\n", Charset.defaultCharset(), true);
            }

        }
    }

}
