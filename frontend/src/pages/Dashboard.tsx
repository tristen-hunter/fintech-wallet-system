import BarChart from "../components/BarChart"
import Networth from "../components/Networth"
import TransactionHistory from "../components/TransactionHistory"

const Dashboard = () => {
  return (
    /* 1. h-[calc(100vh-100px)]: Ensures the dashboard fills the viewport 
         minus the estimated height of your Topbar.
      2. grid-cols-[1.5fr_1fr]: Sets the 60% / 40% width ratio.
      3. grid-rows-[0.4fr_0.6fr]: Sets the 40% / 60% height ratio for the left side.
    */
    <div className="grid h-[calc(100vh-120px)] w-full grid-cols-1 gap-6 lg:grid-cols-[1.5fr_1fr] lg:grid-rows-[0.4fr_0.6fr]">
      
      {/* Top Left: Networth (40% Height, 60% Width) */}
      <div className="lg:col-start-1 lg:row-start-1">
        <Networth />
      </div>

      {/* Bottom Left: BarChart (60% Height, 60% Width) */}
      <div className="lg:col-start-1 lg:row-start-2">
        <BarChart />
      </div>

      {/* Right Side: Transaction History (100% Height, 40% Width) */}
      <div className="lg:col-start-2 lg:row-span-2 lg:row-start-1">
        <TransactionHistory />
      </div>
      
    </div>
  )
}

export default Dashboard