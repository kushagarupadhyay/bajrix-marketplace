package com.bajrix.marketplace.service;

import com.bajrix.marketplace.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    public Page<ProductResponse> search(String q, String category, int page, int size, String sortBy, String direction);

    public ProductResponse getById(Long id);


}
