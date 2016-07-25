package controllers

import akka.actor.ActorSystem
import javax.inject._

import models.Cities
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future, Promise}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import utils._
import utils.Serializers._
import utils.ShortCut._

/**
  * This controller creates an `Action` that demonstrates how to write
  * simple asynchronous code in a controller. It uses a timer to
  * asynchronously delay sending a response for 1 second.
  *
  * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
  *                    run code after a delay.
  * @param exec        We need an `ExecutionContext` to execute our
  *                    asynchronous code.
  */
@Singleton
class WorldController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {
  /**
    * Create an Action that returns a plain text message after a delay
    * of 1 second.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/message`.
    */
  def getCities = Action.async {
    val cities = Cities.get()
    cities.map(citySeq => {
      val response = JsonResponse(JsonResponse.CODE_SUCCESS, "All City List", citySeq.asJson)
      Ok(response.asJson.noSpaces)
    })
  }

  def getCountryOfCity(id: Long) = Action.async {
    val city = Cities.getCountry(id)
    city.map({
      case Some(c) =>
        val response = JsonResponse(JsonResponse.CODE_SUCCESS, "Country", c.asJson)
        Ok(response.asJson.noSpaces)
      case None =>
        jResponse(JsonResponse.CODE_SUCCESS, "Error")
    })
  }

  def getCountryOfAllCity = Action.async {
    val cities = Cities.getCountry()
    cities.map(citySeq => {
      jResponse("", citySeq.asJson)
    })
  }


}
