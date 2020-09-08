/**
 * Description         : This is my sandbox
 */

import java.time.Instant
import java.util.UUID

import ingestion.Device

//val c = "Hello, World!"
//println(c)
//
//val rr = Instant.now.getEpochSecond
//println(rr)
//println("1509793231")

//val uuid = UUID.randomUUID.toString()
//println(uuid)

val device = new Device("r")
println(device.generateMessage())
