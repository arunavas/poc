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

  private def uniqueUserCheck: (User) => Future[Boolean] = UserService.checkIfNewUser andThen userExecutor
  private def userRegistration: (User) => Future[Either[String, String]] = UserService.register andThen userExecutor
  private def userLogin: (LoginRequest) => Future[Either[String, User]] = UserService.login andThen userExecutor

  val restService = HttpService {
    case req @ POST -> Root / "sample" / "register" =>
      println(s"ReqPath-POST: ${req.uri.path} from ${req.remoteUser}@${req.remoteAddr}")
      req.as[String] flatMap {
        request => {
          println(s"Received $request")
          val user = JsonParser.parse(request).extract[User]
          val response = userValidation(user) match {
            case e @ h :: t => Future(ServiceResponse("FAILURE", "1", e.mkString("\n"), null))
            case Nil => uniqueUserCheck(user).flatMap(if (_) userRegistration(user) else Future(Left("User already exists!"))) map {
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
          val response = userLogin(login) map {
            case Left(e) => ServiceResponse("FAILURE", "1", e, null)
            case Right(d) => ServiceResponse("SUCCESS", "0", "SUCCESS", d)
          } map (r => compactRender(Extraction.decompose(r)))
          Ok(response)
        }
      }
  }

}

object RestEndpoint extends RestEndpoint with MockUserDao