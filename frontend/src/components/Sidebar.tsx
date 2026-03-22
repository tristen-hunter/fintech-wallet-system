import { LayoutDashboard, Wallet, ArrowLeftRight, FileBarChart, Settings } from "lucide-react";

const Sidebar = () => {
  // Logic-ready: You'll eventually use 'useLocation' from react-router-dom to set this
  const activePage = "Dashboard"; 

  const navItems = [
    { name: "Dashboard", icon: <LayoutDashboard size={20} />, path: "/" },
    { name: "Wallets", icon: <Wallet size={20} />, path: "/wallets" },
    { name: "Transactions", icon: <ArrowLeftRight size={20} />, path: "/transactions" },
    { name: "Reports", icon: <FileBarChart size={20} />, path: "/reports" },
  ];

  return (
    <aside className="fixed inset-y-0 left-0 z-50 flex w-64 flex-col border-r border-slate-200 bg-white">
      {/* Top Section: Logo/Brand */}
      <div className="flex h-16 items-center border-b border-slate-200 px-6">
        <div className="flex items-center gap-2">
          <div className="h-8 w-8 rounded-lg bg-blue-600 flex items-center justify-center text-white font-bold">
            B
          </div>
          <span className="text-xl font-bold tracking-tight text-slate-900">BlueBank</span>
        </div>
      </div>

      {/* Middle Section: Navigation */}
      <nav className="flex-1 space-y-1 px-3 py-6">
        {navItems.map((item) => {
          const isActive = activePage === item.name;
          return (
            <a
              key={item.name}
              href={item.path}
              className={`flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors ${
                isActive
                  ? "bg-blue-50 text-blue-600"
                  : "text-slate-600 hover:bg-slate-50 hover:text-slate-900"
              }`}
            >
              <span className={isActive ? "text-blue-600" : "text-slate-400"}>
                {item.icon}
              </span>
              {item.name}
            </a>
          );
        })}
      </nav>

      {/* Bottom Section: Support/Settings */}
      <div className="border-t border-slate-200 p-4">
        <button className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-50">
          <Settings size={20} className="text-slate-400" />
          Settings
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;