package com.example.demo.exception.info;

import com.example.demo.exception.ApiException;

/**
 * 分类编码存在异常
 */
public class InfoCategoryRepeatException extends ApiException {
    public InfoCategoryRepeatException() {
        this.setCode("0011");
        this.setMessage("当前分类存在这个编码！");
    }
}
