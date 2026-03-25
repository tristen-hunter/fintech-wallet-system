package dev.hunter.tristen.wallet_api.common.conversion;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeService {

    private final RestClient restClient;

    public ExchangeService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.frankfurter.dev/v2")
                .build();
    }

    public BigDecimal convert(String from, String to, BigDecimal amount) {
        // Skip API call if currencies are the same
        if (from.equals(to)) {
            return amount;
        }

        SingleRateResponse response = restClient.get()
                .uri("/rate/{from}/{to}", from, to)
                .retrieve()
                .body(SingleRateResponse.class);

        if (response == null) {
            throw new RuntimeException("Exchange rate unavailable");
        }

        // Use BigDecimal for precision math
        return amount.multiply(BigDecimal.valueOf(response.rate()))
                .setScale(2, RoundingMode.HALF_UP);
    }
}