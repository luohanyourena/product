package com.letter.product.service.impl;

import com.letter.product.common.DecreaseStockInput;
import com.letter.product.common.ProductInfoOutput;
import com.letter.product.dataobject.ProductInfo;
import com.letter.product.enums.ExceptionEnum;
import com.letter.product.enums.ProductEnum;
import com.letter.product.exception.ProductException;
import com.letter.product.repository.ProductInfoRepository;
import com.letter.product.service.ProductInfoService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceimpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findByUpProduct() {
        return repository.findByProductStatus(ProductEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findByProductIdIn(List<String> ids) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ProductInfoOutput> outputs = new ArrayList<>();
        List<ProductInfo> productInfos = repository.findByProductIdIn(ids);
        for (ProductInfo productInfo : productInfos) {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(productInfo,output);
            outputs.add(output);
        }
        return outputs;
    }

    @Override
    @Transactional
    public void buckleInventory(List<DecreaseStockInput> carDTOS) {
        //扣库存
        List<ProductInfo> list = buckleInventoryDB(carDTOS);
        List<ProductInfoOutput> outputs = list.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e,output);
            return output;
        }).collect(Collectors.toList());
        //将库存信息通知给order服务

        amqpTemplate.convertAndSend("productInfo",outputs);
    }

    public List<ProductInfo> buckleInventoryDB(List<DecreaseStockInput> carDTOS) {
        List<ProductInfo> outputs = new ArrayList<>();
        //扣库存
        for (DecreaseStockInput carDto : carDTOS) {
            //判断该产品是否存在
            Optional<ProductInfo> productInfoOptional= repository.findById(carDto.getProductId());
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ExceptionEnum.PRODUCT_NOT_EXIT);
            }
            //判断库存是否足够
            ProductInfo productInfo = productInfoOptional.get();
            Integer result = productInfo.getProductStock()- carDto.getProductQuantity();
            if (result<0){
                throw new ProductException(ExceptionEnum.STOCK_EORR);
            }
            //更新至productInfo
            productInfo.setProductStock(result);
            repository.save(productInfo);

            outputs.add(productInfo);
        }
        return outputs;
    }
}
