package connectors

import scala.concurrent.Future
import play.api.libs.ws._
import play.api.libs.json._
import services.{Http, WSHttp}

import scala.concurrent.ExecutionContext

class AuthServiceConnector(ws: WSClient) extends TAuthServiceConnector {
  val http: Http = new WSHttp(ws)
}

trait TAuthServiceConnector {
  val http: Http

  def create(email: String, password: String)(implicit ec: ExecutionContext) = {
    val data = Json.obj(
      "email" -> email,
      "username" -> "unk",
      "password" -> password
    )

    http.POST("http://localhost:9000/user", data)
  }

  def verify(implicit ec: ExecutionContext) = Future[String] {
    ???
  }
}