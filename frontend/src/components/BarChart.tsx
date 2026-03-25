const BarChart = () => {
  const data = [
    { week: "Week 1", income: 85, spending: 40 },
    { week: "Week 2", income: 65, spending: 55 },
    { week: "Week 3", income: 90, spending: 30 },
    { week: "Week 4", income: 70, spending: 60 },
  ];

  return (
    <div className="flex h-full w-full flex-col rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
      {/* Header & Legend */}
      <div className="mb-8 flex items-center justify-between">
        <div>
          <h3 className="text-lg font-bold text-slate-900">Cash Flow</h3>
          <p className="text-sm text-slate-500">Weekly income vs spending</p>
        </div>
        <div className="flex gap-4 text-xs font-semibold uppercase tracking-wider">
          <div className="flex items-center gap-1.5">
            <div className="h-3 w-3 rounded-sm bg-emerald-500" />
            <span className="text-slate-600">Income</span>
          </div>
          <div className="flex items-center gap-1.5">
            <div className="h-3 w-3 rounded-sm bg-orange-500" />
            <span className="text-slate-600">Spending</span>
          </div>
        </div>
      </div>

      {/* Chart Area */}
      <div className="relative flex flex-1 items-end justify-around border-b border-slate-100 pb-2">
        {data.map((item, index) => (
          <div key={index} className="flex flex-col items-center gap-2">
            
            {/* The Bar Group */}
            <div className="flex items-end gap-1.5 h-48 sm:h-64">
              
              {/* Income Bar + Tooltip Container */}
              <div className="group relative flex flex-col items-center h-full justify-end">
                {/* Tooltip */}
                <div className="absolute -top-10 left-1/2 -translate-x-1/2 scale-0 rounded bg-slate-900 px-2 py-1 text-[10px] font-bold text-white transition-all group-hover:scale-100 z-20">
                  ${item.income}k
                  {/* Arrow */}
                  <div className="absolute -bottom-1 left-1/2 -translate-x-1/2 border-x-4 border-t-4 border-x-transparent border-t-slate-900" />
                </div>
                
                <div 
                  style={{ height: `${item.income}%` }}
                  className="w-4 rounded-t-sm bg-emerald-500 transition-all duration-300 group-hover:bg-emerald-600 group-hover:shadow-lg group-hover:shadow-emerald-100"
                />
              </div>

              {/* Spending Bar + Tooltip Container */}
              <div className="group relative flex flex-col items-center h-full justify-end">
                {/* Tooltip */}
                <div className="absolute -top-10 left-1/2 -translate-x-1/2 scale-0 rounded bg-slate-900 px-2 py-1 text-[10px] font-bold text-white transition-all group-hover:scale-100 z-20">
                  ${item.spending}k
                  {/* Arrow */}
                  <div className="absolute -bottom-1 left-1/2 -translate-x-1/2 border-x-4 border-t-4 border-x-transparent border-t-slate-900" />
                </div>

                <div 
                  style={{ height: `${item.spending}%` }}
                  className="w-4 rounded-t-sm bg-orange-500 transition-all duration-300 group-hover:bg-orange-600 group-hover:shadow-lg group-hover:shadow-orange-100"
                />
              </div>
            </div>
            
            <span className="text-xs font-medium text-slate-500">{item.week}</span>
          </div>
        ))}

        {/* Grid Lines (Background) */}
        <div className="absolute inset-0 -z-10 flex flex-col justify-between pt-2 pb-8">
          {[...Array(5)].map((_, i) => (
            <div key={i} className="w-full border-t border-slate-50" />
          ))}
        </div>
      </div>
    </div>
  );
};

export default BarChart;