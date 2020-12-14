package com.letter.product.message;

import com.letter.product.common.DecreaseStockInput;
import com.letter.product.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

/**
 * create:luohan
 */
@Component
@Slf4j
public class ProductMessageMQ {

    @Autowired
    private ProductInfoService productInfoService;
    //获取MQ中的消息队列扣除库存
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("orderChanage"),
            key = "product",
            value = @Queue("productQueue")
    ))
    public void processProduct(List<DecreaseStockInput> decreaseStockInputs){
        log.info("DecreaseStockInput，{}",decreaseStockInputs.size());
        productInfoService.buckleInventory(decreaseStockInputs);
    }
}
