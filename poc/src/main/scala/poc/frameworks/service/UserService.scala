package poc.frameworks.service

import poc.frameworks.dao.UserDao
import poc.frameworks.model.external.{User => ExtUser}
import poc.frameworks.model.internal.User
import poc.frameworks.model.{internalToExternalUser, externalToInternalUser}
import poc.frameworks.repo.Users

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.Reader

/**
  * Created by arunavas on 11/10/17.
  */
object UserService extends Users {

  def register(user: ExtUser): Reader[UserDao, Future[Either[String, String]]] = create(user).map(_.map(x => if (x) Right("SUCCESS") else Left("FAILURE")))
  def login(un: String, pwd: String): Reader[UserDao, Future[Either[String, ExtUser]]] = read(_.userName == un).map(_.map {
    case None => Left("Invalid Credentials!")
    case Some(u) => if (u.password == pwd) Right(u) else Left("Invalid Credentials!")
  })

  def checkIfNewUser(userName: String, mobile: String): Reader[UserDao, Future[Boolean]] = read(u => u.userName == userName || u.mobile == mobile).map(_.map {
    case None => true
    case Some(_) => false
  })

}
