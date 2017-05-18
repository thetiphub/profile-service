package models

import org.joda.time.DateTime
import play.api.data.Form
import play.api.data.Forms._

case class Profile(firstName: String, lastName: String, dateOfBirth: DateTime, email: String, password: String)

object ProfileForm {
  val form = Form(
    mapping(
      "first_name" -> text,
      "last_name" -> text,
      "date_of_birth" -> jodaDate("yyyy-MM-dd"),
      "email" -> text,
      "password" -> text
    )(Profile.apply)(Profile.unapply)
  )
}