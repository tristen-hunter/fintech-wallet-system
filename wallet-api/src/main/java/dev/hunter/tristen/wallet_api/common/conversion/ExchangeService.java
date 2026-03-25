package dev.hunter.tristen.wallet_api.common.conversion;

import dev.hunter.tristen.wallet_api.domain.wallet.BulkRateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

@Service
public class ExchangeService {

    private final RestClient restClient;

    public ExchangeService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.frankfurter.dev/v2")
                .build();
    }

    // For converting a single value to another (used during transactions)
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


    // For calculating networth or the barchart
    public Map<String, Double> getRatesFor(String base, Set<String> symbols) {
        if (symbols.isEmpty()) return Map.of();

        String symbolList = String.join(",", symbols);

        // Call /v2/rates with 'quotes' parameter
        BulkRateResponse[] response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rates")
                        .queryParam("base", base)
                        .queryParam("quotes", symbolList)
                        .build())
                .retrieve()
                .body(BulkRateResponse[].class);

        if (response == null) return Map.of();

        // Convert the Array/List into the Map the Service expects
        return java.util.Arrays.stream(response)
                .collect(java.util.stream.Collectors.toMap(
                        BulkRateResponse::quote,
                        BulkRateResponse::rate
                ));
    }
}