package com.bajrix.marketplace.service.impl;

import com.bajrix.marketplace.dto.SellerResponse;
import com.bajrix.marketplace.entity.SellerStatus;
import com.bajrix.marketplace.repository.SellerRepository;
import com.bajrix.marketplace.service.SellerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @GetMapping
    public List<SellerResponse> listApprovedSellers() {
        return sellerRepository.findByStatus(SellerStatus.APPROVED)
                .stream().map(SellerResponse::from).toList();
    }
}
