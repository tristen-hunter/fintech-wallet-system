import { CreditCard, Clock, ArrowUpRight, MoreHorizontal } from "lucide-react";
import { useEffect, useState } from "react"
import type WalletResponseDTO from "../types/WalletResponseDTO"
import api from "../api/axios";
import { useAuth } from "../context/AuthContext";

const Wallets = () => {
  const [walletResponseDTO, setWalletResponseDTO] = useState<WalletResponseDTO[]>([]);
  const { user } = useAuth();

  useEffect(() => {
    const getUserWallets = async () => {
      try {
        const response = await api.get(`/api/wallets/user/${user?.id}`)

        setWalletResponseDTO(response.data);
      } catch (error) {
        console.log("Could not fetch Wallets :", error)
        throw error;
      };
    }

    getUserWallets();
  }, [])

  return (
    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
      {walletResponseDTO.map((wallet) => (
        <div 
          key={wallet.id} 
          className="group relative overflow-hidden rounded-2xl border border-slate-200 bg-white p-6 transition-all hover:shadow-md hover:border-blue-200"
        >
          {/* Card Header: Currency Badge & Options */}
          <div className="flex items-center justify-between mb-4">
            <div className="flex items-center gap-2">
              <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-50 text-blue-600">
                <CreditCard size={20} />
              </div>
              <span className="inline-flex items-center rounded-md bg-slate-100 px-2 py-1 text-xs font-bold text-slate-600 uppercase tracking-wider">
                {wallet.currency}
              </span>
            </div>
            <button className="text-slate-400 hover:text-slate-600">
              <MoreHorizontal size={20} />
            </button>
          </div>

          {/* Balance Section */}
          <div className="space-y-1">
            <p className="text-sm font-medium text-slate-500 uppercase tracking-tight">Total Balance</p>
            <div className="flex items-baseline gap-1">
              <h2 className="text-3xl font-bold text-slate-900">
                {new Intl.NumberFormat('en-US', { 
                  minimumFractionDigits: 2 
                }).format(Number(wallet.balance))} {/* <--- Wrap in Number() */}
              </h2>
              <span className="text-sm font-semibold text-slate-500 uppercase">{wallet.currency}</span>
            </div>
          </div>

          {/* Card Footer: Metadata */}
          <div className="mt-8 flex items-center justify-between border-t border-slate-50 pt-4">
            <div className="flex items-center gap-1.5 text-xs text-slate-400">
              <Clock size={14} />
              <span>Created {new Date(wallet.createdAt).toLocaleDateString()}</span>
            </div>
            
            <button className="flex items-center gap-1 text-sm font-semibold text-blue-600 hover:text-blue-700">
              Details
              <ArrowUpRight size={16} />
            </button>
          </div>
          
          {/* Subtle blue accent bar on hover */}
          <div className="absolute top-0 left-0 h-1 w-full bg-blue-600 opacity-0 transition-opacity group-hover:opacity-100" />
        </div>
      ))}
    </div>
  )
}

export default Wallets