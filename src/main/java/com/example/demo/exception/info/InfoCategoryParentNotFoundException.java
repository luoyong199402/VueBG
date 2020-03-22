package com.example.demo.exception.info;

import com.example.demo.exception.ApiException;

public class InfoCategoryParentNotFoundException extends ApiException {
    public InfoCategoryParentNotFoundException() {
        this.setCode("0012");
        this.setMessage("没有找到父节点！");
    }
}
