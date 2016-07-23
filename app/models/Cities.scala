package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future


case class City(id: Long, name: String, countryCode: String, district: String, population: Long)

// Definition of the City table
class Cities(tag: Tag) extends Table[City](tag, "City") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("Name")

    def countryCode = column[String]("CountryCode")

    def district = column[String]("District")

    def population = column[Long]("Population")

    def country = foreignKey("SUP_FK", countryCode, Countries.countries)(_.code, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    def * = (id, name, countryCode, district, population) <>(City.tupled, City.unapply)
}

// Services for City table
object Cities {

    val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

    val cities = TableQuery[Cities]

    def get(id: Long): Future[Option[City]] = {
        db.run(cities.filter(_.id === id).result.headOption)
    }

    def get(): Future[Seq[City]] = {
        db.run(cities.result)
    }

}
