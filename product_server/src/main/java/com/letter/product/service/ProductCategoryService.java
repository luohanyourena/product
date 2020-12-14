package com.letter.product.service;

import com.letter.product.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> types);
}
