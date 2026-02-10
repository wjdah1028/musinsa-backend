package com.shopping.musinsabackend.domain.product.controller;

import com.shopping.musinsabackend.domain.product.dto.request.ProductCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.ProductCreateResponse;
import com.shopping.musinsabackend.domain.product.dto.response.ProductReadResponse;
import com.shopping.musinsabackend.domain.product.service.ProductService;
import com.shopping.musinsabackend.global.response.BaseResponse;
import com.shopping.musinsabackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록 API")
    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<ProductCreateResponse>> createProduct(
            @RequestPart(value = "data") @Valid ProductCreateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {

        // 서비스 호출
        ProductCreateResponse response = productService.createProduct(request, image);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "상품 등록 성공", response));
    }

    @Operation(summary = "상품 전체 조회 API")
    @GetMapping("/all-product")
    public ResponseEntity<BaseResponse<List<ProductReadResponse>>> getProductList() {

        // 서비스 호출
        List<ProductReadResponse> response = productService.allProductList();

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "상품 조회 성공", response));
    }

    @Operation(summary = "상품 상세 조회 API")
    @GetMapping("/detail-product/{productId}")
    public ResponseEntity<BaseResponse<ProductReadResponse>> detailProduct(@PathVariable Long productId) {

        // 서비스 호출
        ProductReadResponse response = productService.productInfo(productId);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "상품 상세 조회 성공", response));
    }

    @Operation(summary = "상품 삭제 API")
    @DeleteMapping("/{productId}")
    public ResponseEntity<BaseResponse<Void>> deleteProduct(@PathVariable Long productId) {

        // 서비스 호출
        productService.deleteProduct(productId);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "상품 삭제가 성공했습니다.", null));
    }
}
