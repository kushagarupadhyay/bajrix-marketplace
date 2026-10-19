# BajriX Marketplace Backend

A Spring Boot REST API for a multi-seller construction-products marketplace. This repository contains the backend only; it does not include React or any frontend.

## Stack
- Java 21
- Spring Boot 3.5.6
- Maven
- Spring Web, Spring Data JPA, Jakarta Validation
- MySQL 8+

## Run locally
1. Install JDK 21, Maven (or use IntelliJ's bundled Maven), and MySQL.
2. Create a database: `CREATE DATABASE bajrix_marketplace;`
3. Set database credentials in `src/main/resources/application.yml` or environment variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
4. Open this folder in IntelliJ IDEA and reload Maven.
5. Run `com.bajrix.marketplace.MarketplaceApplication`.
6. API runs at `http://localhost:8080`.

`spring.jpa.hibernate.ddl-auto=update` is convenient for this coding challenge. For production, prefer reviewed database migrations.

## Mock seller identity
Seller write/listing-management endpoints use `X-Seller-Id` as a demo identity header. This is intentionally mocked and is **not production authentication**. A real deployment must derive identity from authenticated credentials and enforce authorization centrally.

Sample sellers are seeded on first startup:
- Seller 1: Shree Traders (APPROVED)
- Seller 2: BuildMart Supplies (APPROVED)
- Seller 3: Pending Materials (PENDING)

The exact IDs may differ if you already have data in the database. Query `GET /api/sellers` for approved sellers.

## API endpoints

### Buyer
- `GET /api/products?q=cement&category=Cement&page=0&size=12&sortBy=name&direction=asc`
- `GET /api/products/{id}`
- `GET /api/products/{id}/offers` (approved sellers and active/in-stock listings only)
- `GET /api/sellers` (approved sellers)

### Seller listing management
- `GET /api/seller/listings` with header `X-Seller-Id: 1`
- `POST /api/seller/listings` with header `X-Seller-Id: 1`
- `PUT /api/seller/listings/{listingId}` with header `X-Seller-Id: 1`
- `PATCH /api/seller/listings/{listingId}/status?status=INACTIVE` with header `X-Seller-Id: 1`

Example create body:
```json
{
  "productId": 1,
  "price": 385.00,
  "stockQuantity": 200,
  "minimumOrderQuantity": 5,
  "status": "ACTIVE"
}
```

Example update body:
```json
{
  "price": 380.00,
  "stockQuantity": 180,
  "minimumOrderQuantity": 5,
  "status": "ACTIVE"
}
```

## Design notes
- Product data is separate from seller-specific listings.
- `(product_id, seller_id)` is unique to prevent duplicate listings by the same seller for the same product.
- Validation rejects invalid prices, negative stock, and invalid minimum order quantities.
- `@Version` on listings enables optimistic locking against concurrent updates.
- APIs use pagination for product search.
- Sellers that are not APPROVED cannot create or update listings; buyer offers exclude non-approved sellers.
- No checkout, payment processing, or production authentication is implemented, as those are outside the challenge scope.

## Limitations / next improvements
- Add proper authentication and role-based authorization.
- Add integration tests with a disposable MySQL instance (for example, Testcontainers).
- Add database migrations instead of relying on Hibernate schema updates.
- Consider stronger query indexing and query plans for large catalogues.
