package poc.frameworks.dao
import poc.frameworks.model.internal.User

import scala.concurrent.Future

/**
  * Created by arunavas on 3/10/17.
  */
class UserMySqlDao extends UserDao {

  /*
  Will implement using mysql connection object later.
   */

  override def create(data: User): Future[Boolean] = ???

  override def read(f: (User) => Boolean): Future[Option[User]] = ???

  override def readAll(f: (User) => Boolean): Future[List[User]] = ???

  override def update(data: User)(f: (User) => Boolean): Future[Boolean] = ???

  override def delete(f: (User) => Boolean): Future[Boolean] = ???
}
