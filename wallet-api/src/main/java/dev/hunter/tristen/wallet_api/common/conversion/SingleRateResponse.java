package dev.hunter.tristen.wallet_api.common.conversion;

public record SingleRateResponse(
        String date,
        String base,
        String quote,
        double rate
) {}
