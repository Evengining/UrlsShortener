package object types {

  type GeneratedUrl = String

  case class Value(oldUrl: String, days: Int, var views: Int)

  case class ValidDatas(url: String, days: Int)

}