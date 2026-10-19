# bajrix-marketplace

Multi-seller construction products marketplace

*FRONTEND

Stack
HTML
CSS
JavaScript

A responsive, frontend-only prototype for the BajriX multi-seller construction-material marketplace challenge. It uses **plain HTML, CSS and vanilla JavaScript** — no React, framework, package manager or build step.

## Run it

1. Extract the ZIP.
2. Open `index.html` in a modern browser.
3. For a local server, you can use VS Code Live Server, or run `python -m http.server 8000` in this folder and visit `http://localhost:8000`.

## Included

- Buyer marketplace with search, category filters, sorting and seller-offer comparison.
- Listing cards show lowest approved seller offer, stock and minimum order quantity.
- Seller dashboard with seller selector, listing metrics, search, edit, pause/resume and remove actions.
- Add/edit listing modal with client-side validation for price, stock and minimum order quantity.
- Responsive layout for desktop, tablet and mobile.
- Seed data shows multiple sellers for the same product and a pending seller.
- Data persists in browser `localStorage`.

## Important limitations

This is a **frontend prototype only**. It has no REST API, Java/Spring Boot backend, PostgreSQL database, real authentication, server-side authorization or multi-user synchronization. `localStorage` is local to the browser and is not secure. The seller selector is only a demo UI; it must not be treated as authorization. In a real application, the backend must enforce seller ownership, seller approval visibility, validation and duplicate-listing rules. A production version should use API pagination/search, database constraints and concurrency-safe updates.

## Suggested API integration later

- `GET /api/products?search=&category=&sort=&page=&size=`
- `GET /api/products/{productId}/offers`
- `GET /api/seller/listings` (seller identity determined by backend session)
- `POST /api/seller/listings`
- `PATCH /api/seller/listings/{listingId}`
- `DELETE /api/seller/listings/{listingId}`

These are suggested integration contracts, not implemented endpoints.


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


*BACKEND

A Spring Boot REST API for a multi-seller construction-products marketplace. This repository contains the backend only; it does not include React or any frontend.

Stack
Java 21
Spring Boot 3.5.6
Maven
Spring Web, Spring Data JPA, Jakarta Validation
MySQL 8+
Run locally
Install JDK 21, Maven (or use IntelliJ's bundled Maven), and MySQL.
Create a database: CREATE DATABASE bajrix_db;
Set database credentials in src/main/resources/application.yml or environment variables DB_URL, DB_USERNAME, DB_PASSWORD.
Open this folder in IntelliJ IDEA and reload Maven.
Run com.bajrix.marketplace.MarketplaceApplication.
API runs at http://localhost:8080.
spring.jpa.hibernate.ddl-auto=update is convenient for this coding challenge. For production, prefer reviewed database migrations.

Mock seller identity
Seller write/listing-management endpoints use X-Seller-Id as a demo identity header. This is intentionally mocked and is not production authentication. A real deployment must derive identity from authenticated credentials and enforce authorization centrally.

Sample sellers are seeded on first startup:

Seller 1: Shree Traders (APPROVED)
Seller 2: BuildMart Supplies (APPROVED)
Seller 3: Pending Materials (PENDING)
The exact IDs may differ if you already have data in the database. Query GET /api/sellers for approved sellers.

API endpoints
Buyer
GET /api/products?q=cement&category=Cement&page=0&size=12&sortBy=name&direction=asc
GET /api/products/{id}
GET /api/products/{id}/offers (approved sellers and active/in-stock listings only)
GET /api/sellers (approved sellers)
Seller listing management
GET /api/seller/listings with header X-Seller-Id: 1
POST /api/seller/listings with header X-Seller-Id: 1
PUT /api/seller/listings/{listingId} with header X-Seller-Id: 1
PATCH /api/seller/listings/{listingId}/status?status=INACTIVE with header X-Seller-Id: 1
Example create body:

{
"productId": 1,
"price": 385.00,
"stockQuantity": 200,
"minimumOrderQuantity": 5,
"status": "ACTIVE"
}
Example update body:

{
"price": 380.00,
"stockQuantity": 180,
"minimumOrderQuantity": 5,
"status": "ACTIVE"
}
Design notes
Product data is separate from seller-specific listings.
(product_id, seller_id) is unique to prevent duplicate listings by the same seller for the same product.
Validation rejects invalid prices, negative stock, and invalid minimum order quantities.
@Version on listings enables optimistic locking against concurrent updates.
APIs use pagination for product search.
Sellers that are not APPROVED cannot create or update listings; buyer offers exclude non-approved sellers.
No checkout, payment processing, or production authentication is implemented, as those are outside the challenge scope.
Limitations / next improvements
Add proper authentication and role-based authorization.
Add integration tests with a disposable MySQL instance (for example, Testcontainers).
Add database migrations instead of relying on Hibernate schema updates.
Consider stronger query indexing and query plans for large catalogues.
