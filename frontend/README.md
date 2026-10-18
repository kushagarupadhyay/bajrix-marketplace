# BajriX Frontend (HTML, CSS, JavaScript)

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
