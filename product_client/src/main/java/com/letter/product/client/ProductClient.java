package com.letter.product.client;



import com.letter.product.common.DecreaseStockInput;
import com.letter.product.common.ProductInfoOutput;
//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product",fallback = ProductClientFallback.class)
public interface ProductClient {
    @GetMapping("/product/msg")
    String productMsg();

    @PostMapping("/product/productInfoByIds")
     List<ProductInfoOutput> findByProductIdIn(@RequestBody List<String> ids);

    @PostMapping("/product/buckleInventory")
     void buckleInventory (@RequestBody List<DecreaseStockInput> carDTOS);
}
