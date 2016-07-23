package utils

import play.api.libs.json._
import models._

object Serializers {
    implicit val coffeeWrites = Json.writes[City]
    implicit val countryWrites = Json.writes[Country]
    implicit val countryLanguageWrites = Json.writes[CountryLanguage]
    implicit val jsonResponseWrites = Json.writes[JsonResponse]
}
