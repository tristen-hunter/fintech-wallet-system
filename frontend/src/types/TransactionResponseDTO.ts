type UUID = string;


export default interface TransactionResponseDTO {
    readonly id: UUID;
    readonly senderWalletId: UUID;
    readonly receiverWalletId: UUID;
    amount: string;
    status: string;
    readonly timestamp: string;
};