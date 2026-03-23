type UUID = string;


export default interface WalletResponseDTO {
    readonly id: UUID;
    readonly userId: UUID;
    balance: string;
    currency: string;
    readonly createdAt: string;
};