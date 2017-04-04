package connectors

import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.duration._

import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import play.api.libs.json._

import scala.concurrent.ExecutionContext

class AuthServiceConnector @Inject() (ws: WSClient) {
    def create(email: String, password: String)(implicit ec: ExecutionContext) = {
        val data = Json.obj(
            "email" -> email,
            "username" -> "unk",
            "password" -> password
        )

        ws.url("http://localhost:9000/user").post(data)
    }

    def verify(implicit ec: ExecutionContext) = Future[String] {
        ???
    }
}