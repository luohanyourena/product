package com.letter.product.client;

import com.letter.product.common.DecreaseStockInput;
import com.letter.product.common.ProductInfoOutput;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * create:luohan
 */
@Component
public class ProductClientFallback implements ProductClient{
    @Override
    public String productMsg() {
        return "请求超时，请稍后再试";
    }

    @Override
    public List<ProductInfoOutput> findByProductIdIn(List<String> ids) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void buckleInventory(List<DecreaseStockInput> carDTOS) {

    }


}
