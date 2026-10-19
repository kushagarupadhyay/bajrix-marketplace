package com.bajrix.marketplace.controller;

import com.bajrix.marketplace.dto.SellerResponse;
import com.bajrix.marketplace.service.SellerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerServicee;

    public SellerController(SellerService sellerService) {
        this.sellerServicee = sellerService;
    }

    @GetMapping
    public List<SellerResponse> listApprovedSellers() {
        return sellerServicee.listApprovedSellers();
    }
}
