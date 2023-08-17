package com.itniuma.service.utils;

import lombok.Data;

@Data
public class IPage {
    private Object records;
    private Integer total;

    private Integer currentPage;
    private Integer pageSize;

    public IPage() {
    }

    public IPage(Object records, Integer total, Integer currentPage, Integer pageSize) {
        this.records = records;
        this.total = total;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
