package com.letter.product.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {
    UP(0,"在架"),
    DOWN(1,"下架");
    private Integer code;
    private String message;
    ProductEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    };

}
