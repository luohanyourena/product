package com.letter.product.controller;

import com.letter.product.common.DecreaseStockInput;
import com.letter.product.common.ProductInfoOutput;
import com.letter.product.config.GirlConfig;
import com.letter.product.dataobject.ProductCategory;
import com.letter.product.dataobject.ProductInfo;
import com.letter.product.service.ProductCategoryService;
import com.letter.product.service.ProductInfoService;
import com.letter.product.utils.ResultVOUtil;
import com.letter.product.vo.ProductInfoVO;
import com.letter.product.vo.ProductVO;
import com.letter.product.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@RefreshScope
public class ProductInfoCtrl {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private GirlConfig girlConfig;

    @Value("env")
    private String env;

    @GetMapping("/msg")
    public String productMsg(){
        return "girl-name:"+girlConfig.getName()+",girl-age:"+girlConfig.getAge();
    }

    @GetMapping("/list")
    public ResultVO<ProductVO> list(){
        List<ProductInfo> products = productInfoService.findByUpProduct();
        List<Integer> types = products.stream().
                map(ProductInfo::getCategoryType).
                collect(Collectors.toList());

        List<ProductCategory> categorys = productCategoryService.findByCategoryTypeIn(types);

        List<ProductVO> productVOS = new ArrayList<>();
        for (ProductCategory category : categorys) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            for (ProductInfo product : products) {
                if (product.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(product,productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOS(productInfoVOS);
            productVOS.add(productVO);
        }
        return ResultVOUtil.seccess(productVOS);
    }

    @PostMapping("/productInfoByIds")
    public List<ProductInfoOutput> findByProductIdIn(@RequestBody List<String> ids){
        return productInfoService.findByProductIdIn(ids);
    }

    @PostMapping("/buckleInventory")
    public void buckleInventory (@RequestBody List<DecreaseStockInput> carDTOS){
        productInfoService.buckleInventory(carDTOS);
    }
}
