package com.letter.product.exception;

import com.letter.product.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ProductException extends RuntimeException{
    private Integer code;

    public ProductException(Integer code,String message){
        super(message);
        this.code=code;
    }

    public ProductException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code=exceptionEnum.getCode();
    }
}
