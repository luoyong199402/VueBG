package com.example.demo.exception.info;

import com.example.demo.exception.ApiException;

public class InfoCategoryNotFoundException extends ApiException {
    public InfoCategoryNotFoundException() {
        this.setCode("0013");
        this.setMessage("没有找到这个节点！");
    }
}
