package poc.frameworks.model.external

/**
  * Created by arunavas on 17/9/17.
  */
case class LoginRequest(un: String, pwd: String)
case class User(name: String, mobile: String, address: String, userName: String, password: String)
case class ServiceResponse(sts: String, stsCd: String, stsMsg: String, data: Option[User])
