package services

import play.api.libs.ws.{WSClient, WSResponse}
import play.api.http.Status.{OK, SERVICE_UNAVAILABLE}
import play.api.http.Writeable
import play.api.libs.json.Reads

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class WSHttp (ws: WSClient) extends Http {
  val client: WSClient = ws
}

trait Http extends HttpGet with HttpPost with HttpPut with HttpDelete

trait HttpGet extends HttpMethod {
  def GET[A: Writeable](url: String)(implicit reads: Reads[A]): Future[Either[Int, A]] = {
    client
      .url(url)
      .get()
      .map(mapResponse[A])
  }
}

trait HttpPost extends HttpMethod {
  def POST[A: Writeable](url: String, data: A)(implicit reads: Reads[A]): Future[Either[Int, A]] = {
    client
      .url(url)
      .post(data)
      .map(mapResponse[A])
  }
}

trait HttpPut extends HttpMethod {
  def PUT[A: Writeable](url: String, data: A)(implicit reads: Reads[A]): Future[Either[Int, A]] = {
    client
      .url(url)
      .put(data)
      .map(mapResponse[A])
  }
}

trait HttpDelete extends HttpMethod {
  def DELETE(url: String)(implicit reads: Reads[Unit]): Future[Either[Int, Unit]] = {
    client
      .url(url)
      .delete()
      .map(mapResponse[Unit])
  }
}

trait HttpMethod {
  val client: WSClient

  protected def mapResponse[A](response: WSResponse)(implicit reads: Reads[A]): Either[Int, A] = {
    if (response.status == OK) response.json.asOpt[A] match {
      case Some(result) => Right(result)
      case _ => Left(SERVICE_UNAVAILABLE)
    }
    else Left(response.status)
  }
}