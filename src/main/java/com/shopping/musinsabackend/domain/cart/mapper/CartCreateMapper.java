package com.shopping.musinsabackend.domain.cart.mapper;

import com.shopping.musinsabackend.domain.cart.dto.request.CartItemCreateRequest;
import com.shopping.musinsabackend.domain.cart.dto.response.CartItemCreateResponse;
import com.shopping.musinsabackend.domain.cart.entity.CartEntity;
import com.shopping.musinsabackend.domain.cart.entity.CartItemEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class CartCreateMapper {

    // Request DTO -> Entity
    public CartItemEntity toEntity(CartItemCreateRequest request, CartEntity cart, ProductEntity product) {
        return CartItemEntity.builder()
                .cart(cart)
                .product(product)
                .itemCount(request.getItemCount())
                .build();
    }

    // Entity -> Response DTO
    public CartItemCreateResponse toResponse(CartItemEntity cartItem) {

        ProductEntity product = cartItem.getProduct();
        Integer totalPrice = cartItem.getProduct().getPrice() * cartItem.getItemCount();

        String imageUrl = null;
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            imageUrl = product.getImages().get(0).getImageUrl();
        }

        return CartItemCreateResponse.builder()
                .cartItemId(cartItem.getCartItemId())
                .productId(cartItem.getProduct().getProductId())
                .productName(cartItem.getProduct().getProductName())
                .imageUrl(imageUrl)
                .itemCount(cartItem.getItemCount())
                .totalPrice(totalPrice)
                .build();
    }
}
