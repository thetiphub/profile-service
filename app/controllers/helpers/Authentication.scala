package controllers.helpers

import connectors.AuthServiceConnector
import play.api.mvc.{AnyContent, Request, Result}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Authentication {
  val authServiceConnector: AuthServiceConnector

  def authenticateRequest(callback: (Either[Int, String]) => Result)(implicit request: Request[AnyContent])
  : Future[Result] = {
    val token = request.headers.get("Authorisation").getOrElse("")
    authServiceConnector.verify(token).map{ x => callback(x) }
  }
}

