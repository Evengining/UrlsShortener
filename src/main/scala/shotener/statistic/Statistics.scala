package shotener.statistic

import scala.collection.concurrent._
import types.{GeneratedUrl, Value}

object Statistics {
  def addView(generatedUrls: TrieMap[GeneratedUrl, Value], generatedUrl: GeneratedUrl): Unit = {
    generatedUrls.get(generatedUrl).foreach(_.views += 1)
  }

  def returnStatisticsOfUrl(generatedUrls: TrieMap[GeneratedUrl, Value], generatedUrl: GeneratedUrl) = {
    generatedUrls.get(generatedUrl) match {
      case Some(value) => value.views.toString
    }
  }
}
