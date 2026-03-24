import { ArrowUpRight, ArrowDownLeft, Clock, MoreVertical, ArrowLeftRight } from "lucide-react";
import { useEffect, useState } from "react"
import type TransactionResponseDTO from "../types/TransactionResponseDTO";
import { useAuth } from "../context/AuthContext";
import api from "../api/axios";

const Transactions = () => {
  const [transactionResponseDTO, setTransactionResponseDTO] = useState<TransactionResponseDTO[]>([]);
  const { user } = useAuth();

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
      <div className="flex items-center justify-between">
        <h2 className="text-2xl font-bold text-slate-900">Recent Transactions</h2>
        <span className="text-sm text-slate-500">{transactionResponseDTO.length} total operations</span>
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
                    <span className="text-sm font-semibold text-slate-900">
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
    </div>
  );
}

export default Transactions