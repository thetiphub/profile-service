package services

import play.api.http.Writeable
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global

class WSHttp (ws: WSClient) extends Http {
  val client: WSClient = ws
}

trait Http extends HttpGet with HttpPost with HttpPut with HttpDelete {
}

trait HttpGet extends HttpMethod {
  def GET[A](url: String) = {
    client
      .url(url)
      .get()
      .map(_.asInstanceOf[A])
  }
}

trait HttpPost extends HttpMethod {
  def POST[A: Writeable](url: String, data: A) = {
    client
      .url(url)
      .post(data)
      .map(_.asInstanceOf[A])
  }
}

trait HttpPut extends HttpMethod {
  def PUT[A: Writeable](url: String, data: A) = {
    client
      .url(url)
      .put(data)
      .map(_.asInstanceOf[A])
  }
}

trait HttpDelete extends HttpMethod {
  def DELETE(url: String) = {
    client.url(url).delete()
  }
}

trait HttpMethod {
  val client: WSClient
}