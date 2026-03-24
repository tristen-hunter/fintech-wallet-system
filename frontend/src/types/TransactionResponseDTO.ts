type UUID = string;

export default interface TransactionUserResponseDTO {
    readonly id: UUID;
    readonly amount: string;
    readonly status: string;
    readonly timestamp: string;
    readonly direction: 'SENT' | 'RECEIVED';
    readonly counterpartyEmail: string;
    readonly counterpartyWalletId: UUID;
};