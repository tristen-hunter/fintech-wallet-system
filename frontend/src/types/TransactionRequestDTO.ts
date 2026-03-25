type UUID = string;

export default interface TransactionUserResponseDTO {
    readonly senderWalletId: UUID;
    readonly receiverWalletId: UUID;
    readonly amount: string;
};