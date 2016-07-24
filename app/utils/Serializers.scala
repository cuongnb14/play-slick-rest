package utils

import play.api.libs.json._
import play.api.libs.json.Json.JsValueWrapper
import models._
import io.circe._, io.circe.generic.auto._, io.circe.jawn._, io.circe.syntax._

object Serializers {


  implicit val encodeIntOrString: Encoder[Either[Long, String]] =
    Encoder.instance(_.fold(_.asJson, _.asJson))

  implicit val decodeIntOrString: Decoder[Either[Long, String]] =
    Decoder[Long].map(Left(_)).or(Decoder[String].map(Right(_)))

//  implicit val coffeeWrites = Json.writes[City]
//  implicit val countryWrites = Json.writes[Country]
//  implicit val countryLanguageWrites = Json.writes[CountryLanguage]
//  implicit val jsonResponseWrites = Json.writes[JsonResponse]
//
//
//  implicit val mapWriter = new Writes[Map[String, Any]] {
//    def writes(map: Map[String, Any]): JsValue =
//      Json.obj(map.map { case (s, o) =>
//        val ret: (String, JsValueWrapper) = o match {
//          case _: String => s -> JsString(o.toString())
//          case _: Int => s -> JsNumber(o.asInstanceOf[Int])
//          case _ => s -> JsString(o.toString())
//        }
//        ret
//      }.toSeq: _*)
//  }
}
