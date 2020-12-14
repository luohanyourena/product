package com.letter.product.enums;

import lombok.Getter;

@Getter
public enum  ExceptionEnum {
    PRODUCT_NOT_EXIT(1,"产品不存在"),
    STOCK_EORR(2,"库存错误");

    private Integer code;
    private String message;

    ExceptionEnum(Integer code,String message){
        this.code=code;
        this.message =message;
    }
}
