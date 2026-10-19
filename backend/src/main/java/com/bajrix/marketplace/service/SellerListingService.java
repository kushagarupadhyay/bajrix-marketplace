package com.bajrix.marketplace.service;

import com.bajrix.marketplace.dto.ListingRequest;
import com.bajrix.marketplace.dto.ListingResponse;
import com.bajrix.marketplace.dto.ListingUpdateRequest;
import com.bajrix.marketplace.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SellerListingService {

    @Transactional(readOnly = true)
    public List<ListingResponse> buyerOffers(Long productId);


    @Transactional(readOnly = true)
    public List<ListingResponse> myListings(Long sellerId);


    @Transactional
    public ListingResponse create(Long sellerId, ListingRequest request);


    @Transactional
    public ListingResponse update(Long sellerId, Long listingId, ListingUpdateRequest request);

    @Transactional
    public ListingResponse changeStatus(Long sellerId, Long listingId, ListingStatus status);

}
