package com.shopping.musinsabackend.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "브랜드 생성 응답 DTO")
public class BrandCreateResponse {

    @Schema(description = "브랜드 고유번호", example = "1")
    private Long brandId;

    @Schema(description = "브랜드 이름", example = "수아레")
    private String brandName;
}
