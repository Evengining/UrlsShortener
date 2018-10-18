import io.circe.generic.auto._
import io.circe.parser._
import lol.http._
import lol.json._
import shortener.app._
import types.ValidRequestData

import scala.concurrent.ExecutionContext.Implicits.global

object Api {
  def main(args: Array[String]): Unit = {
    Server.listen(8888) {
      case req@POST at url"/createNewUrl" => {
        req.readAs[String].map {
          decode[ValidRequestData](_) match {
            case Right(validRequestData) => {
              if (validRequestData.url.startsWith("http://") || validRequestData.url.startsWith("https://"))
                Ok(App.generateUrl(validRequestData))
              else
                BadRequest("Not found protocol http or https in url")
            }
            case Left(_) => BadRequest("Error in parameters")
          }
        }
      }

      case GET at url"/$url" => {
        App.returnOriginalUrl(url) match {
          case Some(url) => Redirect(url, 302)
          case None => NotFound
        }
      }

      case GET at url"/getStatistics/$url" => {
        App.getStatistics(url) match {
          case Some(views) => Ok(views)
          case None => NotFound
        }
      }

      case _ => NotFound
    }
    println("Listening on http://localhost:8888...")
  }
}
