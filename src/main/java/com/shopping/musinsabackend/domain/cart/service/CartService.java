package com.shopping.musinsabackend.domain.cart.service;

import com.shopping.musinsabackend.domain.cart.dto.request.CartItemCreateRequest;
import com.shopping.musinsabackend.domain.cart.dto.response.CartItemCreateResponse;
import com.shopping.musinsabackend.domain.cart.entity.CartEntity;
import com.shopping.musinsabackend.domain.cart.entity.CartItemEntity;
import com.shopping.musinsabackend.domain.cart.exception.CartErrorCode;
import com.shopping.musinsabackend.domain.cart.mapper.CartCreateMapper;
import com.shopping.musinsabackend.domain.cart.repository.CartItemRepository;
import com.shopping.musinsabackend.domain.cart.repository.CartRepository;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import com.shopping.musinsabackend.domain.product.exception.ProductErrorCode;
import com.shopping.musinsabackend.domain.product.repository.ProductRepository;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartCreateMapper cartCreateMapper;

    // 장바구니에 상품 담는 로직
    public CartItemCreateResponse addCart(UserEntity user, CartItemCreateRequest request) {

        // 유저의 장바구니 여부 검사
        CartEntity cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(CartEntity.builder().user(user).build()));

        // 장바구니에 넣을 상품이 실제로 있는지 확인
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 이미 장바구니에 있는지 확인
        CartItemEntity cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (cartItem != null) {
            // 이미 있으면 수량 증가
            cartItem.addCount(request.getItemCount());
        }
        else {
            // 장바구니에 없으면 새로 증가
            cartItem = cartCreateMapper.toEntity(request, cart, product);
            cartItemRepository.save(cartItem);
        }

        log.info("장바구니에 상품 담기 성공: 상품 ID {}", product.getProductId());

        // 반환
        return cartCreateMapper.toResponse(cartItem);
    }

    // 장바구니 조회
    public List<CartItemCreateResponse> getCartList(UserEntity user) {

        // 해당 유저 장바구니 조회
        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(CartErrorCode.CART_NOT_FOUND));

        // 장바구니에 담긴 아이템 조회
        List<CartItemEntity> cartItems = cartItemRepository.findByCart(cart);

        // 장바구니가 비어있으면 예외 메시지 출력
        if (cartItems.isEmpty()) {
            throw new CustomException(CartErrorCode.EMPTY_CART);
        }

        // Entity -> DTO 변환
        return cartItems.stream().map(cartCreateMapper::toResponse).collect(Collectors.toList());
    }

    // 장바구니 개별 상품 삭제
    @Transactional
    public void deleteCartItem(UserEntity user, Long cartItemId) {

        // 해당 유저 장바구니 조회
        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(CartErrorCode.CART_NOT_FOUND));

        // 사용자의 장바구니에 해당 상품이 있는지 확인
        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(CartErrorCode.ITEM_NOT_FOUND));

        // 상품의 장바구니 ID와 사용자의 장바구니 ID가 일치하는지 확인
        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            throw new CustomException(CartErrorCode.INVALID_USER);
        }

        // 삭제
        cartItemRepository.delete(cartItem);

        log.info("장바구니 아이템 삭제 완료: 아이템 ID {}", cartItemId);
    }

    // 장바구니 상품 전체 삭제
    @Transactional
    public void deleteAllCartItems(UserEntity user) {

        // 해당 유저 장바구니 조회
        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(CartErrorCode.CART_NOT_FOUND));

        // 전체 삭제
        cartItemRepository.deleteByCart(cart);
    }
}
