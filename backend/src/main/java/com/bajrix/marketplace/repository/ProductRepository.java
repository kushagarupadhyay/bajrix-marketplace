package com.bajrix.marketplace.repository;

import com.bajrix.marketplace.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        select p from Product p
        where p.active = true
          and (:q is null or lower(p.name) like lower(concat('%', :q, '%'))
               or lower(coalesce(p.brand, '')) like lower(concat('%', :q, '%')))
          and (:category is null or lower(p.category) = lower(:category))
        """)
    Page<Product> search(@Param("q") String q, @Param("category") String category, Pageable pageable);
}
