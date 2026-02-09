package com.shopping.musinsabackend.domain.product.service;

import com.shopping.musinsabackend.domain.product.dto.request.CategoryCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.CategoryCreateResponse;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import com.shopping.musinsabackend.domain.product.exception.ProductErrorCode;
import com.shopping.musinsabackend.domain.product.mapper.CategoryCreateMapper;
import com.shopping.musinsabackend.domain.product.repository.CategoryRepository;
import com.shopping.musinsabackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCreateMapper categoryCreateMapper;

    // 새로운 카테고리 생성
    public CategoryCreateResponse create(CategoryCreateRequest request) {

        // 중복 검사
        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new CustomException(ProductErrorCode.CATEGORY_ALREADY_EXIST);
        }

        // DTO -> Entity
        CategoryEntity category = categoryCreateMapper.toEntity(request);

        // DB에 저장
        CategoryEntity savedCategory = categoryRepository.save(category);

        // 로그 남기기
        log.info("새로운 카테고리 생성: {}", savedCategory);

        // Entity -> Response DTO 변환
        return categoryCreateMapper.toResponse(savedCategory);
    }
}
