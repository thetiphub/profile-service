package controllers

import javax.inject._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.ws._
import play.api.mvc._
import connectors._
import org.joda.time.DateTime
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProfileController @Inject()(ws: WSClient) extends Controller {
    val authServiceConnector: AuthServiceConnector = new AuthServiceConnector(ws)
    case class UserData(firstName: String, lastName: String, dateOfBirth: DateTime, email: String, password: String)

    val userForm = Form(
        mapping(
            "first_name" -> text,
            "last_name" -> text,
            "date_of_birth" -> jodaDate("yyyy-MM-dd"),
            "email" -> text,
            "password" -> text
        )(UserData.apply)(UserData.unapply)
    )

    def get = Action { implicit request =>
        ???       
    }

    def create = Action.async { implicit request =>
        userForm.bindFromRequest.fold(
             formWithErrors => Future(BadRequest("has errors")),
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
