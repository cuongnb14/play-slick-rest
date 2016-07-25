package controllers

import java.util
import javax.inject._

import play.api.libs.json._
import play.api._
import play.api.mvc._
import models._
import utils.Serializers._

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test = Action {
//    val c = City(1L, "Hnoi", "sdf", "sdf", 9L)
//    val c1 = City(1L, "Hnoi sd", "sdf", "sdf", 9L)
//    val cs: Seq[City] = c :: c1 :: Nil


//    val m: Map[String, Either[Long, String]] = Map(
//      "val1" -> Left(1L),
//      "val2" -> Right("f"),
//      "d"    -> Right("dsf")
//    )

    Ok(" ")
   // Ok(m.asJson.noSpaces)
  }

}
