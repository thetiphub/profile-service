package controllers

import javax.inject._

import play.api.libs.ws._
import play.api.mvc._
import controllers.helpers.Authentication
import connectors._
import models.ProfileForm
import play.api.Configuration

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProfileController @Inject()(ws: WSClient, config: Configuration) extends Controller with Authentication {
  val authServiceConnector: AuthServiceConnector = new AuthServiceConnector(ws, config)

  def get = Action.async { implicit request =>
    authenticateRequest {
      case Right(x) => Ok(s"I have no data to fetch at the moment have this instead ${x}")
      case Left(UNAUTHORIZED) => Unauthorized
      case Left(x) => ServiceUnavailable
    }
  }

  def create = Action.async { implicit request =>
    ProfileForm.form.bindFromRequest.fold(
      _ => Future(BadRequest("has errors")),
      user => {
        for {
          _ <- authServiceConnector.create(user.email, user.password)
        } yield {
          Ok("User created")
        }
      }
    )
  }
}
