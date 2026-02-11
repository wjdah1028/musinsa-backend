package com.shopping.musinsabackend.domain.cart.entity;

import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cart_item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId; // 장바구니 상품 고유번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private Integer itemCount; // 장바구니에 담긴 상품 수량

    public void addCount(Integer itemCount) {
        this.itemCount += itemCount;
    }
}
