package com.mx.study;

import com.mx.study.http.ChapterLink;
import com.mx.study.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.*;
import java.util.*;

/**
 * @author mengxu
 * @date 2020/6/17 11:42
 */
public class JsoupTest {
    public static String QQG = "https://www.biquge.tw";
    public static String savePath = "F:\\小说";
    public static String novelPath = "F:\\小说";

    public static void main(String[] args) throws Exception {
        String directory = HttpUtil.sendGet("https://www.biquge.tw/490_490533", null);
        Document document = Jsoup.parse(directory);
        Element body = document.body();
        List<Node> nodes = body.getElementById("list").getElementsByTag("dl").get(0).childNodes();

        Set<ChapterLink> chapterLinkSet = new HashSet<>();
        nodes.stream().forEach(node -> {
            if (node instanceof Element && ((Element) node).getElementsByTag("a").size() != 0) {
                ChapterLink chapterLink = new ChapterLink();
                chapterLink.setMinHref(((Element) node).getElementsByTag("a").get(0).attr("href"));
                chapterLink.setTitle(((Element) node).getElementsByTag("a").get(0).text());
                chapterLink.setHref(QQG + chapterLink.getMinHref());
                chapterLinkSet.add(chapterLink);
            }
        });
        List<ChapterLink> chapterLinkList = new LinkedList<>(chapterLinkSet);
        chapterLinkList.sort(Comparator.comparing(ChapterLink::getHref));
        for (ChapterLink chapterLink : chapterLinkList) {
            generateChapter(chapterLink);
        }
//        FileOutputStream fileOutputStream = new FileOutputStream(novelPath);
//        Writer writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
//        for(ChapterLink chapterLink : chapterLinkList) {
//            writer.write(chapterLink.getHref());
//        }
    }

    public static void generateChapter(ChapterLink chapterLink) throws Exception {
        String data = HttpUtil.sendGet(chapterLink.getHref(), null);
        Document document = Jsoup.parse(data);

        String title = document.getElementsByTag("title").text();
        String content = document.getElementById("content").text();

        content = content.replaceAll("[\n\r]", "");
        content = content.replaceAll("\\s+", "\r\n  ");

        String novelName = null;
        String[] splits = title.split("_");
        if (splits.length >= 2) {
            title = splits[0];
            novelName = splits[1];
        } else {
            title = splits[1];
        }

        novelPath = savePath + File.separator + novelName;
        File file = new File(novelPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        title = formatFileName(title);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(novelPath + File.separator + title + ".txt");
            fileOutputStream.write(content.getBytes());
        } catch (IOException e) {

        } finally {
            fileOutputStream.close();
        }
    }

    private static String formatFileName(String chapterName) {
        return chapterName.replaceAll("[/\\\\:;,.?]", "");
    }
}
