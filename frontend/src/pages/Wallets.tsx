import { useEffect, useState } from "react"
import type WalletResponseDTO from "../types/WalletResponseDTO"
import api from "../api/axios";
import { useAuth } from "../context/AuthContext";

const Wallets = () => {
  const [walletResponseDTO, setWalletResponseDTO] = useState<WalletResponseDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();

  useEffect(() => {
    const getUserWallets = async () => {
      try {
        const response = await api.get(`/api/wallets/user/${user?.id}`)

        setWalletResponseDTO(response.data);
        console.log(response.data)
      } catch (error) {
        console.log("Could not fetch Wallets :", error)
        throw error;
      } finally {
        setLoading(false);
      };
    }

    getUserWallets();
  }, [])

  return (
    <div>
      {walletResponseDTO.map((wallet) => (
        <div key={wallet.id}>{wallet.balance}</div>
      ))}
    </div>
  )
}

export default Wallets