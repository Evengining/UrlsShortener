package object types {

  type GeneratedUrl = String

  case class DataOfGeneratedUrl(originalUrl: String, views: Int)

  case class ValidRequestData(url: String, days: Option[Int])

}