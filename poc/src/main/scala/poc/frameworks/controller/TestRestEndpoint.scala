package poc.frameworks.controller

import net.liftweb.json.{Extraction, JsonParser, compactRender}
import org.http4s._
import org.http4s.dsl._
import poc.frameworks.dao.{MockUserDao, UserDao}
import poc.frameworks.model.external.{LoginRequest, ServiceResponse, User}
import poc.frameworks.service.UserService
import poc.frameworks.util.JsonFormat
import poc.frameworks.validation.userValidation

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalaz.{Kleisli, Reader}

/**
  * Created by arunavas on 28/9/17.
  */
trait TestRestEndpoint {
  implicit val formats = JsonFormat.formats

  val restService = HttpService {
    case req @ POST -> Root / "sample1" / "register" =>
      println(s"ReqPath-POST: ${req.uri.path} from ${req.remoteUser}@${req.remoteAddr}")
      req.as[String] flatMap {
        request => {
          println(s"Received $request")
          Ok("SUCCESS")
        }
      }

    case req @ POST -> Root / "sample1" / "login" =>
      println(s"ReqPath-POST: ${req.uri.path} from ${req.remoteUser}@${req.remoteAddr}")
      req.as[String] flatMap {
        request => {
          println(s"Received $request")
          Ok("SUCCESS")
        }
      }
  }

}

object TestRestEndpoint extends TestRestEndpoint