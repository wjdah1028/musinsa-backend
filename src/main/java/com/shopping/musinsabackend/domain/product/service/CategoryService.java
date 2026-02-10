package com.shopping.musinsabackend.domain.product.service;

import com.shopping.musinsabackend.domain.product.dto.request.CategoryCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.CategoryCreateResponse;
import com.shopping.musinsabackend.domain.product.dto.response.CategoryReadResponse;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import com.shopping.musinsabackend.domain.product.exception.ProductErrorCode;
import com.shopping.musinsabackend.domain.product.mapper.CategoryCreateMapper;
import com.shopping.musinsabackend.domain.product.mapper.CategoryReadMapper;
import com.shopping.musinsabackend.domain.product.repository.CategoryRepository;
import com.shopping.musinsabackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCreateMapper categoryCreateMapper;
    private final CategoryReadMapper categoryReadMapper;

    // 새로운 카테고리 생성
    public CategoryCreateResponse create(CategoryCreateRequest request) {

        // 중복 검사
        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new CustomException(ProductErrorCode.CATEGORY_ALREADY_EXIST);
        }

        // 부모 카테고리 찾기
        CategoryEntity parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CustomException(ProductErrorCode.CATEGORY_NOT_FOUND));
        }

        // DTO -> Entity
        CategoryEntity category = categoryCreateMapper.toEntity(request, parent);

        // DB에 저장
        CategoryEntity savedCategory = categoryRepository.save(category);

        // 로그 남기기
        log.info("새로운 카테고리 생성: {} (부모: {})",
                savedCategory.getCategoryName(),
                (parent != null ? parent.getCategoryName() : "없음"));

        // Entity -> Response DTO 변환
        return categoryCreateMapper.toResponse(savedCategory);
    }

    @Transactional
    public List<CategoryReadResponse> getCategoryList() {

        // 남성 , 여성만 조회
        List<CategoryEntity> rootCategories = categoryRepository.findAllByParentIsNull();

        // 트리 구조로 변환
        return rootCategories.stream().map(categoryReadMapper::toResponse).collect(Collectors.toList());
    }
}
