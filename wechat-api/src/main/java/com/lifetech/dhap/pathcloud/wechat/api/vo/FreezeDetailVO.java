package com.lifetech.dhap.pathcloud.wechat.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-06-08.
 */
public class FreezeDetailVO {

    private List<SampleVO> samples;

    private List<BookVO> books;

    public List<SampleVO> getSamples() {
        return samples;
    }

    public void setSamples(List<SampleVO> samples) {
        this.samples = samples;
    }

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }
}
