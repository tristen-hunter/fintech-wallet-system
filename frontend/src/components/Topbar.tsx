import { Menu, Bell } from "lucide-react";
import {useAuth} from "../context/AuthContext"

const Topbar = () => {
  const { user } = useAuth();

  console.log(user)
  console.log(user?.userName.slice(0,2).toUpperCase())
  
  return (
    <header className="sticky top-0 z-40 flex h-16 w-full items-center justify-between border-b border-slate-200 bg-white/80 px-6 backdrop-blur-md">
      {/* Left Section: Toggle & Title */}
      <div className="flex items-center gap-4">
        <button className="rounded-lg p-2 text-slate-600 hover:bg-slate-100 lg:hidden">
          <Menu size={20} />
        </button>
        <h1 className="text-xl font-semibold text-slate-900">Dashboard</h1>
      </div>

      {/* Right Section: Actions & Profile */}
      <div className="flex items-center gap-3">
        {/* Simple Notification Dot/Icon */}
        <button className="relative rounded-full p-2 text-slate-500 hover:bg-slate-100">
          <Bell size={20} />
          <span className="absolute top-2 right-2 h-2 w-2 rounded-full bg-blue-600 border-2 border-white"></span>
        </button>

        <div className="h-8 w-px bg-slate-200 mx-2" /> {/* Divider */}

        <div className="flex items-center gap-3 pl-2">
          <div className="text-right hidden sm:block">
            <p className="text-sm font-medium text-slate-900">{user?.userName}</p>
            <p className="text-xs text-slate-500">{user?.email}</p>
          </div>
          <div className="flex h-9 w-9 items-center justify-center rounded-full bg-blue-600 text-white font-bold">
            {user?.userName.slice(0, 2).toUpperCase()}
          </div>
        </div>
      </div>
    </header>
  );
};

export default Topbar;