package com.letter.product.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecreaseStockInput implements Serializable {
    private String productId;

    private Integer productQuantity;
}
