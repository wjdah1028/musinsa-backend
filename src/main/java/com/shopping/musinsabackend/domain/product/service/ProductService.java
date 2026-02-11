package com.shopping.musinsabackend.domain.product.service;

import com.shopping.musinsabackend.domain.product.dto.request.ProductCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.request.ProductUpdateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.ProductCreateResponse;
import com.shopping.musinsabackend.domain.product.dto.response.ProductReadResponse;
import com.shopping.musinsabackend.domain.product.dto.response.ProductUpdateResponse;
import com.shopping.musinsabackend.domain.product.entity.BrandEntity;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductImageEntity;
import com.shopping.musinsabackend.domain.product.exception.ProductErrorCode;
import com.shopping.musinsabackend.domain.product.mapper.ProductCreateMapper;
import com.shopping.musinsabackend.domain.product.mapper.ProductReadMapper;
import com.shopping.musinsabackend.domain.product.mapper.ProductUpdateMapper;
import com.shopping.musinsabackend.domain.product.repository.BrandRepository;
import com.shopping.musinsabackend.domain.product.repository.CategoryRepository;
import com.shopping.musinsabackend.domain.product.repository.ProductRepository;
import com.shopping.musinsabackend.global.exception.CustomException;
import com.shopping.musinsabackend.global.s3.entity.PathName;
import com.shopping.musinsabackend.global.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCreateMapper productCreateMapper;
    private final S3Service s3Service;
    private final ProductReadMapper productReadMapper;
    private final ProductUpdateMapper productUpdateMapper;

    // 상품 등록
    public ProductCreateResponse createProduct(ProductCreateRequest request, List<MultipartFile> images) {

        // 상품 중복 검사
        if (productRepository.existsByProductName(request.getProductName())) {
            throw new CustomException(ProductErrorCode.PRODUCT_ALREADY_EXIST);
        }

        // 브랜드 조회
        BrandEntity brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new CustomException(ProductErrorCode.BRAND_NOT_FOUND));

        // 카테고리 조회
        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomException(ProductErrorCode.CATEGORY_NOT_FOUND));

        // DTO -> Entity 변환
        ProductEntity product = productCreateMapper.toEntity(request, brand, category);

        // DB 저장
        ProductEntity savedProduct = productRepository.save(product);

        // 이미지 S3 업로드 -> URL
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                if (file.isEmpty()) continue;

                String imageUrl = s3Service.uploadFile(PathName.PRODUCT, file);

                // 이미지 엔티티 생성
                ProductImageEntity newImage = ProductImageEntity.builder()
                        .product(savedProduct)
                        .imageUrl(imageUrl)
                        .build();

                // Entity 리스트에 추가
                savedProduct.getImages().add(newImage);
            }
        }

        productRepository.flush();

        // 로그에 저장
        log.info("새로운 상품 등록 완료: {}", savedProduct.getProductId());

        // 응답 반환
        return productCreateMapper.toResponse(savedProduct);
    }

    // 상품 전체 조회
    public List<ProductReadResponse> allProductList() {

        // DB에서 조회
        List<ProductEntity> productList = productRepository.findAll();

        // 상품이 없으면 에러 메시지 출력
        if (productList.isEmpty()) {
            throw new CustomException(ProductErrorCode.PRODUCT_ALL_EXIST);
        }

        // Entity -> DTO 변환
        return productList.stream()
                .map(productReadMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 상품 상세 조회
    public ProductReadResponse productInfo(Long productId) {

        // 상품 조회
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // Entity -> DTO 변환
        return productReadMapper.toResponse(product);
    }

    // 상품 삭제
    public void deleteProduct(Long productId) {

        // 상품 조회
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 이미지가 있으면 S3에서 삭제
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            for (ProductImageEntity image : product.getImages()) {
                s3Service.deleteFileUrl(image.getImageUrl());
            }
        }

        // 상품 삭제 (이미지도 cascade + orphanRemoval 로 자동 삭제)
        productRepository.delete(product);

        log.info("상품 및 이미지 삭제 완료: {}", productId);
    }

    // 상품 수정
    public ProductUpdateResponse updateProduct(Long productId, ProductUpdateRequest request, List<MultipartFile> newImages) {

        // 상품 조회
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 브랜드 조회
        BrandEntity brand = null;
        if (request.getBrandId() != null) {
            brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new CustomException(ProductErrorCode.BRAND_NOT_FOUND));
        }

        // 카테고리 조회
        CategoryEntity category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CustomException(ProductErrorCode.CATEGORY_NOT_FOUND));
        }

        // 텍스트 정보 업데이트
        product.update(
                request.getProductName(),
                request.getProductContent(),
                request.getPrice(),
                request.getStock(),
                request.getGender(),
                brand,
                category
        );

        // 이미지 삭제
        if (request.getDeletedImageUrls() != null && !request.getDeletedImageUrls().isEmpty()) {

            product.getImages().removeIf(image ->
                    request.getDeletedImageUrls().contains(image.getImageUrl())
            );

            for (String imageUrl : request.getDeletedImageUrls()) {
                s3Service.deleteFileUrl(imageUrl);
            }
        }

        // 이미지 추가
        if (newImages != null && !newImages.isEmpty()) {
            for (MultipartFile file : newImages) {
                if (file.isEmpty()) continue;

                String newImageUrl = s3Service.uploadFile(PathName.PRODUCT, file);

                ProductImageEntity newImage = ProductImageEntity.builder()
                        .product(product)
                        .imageUrl(newImageUrl)
                        .build();

                product.getImages().add(newImage);
            }
        }

        // 반환
        return productUpdateMapper.toUpdateResponse(product);
    }
}
