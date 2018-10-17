import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import lol.http._
import lol.json._
import scala.concurrent.ExecutionContext.Implicits.global

object client {
  def main(args: Array[String]): Unit = {
    case class ValidJson(url: String, days: Int = 5)

    println(Client.runSync(Post("http://localhost:8888/createNewUrl", """{"url": "https://google.com", "days": "null"}""")){
      res => res.readAs[String]
    })

    println("Listening on http://localhost:8888...")
  }
}

