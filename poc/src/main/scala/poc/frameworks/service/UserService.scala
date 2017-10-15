package poc.frameworks.service

import poc.frameworks.dao.UserDao
import poc.frameworks.model.external.{LoginRequest, User => ExtUser}
import poc.frameworks.model.{externalToInternalUser, internalToExternalUser}
import poc.frameworks.repo.Users

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.Reader

/**
  * Created by arunavas on 11/10/17.
  */
object UserService extends Users {

  def register: ExtUser => Reader[UserDao, Future[Either[String, String]]] = user => create(user).map(_.map(x => if (x) Right("SUCCESS") else Left("FAILURE")))
  def login: LoginRequest => Reader[UserDao, Future[Either[String, ExtUser]]] = login => read(_.userName == login.un).map(_.map {
    case None => Left("Invalid Credentials!")
    case Some(u) => if (u.password == login.pwd) Right(u) else Left("Invalid Credentials!")
  })

  def checkIfNewUser: ExtUser => Reader[UserDao, Future[Boolean]] = user => read(u => u.userName == user.userName || u.mobile == user.mobile).map(_.map {
    case None => true
    case Some(_) => false
  })

}