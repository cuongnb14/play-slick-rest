package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future


case class Country(code: String, name: String, continent: String, region: String, surfaceArea: Float, indepYear: Int)

// Definition of the Country table
class Countries(tag: Tag) extends Table[Country](tag, "Country") {
    def code = column[String]("Code", O.PrimaryKey)

    def name = column[String]("Name")

    def continent = column[String]("Continent")

    def region = column[String]("Region")

    def surfaceArea = column[Float]("SurfaceArea")

    def indepYear = column[Int]("IndepYear")

    def * = (code, name, continent,region, surfaceArea, indepYear) <>(Country.tupled, Country.unapply)
}

// Services for Country table
object Countries {

    val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

    val countries = TableQuery[Countries]

    def get(code: String): Future[Option[Country]] = {
        db.run(countries.filter(_.code === code).result.headOption)
    }

    def get(): Future[Seq[Country]] = {
        db.run(countries.result)
    }

}
