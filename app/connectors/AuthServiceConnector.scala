package connectors

import play.api.Configuration

import scala.concurrent.Future
import play.api.libs.ws._
import play.api.libs.json._
import services.{Http, WSHttp}

import scala.concurrent.ExecutionContext.Implicits.global

class AuthServiceConnector(ws: WSClient, config: Configuration) {
  val http: Http = new WSHttp(ws)

  def create(email: String, password: String) = {
    val url = config.getString("authService.urls.user").getOrElse("")

    println(s"url=$url")

    val data = Json.obj(
      "email" -> email,
      "username" -> "unk",
      "password" -> password
    )

    http.POST(url, data) map { a =>
      println(s"\n\n\n\n\n\n success $a \n\n\n\n\n")
    }
  }

  def verify(token: String): Future[Either[Int, String]] = {
    val url = config.getString("authService.urls.token").getOrElse("")
    http.GET[String](s"$url/$token")
  }
}