package com.bajrix.marketplace.service;

import com.bajrix.marketplace.dto.SellerResponse;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface SellerService {

    @GetMapping
    public List<SellerResponse> listApprovedSellers();
}
