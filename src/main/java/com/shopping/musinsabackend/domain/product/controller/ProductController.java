package com.shopping.musinsabackend.domain.product.controller;

import com.shopping.musinsabackend.domain.product.dto.request.ProductCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.ProductCreateResponse;
import com.shopping.musinsabackend.domain.product.service.ProductService;
import com.shopping.musinsabackend.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
