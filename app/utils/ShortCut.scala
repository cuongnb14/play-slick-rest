package utils


import play.api.mvc.Results._
import play.api.mvc._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object ShortCut {

  def jResponse(message: String, data: Json): Result ={
    val response = JsonResponse(JsonResponse.CODE_SUCCESS, message, data)
    Ok(response.asJson.noSpaces)
  }

  def jResponse(data: Json): Result ={
    val response = JsonResponse(JsonResponse.CODE_SUCCESS, "Success", data)
    Ok(response.asJson.noSpaces)
  }

  def jResponse(code: Int, message: String): Result ={
    val response = JsonResponse(code, message, null)
    Ok(response.asJson.noSpaces)
  }


}