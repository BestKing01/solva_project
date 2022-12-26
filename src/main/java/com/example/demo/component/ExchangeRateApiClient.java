package com.example.demo.component;

import com.example.demo.model.ExchangeRateResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeRateApiClient {
    private final RestTemplate restTemplate;

    public ExchangeRateApiClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ExchangeRateResponse getExchangeRates(String base, String target, String period) {
        // Make a GET request to the exchange rate API
        ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(
                "https://api.exchangeratesapi.io/{period}?base={base}&symbols={target}",
                ExchangeRateResponse.class,
                period, base, target);
        return response.getBody();
    }
}