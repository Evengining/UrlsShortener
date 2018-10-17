package shortener.app

import scala.collection.concurrent._
import shortener.urls._
import types.{GeneratedUrl, ValidRequestData, DataOfGeneratedUrl}

object App{
  val generatedUrls: TrieMap[GeneratedUrl, DataOfGeneratedUrl] = TrieMap.empty[GeneratedUrl, DataOfGeneratedUrl]

  def generateUrl(dataRequest: ValidRequestData): GeneratedUrl = {
    def newUrl: GeneratedUrl = {
      val url = WorkWithUrls.generateNewUrl
      if (generatedUrls.contains(url))
        newUrl
      else
        url
    }

    val lifeTimeInDays = dataRequest.days.getOrElse(5)
    val views = 0
    val generatedUrl = newUrl

    generatedUrls += generatedUrl -> DataOfGeneratedUrl(dataRequest.url, views)

    WorkWithUrls.intervalRemoveUrl(generatedUrls, generatedUrl, lifeTimeInDays)
    generatedUrl
  }

  def returnOriginalUrl(generatedUrl: GeneratedUrl): Option[String] = {
    generatedUrls.get(generatedUrl) map {
      dataOfGeneratedUrl =>
        generatedUrls += generatedUrl -> dataOfGeneratedUrl.copy(dataOfGeneratedUrl.originalUrl, dataOfGeneratedUrl.views + 1)
        dataOfGeneratedUrl.originalUrl
    }
  }

  def getStatistics(generatedUrl: GeneratedUrl): Option[String] = {
    generatedUrls.get(generatedUrl) map (_.views.toString)
  }
}
