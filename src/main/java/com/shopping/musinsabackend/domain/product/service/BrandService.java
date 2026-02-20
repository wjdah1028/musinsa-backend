package com.shopping.musinsabackend.domain.product.service;

import com.shopping.musinsabackend.domain.product.dto.request.BrandCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.BrandCreateResponse;
import com.shopping.musinsabackend.domain.product.entity.BrandEntity;
import com.shopping.musinsabackend.domain.product.exception.ProductErrorCode;
import com.shopping.musinsabackend.domain.product.mapper.BrandCreateMapper;
import com.shopping.musinsabackend.domain.product.repository.BrandRepository;
import com.shopping.musinsabackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandCreateMapper brandCreateMapper;

    // 새로운 브랜드 생성
    public BrandCreateResponse createBrand(BrandCreateRequest request) {

        // 중복 검사
        if (brandRepository.existsByBrandName(request.getBrandName())) {
            throw new CustomException(ProductErrorCode.BRAND_ALREADY_EXIST);
        }

        // DTO -> Entity
        BrandEntity brand = brandCreateMapper.toEntity(request);

        // DB에 저장
        BrandEntity savedBrand = brandRepository.save(brand);

        // 로그 남기기
        log.info("새로운 브랜드 생성: {}", savedBrand);

        // Entity -> Response DTO 변환
        return brandCreateMapper.toResponse(savedBrand);
    }
}
