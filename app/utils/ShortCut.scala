package utils


import play.api.mvc.Results._
import play.api.mvc._
//import io.circe._
//import io.circe.generic.auto._
//import io.circe.parser._
//import io.circe.syntax._
import play.api.libs.json.JsValue
import play.api.libs.json._
import utils.Serializers._

object ShortCut {

  def jResponse(message: String, data: JsValue): Result ={
    val response = JsonResponse(JsonResponse.CODE_SUCCESS, message, data)
    Ok(Json.toJson(response))
  }

  def jResponse(data: JsValue): Result ={
    val response = JsonResponse(JsonResponse.CODE_SUCCESS, "Success", data)
    Ok(Json.toJson(response))
  }

  def jResponse(code: Int, message: String): Result ={
    val response = JsonResponse(code, message, JsNull)
    Ok(Json.toJson(response))
  }


}