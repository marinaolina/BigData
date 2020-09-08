/**
 * Author              : Marina Olina
 * Author email        : marina.olina@inox.lv
 * Object Name         : Ingestion
 * Script Creation Date: 05.09.2020
 * Description         : Designed to simulate messages of 3 IoT devices
 */


package ingestion

object Ingestion {
  def main(args: Array[String]): Unit = {
    Simulator.simulate()
    Simulator.simulate()
    Simulator.simulate()
    Aggregator.run()
  }

}
