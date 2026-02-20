package com.shopping.musinsabackend.domain.product.controller;

import com.shopping.musinsabackend.domain.product.dto.request.BrandCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.BrandCreateResponse;
import com.shopping.musinsabackend.domain.product.dto.response.ProductCreateResponse;
import com.shopping.musinsabackend.domain.product.service.BrandService;
import com.shopping.musinsabackend.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "브랜드 생성 API")
public class BrandController {

    private final BrandService brandService;

    // 브랜드 생성 API
    @Operation(summary = "브랜드 등록 API")
    @PostMapping("/create-brand")
    public ResponseEntity<BaseResponse<BrandCreateResponse>> createBrand(@RequestBody @Valid BrandCreateRequest request) {

        // 서비스 호출
        BrandCreateResponse brandCreateResponse = brandService.createBrand(request);
        
        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "브랜드 등록 성공", brandCreateResponse));
    }
}
