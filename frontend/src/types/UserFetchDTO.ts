type UUID = string;

export default interface UserfetchDTO {
    readonly id: UUID;
    userName: string;
    email: string;
    readonly createdAt: string;
};