import { Plus, CreditCard, MoreHorizontal, Clock, ArrowUpRight } from "lucide-react";import { useEffect, useState } from "react"
import type WalletResponseDTO from "../types/WalletResponseDTO"
import api from "../api/axios";
import { useAuth } from "../context/AuthContext";

const Wallets = () => {
  const [walletResponseDTO, setWalletResponseDTO] = useState<WalletResponseDTO[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false); // Modal Toggle
  const [selectedCurrency, setSelectedCurrency] = useState("USD");
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


  const handleCreateWallet = async (e: React.SubmitEvent) => {
    e.preventDefault();
    try {
      const response = await api.post('/api/wallets', {
        userId: user?.id,
        currency: selectedCurrency
      });
      
      // Optimistically update the UI or refetch
      setWalletResponseDTO([...walletResponseDTO, response.data]);
      setIsModalOpen(false);
    } catch (error) {
      console.error("Failed to create wallet:", error);
    }
  };

  return (
    <div className="space-y-8">
  {/* Header Section */}
  <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
    <div>
      <h1 className="text-2xl font-bold tracking-tight text-slate-900">
        Your Wallets
      </h1>
      <p className="text-sm font-medium text-slate-500">
        You have <span className="text-blue-600">{walletResponseDTO.length}</span> active wallets
      </p>
    </div>

    <button 
      onClick={() => setIsModalOpen(true)} // Open modal here
      className="flex items-center justify-center gap-2 rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white transition-all hover:bg-blue-700 hover:shadow-lg hover:shadow-blue-200 active:scale-95"
    >
      <Plus size={18} />
      New Wallet
    </button>
  </div>

  {/* Wallets Grid */}
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
              }).format(Number(wallet.balance))}
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

  {/* New Wallet Modal */}
  {isModalOpen && (
      <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 backdrop-blur-sm">
        <div className="w-full max-w-md rounded-2xl bg-white p-8 shadow-2xl animate-in fade-in zoom-in duration-200">
          <div className="mb-6">
            <h2 className="text-xl font-bold text-slate-900">Create New Wallet</h2>
            <p className="text-sm text-slate-500">Select a currency to get started.</p>
          </div>

          <form onSubmit={handleCreateWallet} className="space-y-6">
            <div>
              <label className="block text-sm font-semibold text-slate-700 mb-2">
                Currency
              </label>
              <select 
                value={selectedCurrency}
                onChange={(e) => setSelectedCurrency(e.target.value)}
                className="w-full rounded-lg border border-slate-200 p-2.5 text-slate-900 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 outline-none transition-all"
              >
                <option value="USD">USD - US Dollar</option>
                <option value="EUR">EUR - Euro</option>
                <option value="GBP">GBP - British Pound</option>
                <option value="NGN">NGN - Nigerian Naira</option>
                <option value="ZAR">ZAR - South African Rand</option>
              </select>
            </div>

            <div className="flex gap-3">
              <button
                type="button"
                onClick={() => setIsModalOpen(false)}
                className="flex-1 rounded-lg border border-slate-200 px-4 py-2.5 text-sm font-semibold text-slate-600 hover:bg-slate-50"
              >
                Cancel
              </button>
              <button
                type="submit"
                className="flex-1 rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white hover:bg-blue-700"
              >
                Create Wallet
              </button>
            </div>
          </form>
        </div>
      </div>
    )}
</div>
  )
}

export default Wallets