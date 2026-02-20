package com.shopping.musinsabackend.domain.mypage.service;

import com.shopping.musinsabackend.domain.order.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getRecommendation(List<OrderEntity> orders) {
        if (orders == null || orders.isEmpty()) {
            return "아직 구매 내역이 없으시네요 ! 첫 주문을 하시면 AI가 코디를 추천해드려요 !";
        }

        // 주문 목록에서 상품명 추출
        String productNames = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(item -> item.getProduct().getProductName())
                .distinct()
                .limit(5)
                .collect(Collectors.joining(","));

        // OpenAI 요청 구성
        if (productNames.isEmpty()) {
            return "최근 구매하신 상품 기반으로 트렌디한 아이템을 둘러보세요!";
        }

        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", List.of(
                Map.of("role", "system", "content", "너는 무신사 쇼핑몰의 트렌디하고 친절한 패션 MD야. 고객이 최근에 구매한 상품 목록을 보고, 그 옷들과 잘 어올릴 만한 다른 패션 아이템이나 코디 팁을 2~3줄 이내로 자연스럽게 추천해줘. 출력은 꼭 한국어로 해줘."),
                Map.of("role", "user", "content", "구매 목록: [" + productNames + "]")
        ));

        try {
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");

            return (String) message.get("content");

        }
        catch (Exception e) {
            log.error("AI 추천 생성 실패: ", e);
            return "현재 AI MD가 코디를 고민 중입니다. 잠시 후 다시 확인해 주세요! ☕";
        }
    }
}
