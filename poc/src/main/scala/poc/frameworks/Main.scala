package poc.frameworks

import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.{CORS, CORSConfig}
import poc.frameworks.controller.RestEndpoint

import scala.concurrent.duration._

/**
  * Created by arunavas on 28/9/17.
  */
object Main extends App {

  val service = RestEndpoint.restService
  val corsEnabledService = CORS(service, CORSConfig(
    anyOrigin = true,
    allowCredentials = false,
    1.day.toSeconds,
    anyMethod = false,
    Some(Set("localhost:80", "127.0.0.1:80")),
    Some(Set("POST")),
    None))

  val server = BlazeBuilder.bindHttp(Integer.valueOf(System.getProperty("server.port", "8080")), "0.0.0.0")
    .mountService(corsEnabledService)
    .run
  println(s"Service started and running at ${server.address.toString}")
  server.awaitShutdown()

}
