package controllers

import javax.inject._
import play.api._
import play.api.libs.ws._
import play.api.mvc._
import connectors._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProfileController @Inject()(ws: WSClient) extends Controller {

    def get = Action { implicit request =>
    
        val asc = new AuthServiceConnector(ws)
        asc.create("hello", "password")

        Ok("Success")
    }

    def create = Action { implicit request =>
        ???
    }

}
