package connectors

import scala.concurrent.Future
import play.api.libs.ws._
import play.api.libs.json._
import services.{Http, WSHttp}

import scala.concurrent.ExecutionContext.Implicits.global

class AuthServiceConnector(ws: WSClient) extends TAuthServiceConnector {
  val http: Http = new WSHttp(ws)
}

trait TAuthServiceConnector {
  val http: Http

  def create(email: String, password: String) = {
    val data = Json.obj(
      "email" -> email,
      "username" -> "unk",
      "password" -> password
    )

    http.POST("http://localhost:9000/user", data) map { a =>
      println(s"\n\n\n\n\n\n success $a \n\n\n\n\n")
    }
  }

  def verify() = Future[String] {
    ???
  }
}