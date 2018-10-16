package shortener.app

import scala.collection.concurrent._
import shortener.urls._
import shortener.statistic.Statistics
import types.{GeneratedUrl, ValidDatas, Value}

case object App{
  val generatedUrls: TrieMap[GeneratedUrl, Value] = TrieMap.empty[GeneratedUrl, Value]

  def reqToGenerateUrl(datas: ValidDatas): GeneratedUrl = {
    def newUrl: GeneratedUrl = {
      val url = WorkWithUrls.generateNewUrl
      generatedUrls.find(_._1 == url) match {
        case Some(v) => newUrl
        case None => url
      }
    }

    val lifeTimeInDays = if (datas.days == 0) 5 else datas.days
    val views = 0
    val generatedUrl = newUrl

    generatedUrls(generatedUrl) = Value(datas.url, lifeTimeInDays, views)
    WorkWithUrls.intervalRemoveUrl(generatedUrls, generatedUrl, lifeTimeInDays)
    generatedUrl
  }

  def returnOldUrl(generatedUrl: GeneratedUrl): Option[String] = {
    generatedUrls.find(_._1 == generatedUrl) match {
      case Some(v) => {
        Statistics.addView(generatedUrls, generatedUrl)
        Some(v._2.oldUrl)
      }
      case None => None
    }
  }

  def getStatistics(generatedUrl: GeneratedUrl): Option[String] = {
    generatedUrls.find(_._1 == generatedUrl) match {
      case Some(v) => Some(Statistics.returnStatisticsOfUrl(generatedUrls, generatedUrl))
      case None => None
    }
  }
}
