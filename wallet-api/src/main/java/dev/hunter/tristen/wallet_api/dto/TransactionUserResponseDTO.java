package dev.hunter.tristen.wallet_api.dto;

import dev.hunter.tristen.wallet_api.domain.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionUserResponseDTO(
        UUID id,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime timestamp,
        String direction,
        String counterpartyEmail,
        UUID counterpartyWalletId
) {}