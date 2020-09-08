package ingestion
import java.io.IOException
import java.io.{BufferedInputStream, DataInputStream, PrintWriter}
import java.net.ServerSocket


object Aggregator {
  val serverSocket = new ServerSocket(8080)
  var streamIn: DataInputStream = null
  var output: PrintWriter = null

  def run() {
    while (true) {
      // This will block until a connection comes in.
      val socket = serverSocket.accept()
      println("")
      println("socket accepted")

      output = new PrintWriter(socket.getOutputStream(), true)
      streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()))
      try {
        streamIn.readUTF
      // this blocks until session disconnects
      } catch {
        case ioe: IOException =>
          println("")
          ioe.printStackTrace()
      }
      if (socket != null) socket.close
      if (streamIn != null) streamIn.close
      if (output != null) output.close
      output = null
    }
  }


  def put(message: String) = {
    try {
      if (output != null) {
        output.println(message)
        output.flush()
      } else{
        print(".")
      }
    } catch {
      case exception: Exception => exception.printStackTrace()
    }
  }

}
