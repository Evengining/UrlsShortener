import io.circe.generic.auto._
import io.circe.parser._
import lol.http._
import lol.json._
import shortener.app._
import types.ValidDatas

import scala.concurrent.ExecutionContext.Implicits.global

object Main {
  def main(args: Array[String]): Unit = {
    Server.listen(8888){
      case req @ POST at url"/createNewUrl" => {
        req.readAs[String].map {
            decode[ValidDatas](_) match {
              case Right(v) => {
                if (v.url.contains("http://") || v.url.contains("https://"))
                  Ok(App.reqToGenerateUrl(v))
                else
                  BadRequest
              }
              case Left(_) => BadRequest
            }
        }
      }

      case GET at url"/$url" => {
        App.returnOldUrl(url) match {
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
