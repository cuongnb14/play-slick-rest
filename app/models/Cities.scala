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

  def getCountry(idCity: Long): Future[Option[Map[String, Any]]] = {
    val q = for {
      ci <- cities if ci.id === idCity
      co <- Countries.countries if co.code === ci.countryCode
    } yield (co.name, ci.name)

    val result: Future[Option[(String, String)]] = db.run(q.result.headOption)
    result.map{
      _ match {
        case Some(rs) => Some(Map[String, Any](
                        "city" -> rs._2,
                        "country" -> rs._1
                      ))
        case None => None
      }

    }
  }

  def getCountry(): Future[Seq[Map[String, Any]]] = {
    val q = for {
      ci <- cities
      co <- Countries.countries if co.code === ci.countryCode
    } yield (co.name, ci.name, ci.id, ci.population)


    val cs: Future[Seq[(String, String, Long, Option[Long])]] = db.run(q.take(10).result)
    cs.map {
      _.map(
        rs => Map[String, Any](
          "city" -> rs._2,
          "country" -> rs._1,
          "id" -> rs._3,
          "popu" -> rs._4.getOrElse(None)
        )
      )
    }
  }

}
