package com.mx.study.http;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mengxu
 * @date 2020/6/17 9:25
 */
public class GetHtmlData {

    public static void main(String[] args) throws Exception {
        String directory = HttpUtil.sendGet("https://www.biquge.tw/490_490533/", null);
        String content = getContent(directory, "<div id=\"list\">", "</div>");
        System.out.println(content);
    }

    public static void getChapterContent(ChapterLink chapterLink) throws Exception {
        String data = HttpUtil.sendGet(chapterLink.getHref(), null);
        Pattern title = Pattern.compile("<title>([\\s\\S]*)</title>");
        Matcher titleMatcher = title.matcher(data);
        String chapterName = null;
        if (titleMatcher.find(0)) {
            chapterName = titleMatcher.group(1);
            System.out.println(chapterName);
        }

        String novel = null;
        String[] splits = chapterName.split("_");
        if (splits.length >= 2) {
            chapterName = splits[0];
            novel = splits[1];
        } else {
            chapterName = splits[1];
        }

        String filePath = "F:\\小说\\" + novel;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        chapterName = formatFileName(chapterName);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath + File.separator + chapterName + ".txt");

        String content = getContent(data, "<div id=\"content\">", "</div>");
        content = content.replaceAll("(&nbsp;)|(<script[\\s\\S]*script>)", "");
        content = content.replaceAll("(</br>)|(<br/>)", "\r\n");
        fileOutputStream.write(content.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private static String getContent(String data, String startStr, String endStr) {
        int startIndex = data.indexOf(startStr);
        int endIndex = data.indexOf(endStr, startIndex);
        return data.substring(startIndex + 18, endIndex);
    }

    private static String formatFileName(String chapterName) {
        return chapterName.replaceAll("[/\\:;,.?]", "");
    }

}
