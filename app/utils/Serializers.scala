package utils

import play.api.libs.json._
import play.api.libs.json.Json.JsValueWrapper
import models._

object Serializers {

  implicit val coffeeWrites = Json.writes[City]
  implicit val countryWrites = Json.writes[Country]
  implicit val countryLanguageWrites = Json.writes[CountryLanguage]
  implicit val jsonResponseWrites = Json.writes[JsonResponse]


  implicit val mapWriter = new Writes[Map[String, Any]] {
    def writes(map: Map[String, Any]): JsValue =
      Json.obj(map.map { case (s, o) =>
        val ret: (String, JsValueWrapper) = o match {
          case _: String => s -> JsString(o.toString())
          case _: Int    => s -> JsNumber(o.asInstanceOf[Int])
          case _: Long    => s -> JsNumber(o.asInstanceOf[Long])
          case _         => s -> JsNull
        }
        ret
      }.toSeq: _*)
  }
}
