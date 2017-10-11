package poc.frameworks.repo

import poc.frameworks.dao.{Dao, UserDao}
import poc.frameworks.model.internal.User

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.Reader

/**
  * Created by arunavas on 11/10/17.
  */
trait Users {

  protected def create(data: User): Reader[UserDao, Future[Boolean]] = Reader(dao => dao.create(data))
  protected def read(f: User => Boolean): Reader[UserDao, Future[Option[User]]] = Reader(dao => dao.read(f))
  protected def readAll(f: User => Boolean): Reader[UserDao, Future[List[User]]] = Reader(dao => dao.readAll(f))
  protected def update(data: User)(f: User => Boolean): Reader[UserDao, Future[Boolean]] = Reader(dao => dao.update(data)(f))
  protected def delete(f: User => Boolean): Reader[UserDao, Future[Boolean]] = Reader(dao => dao.delete(f))

}
