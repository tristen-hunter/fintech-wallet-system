import { TrendingUp, DollarSign, Target, Zap } from "lucide-react";
import { useEffect, useState } from "react";
import api from "../api/axios";
import { useAuth } from "../context/AuthContext";

const Networth = () => {
    const totalNetworth = 12;
  const percentageChange = 12.5;
  const mainCurrency = "USD";

  const [totalNetWorth, setTotalNetWorth] = useState("");
  const { user } = useAuth();

  useEffect(() => {
    const getUserNetworth = async () => {
        try {
            const response = await api.get(`/api/wallets/networth/${user?.id}`);
            setTotalNetWorth(response.data);

        } catch (error) {
            console.log(error)
            throw error;
        }

    }

    getUserNetworth();
  }, [])

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-US', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(amount);
  };

  // Splitting integer and decimal for fine-tuned styling
  const number = Number(totalNetWorth);
  const [integer, decimal] = formatCurrency(number).split('.');

  return (
    <div className="relative h-full w-full overflow-hidden rounded-3xl border border-slate-100 bg-white p-7 shadow-sm transition-all hover:shadow-md">
      
      {/* 1. Lighter Background Decoration */}
      <div className="absolute inset-0 -z-10 opacity-30">
        <div className="absolute -top-24 -right-24 h-64 w-64 rounded-full bg-blue-50/50 blur-3xl" />
        <svg width="100%" height="100%" xmlns="http://www.w3.org/2000/svg">
          <defs>
            <pattern id="dotPattern" x="0" y="0" width="20" height="20" patternUnits="userSpaceOnUse">
              <circle cx="2" cy="2" r="0.5" className="fill-slate-200" />
            </pattern>
          </defs>
          <rect width="100%" height="100%" fill="url(#dotPattern)" />
        </svg>
      </div>

      <div className="flex h-full flex-col justify-between relative z-10">
        
        {/* Header Section */}
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-blue-50 text-blue-600">
              <Zap size={20} />
            </div>
            <div>
              <h3 className="text-[10px] font-bold uppercase tracking-[0.15em] text-slate-400">
                Net Worth
              </h3>
            </div>
          </div>
          
          <div className="flex items-center gap-1 rounded-full bg-slate-50 px-3 py-1 text-[10px] font-bold text-slate-500">
            <DollarSign size={12} />
            {mainCurrency}
          </div>
        </div>

        {/* 2. Smaller, Refined Typography */}
        <div className="my-4">
          <div className="flex items-baseline text-slate-900">
            <span className="text-2xl font-medium mr-1 opacity-50">$</span>
            <h1 className="text-4xl font-bold tracking-tight">
              {integer}
              <span className="text-2xl font-medium text-slate-400">.{decimal}</span>
            </h1>
          </div>
          <p className="mt-1 text-xs text-slate-400 font-medium">Available balance across 4 wallets</p>
        </div>

        {/* Footer Section */}
        <div className="flex items-center justify-between pt-4">
          <div className="flex items-center gap-2">
            <div className="flex items-center gap-1 rounded-lg bg-emerald-50 px-2 py-1 text-emerald-600">
              <TrendingUp size={14} />
              <span className="text-xs font-bold">+{percentageChange}%</span>
            </div>
            <span className="text-[10px] font-medium text-slate-400">This month</span>
          </div>

          <button className="group flex h-9 w-9 items-center justify-center rounded-full border border-slate-100 bg-white text-slate-400 transition-all hover:border-blue-200 hover:text-blue-600 hover:bg-blue-50/30">
            <Target size={18} />
          </button>
        </div>
      </div>
    </div>
  );
};

export default Networth;