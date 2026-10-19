package com.bajrix.marketplace.repository;

import com.bajrix.marketplace.entity.ListingStatus;
import com.bajrix.marketplace.entity.SellerListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SellerListingRepository extends JpaRepository<SellerListing, Long> {
    boolean existsByProductIdAndSellerId(Long productId, Long sellerId);

    List<SellerListing> findBySellerIdOrderByUpdatedAtDesc(Long sellerId);

    Optional<SellerListing> findByIdAndSellerId(Long id, Long sellerId);

    @Query("""
        select l from SellerListing l
        join fetch l.product p
        join fetch l.seller s
        where p.id = :productId
          and p.active = true
          and l.status = :listingStatus
          and s.status = :sellerStatus
          and l.stockQuantity >= l.minimumOrderQuantity
        order by l.price asc
        """)
    List<SellerListing> findBuyerOffers(@Param("productId") Long productId,
                                        @Param("listingStatus") ListingStatus listingStatus,
                                        @Param("sellerStatus") com.bajrix.marketplace.entity.SellerStatus sellerStatus);
}
