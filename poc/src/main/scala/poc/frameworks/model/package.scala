package poc.frameworks

import poc.frameworks.model.external.{User => EUser}
import poc.frameworks.model.internal.{User => IUser}

/**
  * Created by arunavas on 28/9/17.
  */
package object model {

  implicit def externalToInternalUser(u: EUser): IUser = IUser(null, u.name, u.mobile, u.address, u.userName, u.password)
  implicit def internalToExternalUser(u: IUser): EUser = EUser(u.name, u.mobile, u.address, u.userName, u.password)

}
