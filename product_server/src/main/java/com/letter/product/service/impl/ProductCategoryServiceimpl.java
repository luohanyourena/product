package com.letter.product.service.impl;

import com.letter.product.dataobject.ProductCategory;
import com.letter.product.repository.ProductCategoryRepositroy;
import com.letter.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceimpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepositroy repositroy;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> types) {
        return repositroy.findByCategoryTypeIn(types);
    }
}
