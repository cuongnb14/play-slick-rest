package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future


case class CountryLanguage(countryCode: String, language: String, isOfficial: String, percentage: Float)

// Definition of the CountryLanguage table
class CountryLanguages(tag: Tag) extends Table[CountryLanguage](tag, "CountryLanguage") {
    def countryCode = column[String]("CountryCode", O.PrimaryKey)

    def language = column[String]("Language")

    def isOfficial = column[String]("IsOfficial")

    def Percentage = column[Float]("Percentage")

    def * = (countryCode, language, isOfficial, Percentage) <>(CountryLanguage.tupled, CountryLanguage.unapply)
}

// Services for CountryLanguage table
object CountryLanguages {

    val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

    val countryLanguages = TableQuery[CountryLanguages]

    def get(countryCode: String): Future[Option[CountryLanguage]] = {
        db.run(countryLanguages.filter(_.countryCode === countryCode).result.headOption)
    }

    def get(): Future[Seq[CountryLanguage]] = {
        db.run(countryLanguages.result)
    }

}
