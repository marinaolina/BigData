package ingestion

import java.util.UUID

object Simulator {
  def simulate() = {
    val uuid = UUID.randomUUID.toString()
    val device = new Device(uuid)

    val t = new java.util.Timer()
    val task = new java.util.TimerTask {
      def run() = Aggregator.put(device.generateMessage())
    }
    t.schedule(task, 1000L, 1000L)

  }

}
