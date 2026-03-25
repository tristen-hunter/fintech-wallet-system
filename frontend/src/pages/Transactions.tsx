import { ArrowUpRight, ArrowDownLeft, Clock, MoreVertical, ArrowLeftRight, Send } from "lucide-react";
import { useEffect, useState } from "react"
import type TransactionResponseDTO from "../types/TransactionResponseDTO";
import { useAuth } from "../context/AuthContext";
import api from "../api/axios";

const Transactions = () => {
  const [transactionResponseDTO, setTransactionResponseDTO] = useState<TransactionResponseDTO[]>([]);
  const { user } = useAuth();

  // New State for Modal and Form
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [formData, setFormData] = useState({
    senderWalletId: '',
    receiverWalletId: '',
    amount: ''
  });

  // Handler for form submission
  const handleCreateTransaction = async (e: React.SubmitEvent) => {
    e.preventDefault();
    try {
      const response = await api.post('/api/transactions', formData);
      setTransactionResponseDTO([response.data, ...transactionResponseDTO]);
      setIsModalOpen(false);
      setFormData({ senderWalletId: '', receiverWalletId: '', amount: '' });
    } catch (error) {
      console.error("Transaction failed:", error);
      alert("Failed to process transaction. Check Wallet IDs and balance.");
    }
  };


  useEffect(() => {
    const getUserTransactions = async () => {
      try {
        const response = await api.get(`/api/transactions/user/${user?.id}`)

        setTransactionResponseDTO(response.data);
      } catch (error) {
        console.log("Could not fetch Wallets :", error)
        throw error;
      }
    }

    getUserTransactions();
  }, [])

  const formatCurrency = (amount: string) => {
    return new Intl.NumberFormat('en-US', {
      minimumFractionDigits: 2,
    }).format(Number(amount));
  };

  const getStatusColor = (status: string) => {
    switch (status.toUpperCase()) {
      case 'COMPLETED': return 'bg-emerald-50 text-emerald-700 border-emerald-100';
      case 'PENDING': return 'bg-amber-50 text-amber-700 border-amber-100';
      case 'FAILED': return 'bg-rose-50 text-rose-700 border-rose-100';
      default: return 'bg-slate-50 text-slate-600 border-slate-100';
    }
  };

  return (
    <div className="space-y-6">
      {/* Page Header */}
      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h2 className="text-2xl font-bold text-slate-900">Recent Transactions</h2>
          <p className="text-sm text-slate-500">
            You have performed <span className="font-semibold text-blue-600">{transactionResponseDTO.length}</span> operations
          </p>
        </div>

        <button 
          onClick={() => setIsModalOpen(true)}
          className="flex items-center justify-center gap-2 rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white transition-all hover:bg-blue-700 hover:shadow-lg hover:shadow-blue-200 active:scale-95"
        >
          <Send size={18} />
          New Transaction
        </button>
      </div>

      {/* Transaction List Container */}
      <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
        <div className="divide-y divide-slate-100">
          {transactionResponseDTO.length > 0 ? (
            transactionResponseDTO.map((tx) => (
              <div 
                key={tx.id} 
                className="group flex items-center justify-between p-4 transition-colors hover:bg-slate-50/80"
              >
                {/* Left: Direction Icon & Info */}
                <div className="flex items-center gap-4">
                  <div className={`flex h-11 w-11 shrink-0 items-center justify-center rounded-xl border ${
                    tx.direction === 'RECEIVED' 
                      ? 'border-emerald-100 bg-emerald-50 text-emerald-600' 
                      : 'border-blue-100 bg-blue-50 text-blue-600'
                  }`}>
                    {tx.direction === 'RECEIVED' ? <ArrowDownLeft size={22} /> : <ArrowUpRight size={22} />}
                  </div>
                  
                  <div className="flex flex-col">
                    <span className="text-sm font-semibold text-slate-900 line-clamp-1">
                      {tx.direction === 'RECEIVED' ? 'From' : 'To'} {tx.counterpartyEmail}
                    </span>
                    <div className="flex items-center gap-2 text-xs text-slate-500">
                      <Clock size={12} />
                      {new Date(tx.timestamp).toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' })}
                    </div>
                  </div>
                </div>

                {/* Right: Amount & Status */}
                <div className="flex items-center gap-6">
                  <div className="text-right">
                    <p className={`text-sm font-bold ${
                      tx.direction === 'RECEIVED' ? 'text-emerald-600' : 'text-slate-900'
                    }`}>
                      {tx.direction === 'RECEIVED' ? '+' : '-'}{formatCurrency(tx.amount)}
                    </p>
                    <span className={`inline-flex items-center rounded-full border px-2 py-0.5 text-[10px] font-bold uppercase tracking-wider ${getStatusColor(tx.status)}`}>
                      {tx.status}
                    </span>
                  </div>
                  
                  <button className="rounded-lg p-2 text-slate-400 opacity-0 transition-opacity hover:bg-slate-100 group-hover:opacity-100">
                    <MoreVertical size={18} />
                  </button>
                </div>
              </div>
            ))
          ) : (
            <div className="flex flex-col items-center justify-center py-20 text-slate-500">
              <div className="mb-4 rounded-full bg-slate-100 p-4">
                <ArrowLeftRight size={32} className="text-slate-400" />
              </div>
              <p>No transactions found</p>
            </div>
          )}
        </div>
      </div>
      {isModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/40 backdrop-blur-sm p-4">
          <div className="w-full max-w-md rounded-2xl bg-white p-6 shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="mb-6 flex items-center gap-3">
              <div className="rounded-full bg-blue-50 p-2 text-blue-600">
                <Send size={20} />
              </div>
              <div>
                <h3 className="text-lg font-bold text-slate-900">Send Money</h3>
                <p className="text-xs text-slate-500">Transfer funds between wallets instantly.</p>
              </div>
            </div>

            <form onSubmit={handleCreateTransaction} className="space-y-4">
              {/* Sender Wallet ID */}
              <div>
                <label className="mb-1.5 block text-xs font-bold uppercase tracking-wider text-slate-500">
                  Source Wallet ID
                </label>
                <input
                  required
                  type="text"
                  placeholder="Your Wallet UUID"
                  className="w-full rounded-xl border border-slate-200 bg-slate-50 p-3 text-sm outline-none transition-all focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
                  value={formData.senderWalletId}
                  onChange={(e) => setFormData({...formData, senderWalletId: e.target.value})}
                />
              </div>

              {/* Receiver Wallet ID */}
              <div>
                <label className="mb-1.5 block text-xs font-bold uppercase tracking-wider text-slate-500">
                  Recipient Wallet ID
                </label>
                <input
                  required
                  type="text"
                  placeholder="Enter destination UUID"
                  className="w-full rounded-xl border border-slate-200 bg-slate-50 p-3 text-sm outline-none transition-all focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
                  value={formData.receiverWalletId}
                  onChange={(e) => setFormData({...formData, receiverWalletId: e.target.value})}
                />
              </div>

              {/* Amount */}
              <div>
                <label className="mb-1.5 block text-xs font-bold uppercase tracking-wider text-slate-500">
                  Amount
                </label>
                <div className="relative">
                  <input
                    required
                    type="number"
                    step="0.01"
                    placeholder="0.00"
                    className="w-full rounded-xl border border-slate-200 bg-slate-50 p-3 pl-8 text-sm font-semibold outline-none transition-all focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
                    value={formData.amount}
                    onChange={(e) => setFormData({...formData, amount: e.target.value})}
                  />
                  <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 font-bold">$</span>
                </div>
              </div>

              <div className="flex gap-3 pt-2">
                <button
                  type="button"
                  onClick={() => setIsModalOpen(false)}
                  className="flex-1 rounded-xl border border-slate-200 py-3 text-sm font-bold text-slate-600 transition-colors hover:bg-slate-50"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="flex-1 rounded-xl bg-blue-600 py-3 text-sm font-bold text-white transition-all hover:bg-blue-700 hover:shadow-lg hover:shadow-blue-200 active:scale-95"
                >
                  Confirm Send
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
export default Transactions