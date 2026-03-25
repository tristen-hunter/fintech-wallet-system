package dev.hunter.tristen.wallet_api.domain.wallet;

// Updated to match Frankfurter v2: [{ "base": "USD", "quote": "EUR", "rate": 0.85 }, ...]
public record BulkRateResponse(
        String date,
        String base,
        String quote,
        double rate
) {}