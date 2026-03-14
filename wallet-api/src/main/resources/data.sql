-- This ensures the UUID function works in Postgres
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Clean up
-- TRUNCATE TABLE transactions, wallets, users CASCADE;

-- Insert Users (Standardizing column names to match your @Column annotations)
INSERT INTO users (id, user_name, email, password_hash, created_at) VALUES
                                                                        (gen_random_uuid(), 'Tristen', 'tristen@huntermedia.com', 'hash123', CURRENT_TIMESTAMP),
                                                                        (gen_random_uuid(), 'Sarah J', 'sarah@example.com', 'hash456', CURRENT_TIMESTAMP),
                                                                        (gen_random_uuid(), 'Mike Ross', 'mike@pearsonhardman.com', 'hash789', CURRENT_TIMESTAMP);

-- Insert Wallets
INSERT INTO wallets (id, user_id, balance, currency, created_at)
SELECT gen_random_uuid(), id, 5000.00, 'ZAR', CURRENT_TIMESTAMP FROM users WHERE user_name = 'Tristen';

INSERT INTO wallets (id, user_id, balance, currency, created_at)
SELECT gen_random_uuid(), id, 12000.00, 'ZAR', CURRENT_TIMESTAMP FROM users WHERE user_name = 'Sarah J';

INSERT INTO wallets (id, user_id, balance, currency, created_at)
SELECT gen_random_uuid(), id, 0.00, 'ZAR', CURRENT_TIMESTAMP FROM users WHERE user_name = 'Mike Ross';

-- Insert Transactions (Using IDs from the wallets we just created)
INSERT INTO transactions (id, sender_wallet_id, receiver_wallet_id, amount, status, timestamp)
VALUES (
           gen_random_uuid(),
           (SELECT w.id FROM wallets w JOIN users u ON w.user_id = u.id WHERE u.user_name = 'Tristen' LIMIT 1),
           (SELECT w.id FROM wallets w JOIN users u ON w.user_id = u.id WHERE u.user_name = 'Sarah J' LIMIT 1),
           100.00, 'PENDING', CURRENT_TIMESTAMP
       );