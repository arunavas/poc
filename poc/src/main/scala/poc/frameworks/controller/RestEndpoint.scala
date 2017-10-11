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
trait RestEndpoint {
  userDao: UserDao =>
  implicit val formats = JsonFormat.formats

  private def userExecutor[A](reader: Reader[UserDao, A]): A = reader(userDao)

  val restService = HttpService {
    case req @ POST -> Root / "sample" / "register" =>
      println(s"ReqPath-POST: ${req.uri.path} from ${req.remoteUser}@${req.remoteAddr}")
      req.as[String] flatMap {
        request => {
          println(s"Received $request")
          val user = JsonParser.parse(request).extract[User]
          val response = userValidation(user) match {
            case e @ h :: t => Future(ServiceResponse("FAILURE", "1", e.mkString("\n"), null))
            case Nil => userExecutor(UserService.checkIfNewUser(user.userName, user.mobile)
              .map(_.flatMap(if (_) userExecutor(UserService.register(user)) else Future(Left("User already exists!"))))) map {
              case Left(e) => ServiceResponse("FAILURE", "1", e, null)
              case Right(d) => ServiceResponse("SUCCESS", "0", "SUCCESS", null)
            }
          }
          Ok(response map (r => compactRender(Extraction.decompose(r))))
        }
      }

    case req @ POST -> Root / "sample" / "login" =>
      println(s"ReqPath-POST: ${req.uri.path} from ${req.remoteUser}@${req.remoteAddr}")
      req.as[String] flatMap {
        request => {
          println(s"Received $request")
          val login = JsonParser.parse(request).extract[LoginRequest]
          val response = userExecutor(UserService.login(login.un, login.pwd)) map {
            case Left(e) => ServiceResponse("FAILURE", "1", e, null)
            case Right(d) => ServiceResponse("SUCCESS", "0", "SUCCESS", null)
          } map (r => compactRender(Extraction.decompose(r)))
          Ok(response)
        }
      }
  }

}

object RestEndpoint extends RestEndpoint with MockUserDao