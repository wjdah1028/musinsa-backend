package com.shopping.musinsabackend.domain.product.controller;

import com.shopping.musinsabackend.domain.product.dto.request.CategoryCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.CategoryCreateResponse;
import com.shopping.musinsabackend.domain.product.dto.response.CategoryReadResponse;
import com.shopping.musinsabackend.domain.product.service.CategoryService;
import com.shopping.musinsabackend.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 2. 카테고리 전체 조회 (이게 없어서 조회를 못하셨던 겁니다!)
    @Operation(summary = "카테고리 전체 조회")
    @GetMapping // GET 요청을 받습니다.
    public ResponseEntity<List<CategoryReadResponse>> getCategoryList() {
        List<CategoryReadResponse> list = categoryService.getCategoryList();
        return ResponseEntity.ok(list);
    }
}
