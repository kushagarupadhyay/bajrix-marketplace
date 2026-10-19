package com.bajrix.marketplace.service.impl;

import com.bajrix.marketplace.dto.ProductResponse;
import com.bajrix.marketplace.entity.Product;
import com.bajrix.marketplace.exception.ResourceNotFoundException;
import com.bajrix.marketplace.repository.ProductRepository;
import com.bajrix.marketplace.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository products;

    public ProductServiceImpl(ProductRepository products) {
        this.products = products;
    }

    public Page<ProductResponse> search(String q, String category, int page, int size, String sortBy, String direction) {
        if (page < 0) throw new IllegalArgumentException("page must be zero or greater");
        if (size < 1 || size > 100) throw new IllegalArgumentException("size must be between 1 and 100");
        String safeSort = switch (sortBy == null ? "name" : sortBy) {
            case "name", "category", "createdAt" -> sortBy == null ? "name" : sortBy;
            default -> "name";
        };
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, safeSort));
        return products.search(blankToNull(q), blankToNull(category), pageable).map(ProductResponse::from);
    }

    public ProductResponse getById(Long id) {
        Product p = products.findById(id)
                .filter(Product::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Active product not found: " + id));
        return ProductResponse.from(p);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
