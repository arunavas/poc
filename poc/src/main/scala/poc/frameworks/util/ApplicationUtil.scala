package poc.frameworks.util

import poc.frameworks.dao.{Dao, MockDao}

/**
  * Created by arunavas on 28/9/17.
  */
class ApplicationUtil[A] {
  def getDao: Dao[A] = System.getProperty("DB", "MOCK") match {
    case "MYSQL" => ???
    case "MOCK" => new MockDao[A]()
  }
}
