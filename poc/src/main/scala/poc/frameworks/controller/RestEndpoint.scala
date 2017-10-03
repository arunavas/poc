package poc.frameworks.controller

import net.liftweb.json.{Extraction, JsonParser, compactRender}
import org.http4s._
import org.http4s.dsl._
import poc.frameworks.dao.Dao
import poc.frameworks.model.external.{LoginRequest, ServiceResponse}
import poc.frameworks.model.internal.User
import poc.frameworks.service.Service.{login, register}
import poc.frameworks.util.{JsonFormat, ApplicationUtil}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by arunavas on 28/9/17.
  */
object RestEndpoint {
  implicit val formats = JsonFormat.formats
  lazy val dao: Dao[User] = new ApplicationUtil[User]().getDao
  lazy val registerUser = register(dao)
  lazy val loginUser = login(dao)

  val restService = HttpService {
    case req @ POST -> Root / "sample" / "register" =>
      println(s"ReqPath-POST: ${req.uri.path} from ${req.remoteUser}@${req.remoteAddr}")
      req.as[String] flatMap {
        request => {
          println(s"Received $request")
          val user = JsonParser.parse(request).extract[User]
          val response = registerUser(user) map {
            case Left(e) => ServiceResponse("FAILURE", "1", e, None)
            case Right(d) => ServiceResponse("SUCCESS", "0", "SUCCESS", None)
          } map (r => compactRender(Extraction.decompose(r)))
          Ok(response)
        }
      }

    case req @ POST -> Root / "sample" / "login" =>
      println(s"ReqPath-POST: ${req.uri.path} from ${req.remoteUser}@${req.remoteAddr}")
      req.as[String] flatMap {
        request => {
          println(s"Received $request")
          val login = JsonParser.parse(request).extract[LoginRequest]
          val response = loginUser(login.un, login.pwd) map {
            case Left(e) => ServiceResponse("FAILURE", "1", e, None)
            case Right(d) => ServiceResponse("SUCCESS", "0", "SUCCESS", None)
          } map (r => compactRender(Extraction.decompose(r)))
          Ok(response)
        }
      }
  }

}
