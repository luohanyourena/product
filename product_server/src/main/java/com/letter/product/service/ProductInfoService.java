package com.letter.product.service;

import com.letter.product.common.DecreaseStockInput;
import com.letter.product.common.ProductInfoOutput;
import com.letter.product.dataobject.ProductInfo;

import java.util.List;

public interface ProductInfoService {
     List<ProductInfo> findByUpProduct();

     List<ProductInfoOutput> findByProductIdIn(List<String> ids);

    void buckleInventory(List<DecreaseStockInput> carDTOS);
}
