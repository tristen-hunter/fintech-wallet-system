const TransactionHistory = () => {
  return (
    <div className="h-full w-full rounded-2xl border border-slate-200 bg-white p-6 shadow-sm overflow-hidden flex flex-col">
       <h3 className="font-bold mb-4">Transactions</h3>
       <div className="flex-1 overflow-y-auto"> 
          {/* Your list goes here */}
       </div>
    </div>
  )
}

export default TransactionHistory