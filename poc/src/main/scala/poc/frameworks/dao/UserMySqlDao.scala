package poc.frameworks.dao
import scala.concurrent.Future

/**
  * Created by arunavas on 3/10/17.
  */
class UserMySqlDao[User] extends Dao[User] {

  /*
  Will implement using mysql connection object later.
  Also, it would be good if you can give me some idea whether I should have dao for individual models (like UserDao, ProductDao etc) or shall type parameterize the model and keep one dao per db.
   */

  override def create(data: User): Future[Boolean] = ???

  override def read(f: (User) => Boolean): Future[Option[User]] = ???

  override def readAll(f: (User) => Boolean): Future[List[User]] = ???

  override def update(data: User)(f: (User) => Boolean): Future[Boolean] = ???

  override def delete(f: (User) => Boolean): Future[Boolean] = ???
}
