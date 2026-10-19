package com.bajrix.marketplace.repository;

import com.bajrix.marketplace.entity.Seller;
import com.bajrix.marketplace.entity.SellerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByStatus(SellerStatus status);
}
