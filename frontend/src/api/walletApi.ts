// import { useAuth } from "../context/AuthContext";
// import type WalletResponseDTO from "../types/WalletResponseDTO";
// import api from "./axios"

// export const getUserWallets = (data: any) => {
//     return api.get<WalletResponseDTO>("/api/wallets", { params: data });
// }
// const { user } = useAuth();

// export const getWallets = async (): Promise<WalletResponseDTO[]> => {
//     const response = await api.get(`/api/wallets/user/${user?.id}`);
//     return response.data; 
// };