package poc.frameworks.service

import poc.frameworks.dao.Dao
import poc.frameworks.model.external.{User => ExtUser}
import poc.frameworks.model.internal.User
import poc.frameworks.model.internalToExternalUser

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by arunavas on 28/9/17.
  */
object Service {

  //Doubt: I validated whether user name is unique or not by fetching by user name here. Am I hampering the purity of this function by doing some other stuff than only registering?. Not sure if validation needs to be part of this or upper layer. Please clarify
  //Question: which is better? parameterizing the dao object and calling dao.create inside body or parameterizing a (f: User => Future[Boolean]) and calling 'f' inside body.
  def register(dao: Dao[User]): User => Future[Either[String, String]] = user => dao.read(_.userName == user.userName) flatMap {
    case Some(_) => Future(Left("User Name already exists!"))
    case None    => dao.create(user) map (if (_) Right("User created successfully!") else Left("Error creating user. Please try again!"))
  }
  def login(dao: Dao[User]): (String, String) => Future[Either[String, ExtUser]] = (un, pwd) => dao.read(_.userName == un) map {
    case None    => Left("Invalid Credentials!")
    case Some(x) => if (x.password == pwd) Right(x) else Left("Invalid Credentials!")
  }

}