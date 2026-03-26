import { Outlet } from "react-router-dom";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";


// This is the general layout of all the elements
const MainLayout = () => {
  return (
    <div className="flex min-h-screen bg-slate-50">
      {/* Sidebar - Assuming it has a fixed width like w-64 */}
      <Sidebar />

      <div className="flex flex-1 flex-col">
        <Topbar />
        
        {/* Content Area */}
        <main className="p-6 pl-72">
          <div className="mx-auto max-w-7xl">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
};

export default MainLayout;