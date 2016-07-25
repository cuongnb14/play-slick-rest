package models

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future


case class City(id: Long, name: String, countryCode: String, district: String, population: Option[Long])

// Definition of the City table
class Cities(tag: Tag) extends Table[City](tag, "City") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("Name")

  def countryCode = column[String]("CountryCode")

  def district = column[String]("District")

  def population = column[Option[Long]]("Population")

  def * = (id, name, countryCode, district, population) <>(City.tupled, City.unapply)

  def country = foreignKey("SUP_FK", countryCode, Countries.countries)(_.code, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
}

/*
Ex:
- drop(10).take(5): limit 5 offset 10
- sortBy(_.name.desc.nullsFirst):  order by "COF_NAME" desc nulls first

 */

// Services for City table
object Cities {

  val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

  lazy val cities = TableQuery[Cities]

  // filter by field
  def get(id: Long): Future[Option[City]] = {
    db.run(cities.filter(_.id === id).result.headOption)
  }

  // limit 10
  def get(): Future[Seq[City]] = {
    db.run(cities.take(10).result)
  }

  def getCountry(idCity: Long): Future[Option[(String, String)]] = {
    val q = for {
      ci <- cities if ci.id === idCity
      co <- Countries.countries if co.code === ci.countryCode
    } yield (co.name, ci.name)

    db.run(q.result.headOption)
  }

  def getCountry(): Future[Seq[Map[String, Either[Long, String]]]] = {
    val q = for {
      ci <- cities
      co <- Countries.countries if co.code === ci.countryCode
    } yield (co.name, ci.name, ci.id, ci.population)


    val cs: Future[Seq[(String, String, Long, Option[Long])]] = db.run(q.take(100).result)
    cs.map {
      _.map(
        rs => Map[String, Either[Long, String]](
          "city" -> Right(rs._2),
          "country" -> Right(rs._1),
          "id" -> Left(rs._3),
          "popu" -> Left(rs._4.getOrElse(1L))
        )
      )
    }
  }

}
