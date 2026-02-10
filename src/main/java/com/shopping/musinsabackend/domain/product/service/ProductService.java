package com.shopping.musinsabackend.domain.product.service;

import com.shopping.musinsabackend.domain.product.dto.request.ProductCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.ProductCreateResponse;
import com.shopping.musinsabackend.domain.product.dto.response.ProductReadResponse;
import com.shopping.musinsabackend.domain.product.entity.BrandEntity;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import com.shopping.musinsabackend.domain.product.exception.ProductErrorCode;
import com.shopping.musinsabackend.domain.product.mapper.ProductCreateMapper;
import com.shopping.musinsabackend.domain.product.mapper.ProductReadMapper;
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

    // 상품 등록
    public ProductCreateResponse createProduct(ProductCreateRequest request, MultipartFile image) {

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

        // 이미지 S3 업로드 -> URL
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Service.uploadFile(PathName.PRODUCT, image);
        }

        // DTO -> Entity 변환
        ProductEntity product = productCreateMapper.toEntity(request, brand, category, imageUrl);

        // DB 저장
        ProductEntity savedProduct = productRepository.save(product);

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
                .map(productReadMapper::toResponse).collect(Collectors.toList());
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
    @Transactional
    public void deleteProduct(Long productId) {

        // 상품 조회
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 이미지가 있으면 S3에서 삭제
        if (product.getImage() != null) {
            try {
                String imageUrl = product.getImage();
                String splitStr = ".com/"; // product/랜덤UUID만 추출

                // .com 뒤에 있는 문자열 추출
                String keyName = imageUrl.substring(imageUrl.lastIndexOf(splitStr) + splitStr.length());

                s3Service.deleteFile(keyName);
            }
            catch (Exception e) {
                log.error("S3 이미지 삭제 실패: {}", e.getMessage());
            }
        }

        // 상품 삭제
        productRepository.delete(product);

        log.info("상품 및 이미지 삭제 완료: {}", productId);
    }
}
