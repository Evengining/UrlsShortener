package shotener.urls

import java.util.{Timer, TimerTask}
import scala.util.Random
import scala.collection.concurrent._
import scala.concurrent.duration._
import types.{GeneratedUrl, Value}

case object WorkWithUrls {
  def generateNewUrl = Random.alphanumeric.take(6).mkString

  def intervalRemoveUrl(generatedUrls: TrieMap[GeneratedUrl, Value], generatedUrl: GeneratedUrl, lifeTimeInDays: Int) = {
    val timer = new Timer
    val clearTask = new TimerTask {
      def run = generatedUrls -= generatedUrl
    }
    timer.schedule(clearTask, lifeTimeInDays.days.toMillis)
  }
}


