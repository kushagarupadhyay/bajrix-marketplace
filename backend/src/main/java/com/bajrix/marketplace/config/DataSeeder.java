package com.bajrix.marketplace.config;

import com.bajrix.marketplace.entity.*;
import com.bajrix.marketplace.repository.ProductRepository;
import com.bajrix.marketplace.repository.SellerListingRepository;
import com.bajrix.marketplace.repository.SellerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedMarketplace(ProductRepository products, SellerRepository sellers,
                                     SellerListingRepository listings) {
        return args -> {
            if (products.count() > 0 || sellers.count() > 0) return;

            Product cement = products.save(new Product(
                    "UltraTech PPC Cement 50 kg", "UltraTech", "Cement", "bag",
                    "Portland pozzolana cement, 50 kg bag."));
            Product bricks = products.save(new Product(
                    "Red Clay Bricks", "Generic", "Bricks", "piece",
                    "Standard red clay construction bricks."));
            Product steel = products.save(new Product(
                    "TMT Steel Bar 12 mm", "Tata Tiscon", "Steel", "piece",
                    "12 mm TMT reinforcement bar."));

            Seller shree = sellers.save(new Seller("Shree Traders", "shree@example.com",
                    "Noida", SellerStatus.APPROVED));
            Seller buildmart = sellers.save(new Seller("BuildMart Supplies", "buildmart@example.com",
                    "Greater Noida", SellerStatus.APPROVED));
            Seller pending = sellers.save(new Seller("Pending Materials", "pending@example.com",
                    "Ghaziabad", SellerStatus.PENDING));

            listings.save(new SellerListing(cement, shree, new BigDecimal("390.00"), 500, 10, ListingStatus.ACTIVE));
            listings.save(new SellerListing(cement, buildmart, new BigDecimal("385.00"), 250, 5, ListingStatus.ACTIVE));
            listings.save(new SellerListing(cement, pending, new BigDecimal("375.00"), 100, 5, ListingStatus.ACTIVE));
            listings.save(new SellerListing(bricks, shree, new BigDecimal("8.50"), 5000, 100, ListingStatus.ACTIVE));
            listings.save(new SellerListing(steel, buildmart, new BigDecimal("720.00"), 150, 1, ListingStatus.ACTIVE));
        };
    }
}
