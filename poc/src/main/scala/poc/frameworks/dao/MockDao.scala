package poc.frameworks.dao

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by arunavas on 28/9/17.
  */
class MockDao[A] extends Dao[A] {

  val store = new ListBuffer[A]()

  override def create(data: A): Future[Boolean] = {
    store += data
    Future(true)
  }

  override def read(f: (A) => Boolean): Future[Option[A]] = Future(store find f)

  override def readAll(f: A => Boolean): Future[List[A]] = Future(store.toList)

  override def update(data: A)(f: A => Boolean): Future[Boolean] = {
    val idx = store indexWhere f
    store.update(idx, data)
    Future(idx >= 0)
  }

  override def delete(f: (A) => Boolean): Future[Boolean] = {
    val idx = store indexWhere f
    store.remove(idx)
    Future(idx >= 0)
  }
}
