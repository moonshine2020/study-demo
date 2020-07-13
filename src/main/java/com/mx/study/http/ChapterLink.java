package com.mx.study.http;

/**
 * @author mengxu
 * @date 2020/6/17 11:26
 */
public class ChapterLink {
    private String href;
    private String title;
    private String minHref;

    @Override
    public int hashCode() {
        return this.minHref.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.minHref != null && (this.minHref).equals(((ChapterLink) obj).getMinHref());
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMinHref() {
        return minHref;
    }

    public void setMinHref(String minHref) {
        this.minHref = minHref;
    }
}
