package ingestion

import java.time.Instant

import scala.util.Random

class Device(deviceId: String) {
  val r = new Random()

  def generateLongitude(): Double = {
    val long = r.nextDouble() * 360 - 180
    long
  }

  def generateLatitude(): Double = {
    val lat = r.nextDouble() * 180 - 90
    lat
  }

  def generateTemp(): Int = {
    val temp = r.nextInt(200) - 100
    temp
  }

  def generateMessage(): String = {
    val message = s"""{"data":{"deviceId": "${deviceId}","temperature": ${generateTemp()},"location":{"latitude":"${generateLatitude()}","longitude":"${generateLongitude()}"},"time":"${Instant.now.getEpochSecond}"}}"""
    message
  }
}

