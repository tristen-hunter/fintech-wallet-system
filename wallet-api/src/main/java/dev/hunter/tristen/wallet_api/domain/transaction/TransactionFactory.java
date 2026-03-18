package dev.hunter.tristen.wallet_api.domain.transaction;

import dev.hunter.tristen.wallet_api.domain.wallet.Wallet;
import dev.hunter.tristen.wallet_api.dto.TransactionRequestDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TransactionFactory {

    public Transaction createCompletedTransaction(Wallet sender, Wallet receiver, @NonNull BigDecimal amount){
        // 1. Validate transfer amount > 0
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        // 2. Create the object & set the status
        Transaction transaction = new Transaction(sender, receiver, amount);
        transaction.setStatus(TransactionStatus.COMPLETED);

        // 3. Create the ledger entries
        transaction.addLedgerEntry(new LedgerEntry(transaction, receiver, LedgerType.DEBIT, amount));
        transaction.addLedgerEntry(new LedgerEntry(transaction, sender, LedgerType.CREDIT, amount.negate()));

        return transaction;

    }
}
