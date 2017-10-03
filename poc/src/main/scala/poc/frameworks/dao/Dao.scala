package poc.frameworks.dao

import scala.concurrent.Future

/**
  * Created by arunavas on 3/10/17.
  */
trait Dao[A] {

  def create(data: A): Future[Boolean]
  def read(f: A => Boolean): Future[Option[A]]
  def readAll(f: A => Boolean): Future[List[A]]
  def update(data: A)(f: A => Boolean): Future[Boolean]
  def delete(f: A => Boolean): Future[Boolean]

}
