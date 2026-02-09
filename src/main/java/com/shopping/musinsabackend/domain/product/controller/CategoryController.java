package com.shopping.musinsabackend.domain.product.controller;

import com.shopping.musinsabackend.domain.product.dto.request.CategoryCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.CategoryCreateResponse;
import com.shopping.musinsabackend.domain.product.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 생성 API")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 등록 API")
    @PostMapping("/create-category")
    public ResponseEntity<BaseResponse<CategoryCreateResponse>> create(@RequestBody @Valid CategoryCreateRequest request) {

        // 서비스 호출
        CategoryCreateResponse categoryCreateResponse = categoryService.create(request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "카테고리 등록 성공", categoryCreateResponse));
    }
}
