package poc.frameworks.dao

import poc.frameworks.model.internal.User

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by arunavas on 28/9/17.
  */
trait MockUserDao extends UserDao {

  val store = new ListBuffer[User]()

  override def create(data: User): Future[Boolean] = {
    val user = User((store.length + 1).toString, data.name, data.mobile, data.address, data.userName, data.password)
    store += user
    Future(true)
  }

  override def read(f: User => Boolean): Future[Option[User]] = Future(store find f)

  override def readAll(f: User => Boolean): Future[List[User]] = Future(store filter f toList)

  override def update(data: User)(f: User => Boolean): Future[Boolean] = {
    val idx = store indexWhere f
    store.update(idx, data)
    Future(idx >= 0)
  }

  override def delete(f: (User) => Boolean): Future[Boolean] = {
    val idx = store indexWhere f
    store.remove(idx)
    Future(idx >= 0)
  }
}
