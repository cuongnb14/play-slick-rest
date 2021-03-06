package utils

import play.api.libs.json.JsValue

case class JsonResponse(code: Int, message: String, data: JsValue)

object JsonResponse {
  val CODE_SUCCESS = 200
  val CODE_FAIL_GENERAL = 201

  val CODE_FAIL_PARAM_REQUIRED = 202

  val CODE_FAIL_USER_NOT_FOUND = 203
  val CODE_FAIL_PASS_NOT_MATCH = 204
  val CODE_FAIL_INVALID_EXTERNAL_ID = 205
  val CODE_FAIL_INVALID_ACCESS_TOKEN = 206
  val CODE_FAIL_SESSION_TOKEN_NOT_FOUND = 207
  val CODE_FAIL_INVALID_USER_LOGIN = 208


  val CODE_SUCCESS_CREATE_USER = 300

  val CODE_SUCCESS_CREATE_FILE = 300
  val CODE_FAIL_CREATE_FILE = 301

}