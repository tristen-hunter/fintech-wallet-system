import { useLocation, Link } from "react-router-dom";
import { LayoutDashboard, Wallet, ArrowLeftRight, Settings, LogOut  } from "lucide-react";
import { useAuth } from "../context/AuthContext";

const Sidebar = () => {
  const location = useLocation();
  const { logoutUser } = useAuth();

  // Defines the items displayed un the nav bar
  const navItems = [
    { name: "Dashboard", icon: <LayoutDashboard size={20} />, path: "/" },
    { name: "Wallets", icon: <Wallet size={20} />, path: "/wallets" },
    { name: "Transactions", icon: <ArrowLeftRight size={20} />, path: "/transactions" },
    // { name: "Logout", icon: <LogOut size={20} />, action: logoutUser }
  ];

  return (
    <aside className="fixed inset-y-0 left-0 z-50 flex w-64 flex-col border-r border-slate-200 bg-white shadow-sm">
      {/* 1. Header Section: Logo/Brand */}
      <div className="flex h-16 shrink-0 items-center border-b border-slate-200 px-6">
        <div className="flex items-center gap-2.5">
          <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-blue-600 font-bold text-white shadow-lg shadow-blue-100">
            B
          </div>
          <span className="text-xl font-bold tracking-tight text-slate-900">
            BlueBank
          </span>
        </div>
      </div>

      {/* 2. Middle Section: Navigation */}
      <nav className="flex-1 overflow-y-auto space-y-1 px-3 py-6">
        {navItems.map((item) => {
          const isActive = location.pathname === item.path;

          return (
            <Link
              key={item.name}
              to={item.path}
              className={`flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-all ${
                isActive
                  ? "bg-blue-50 text-blue-600 shadow-sm shadow-blue-100/50"
                  : "text-slate-600 hover:bg-slate-50 hover:text-slate-900"
              }`}
              onClick={item.name === "Logout" ? logoutUser : undefined}
            >
              <span className={isActive ? "text-blue-600" : "text-slate-400"}>
                {item.icon}
              </span>
              {item.name}
              
              {isActive && (
                <div className="ml-auto h-1.5 w-1.5 rounded-full bg-blue-600" />
              )}
            </Link>
          );
        })}

        {/* Separate Logout Button */}
        <button 
          onClick={logoutUser} 
          className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-slate-600 hover:bg-slate-50 hover:text-slate-900 transition-all w-full mt-2"
        >
          <span className="text-slate-400"><LogOut size={20} /></span>
          Logout
        </button>
      </nav>

      

      {/* 3. Bottom Section: Support/Settings */}
      <div className="shrink-0 border-t border-slate-200 p-4">
        <button className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-50 hover:text-slate-900 transition-colors">
          <Settings size={20} className="text-slate-400" />
          Settings
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;