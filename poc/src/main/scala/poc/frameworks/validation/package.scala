package poc.frameworks

import poc.frameworks.model.external.User

/**
  * Created by arunavas on 12/10/17.
  */
package object validation {

  private[validation] type Val[A] = (A => List[ValidationError])

  private[validation] case class ValidationError(field: List[String], message: String) {
    def prefix(name: String) = this.copy(field = name +: field)
  }

  private[validation] def validator[A](func: A => List[ValidationError]): Validator[A] = new Validator[A] {
    def apply(value: A) = func(value)
  }

  private[validation] trait Validator[A] extends Val[A] {
    def and(that: Validator[A]) = validator[A] { value =>
      this(value) ++ that(value)
    }

    def orElse(that: Validator[A]) = validator[A] { value =>
      this(value) match {
        case List() => that(value)
        case other => other
      }
    }
  }

  private[validation] def pass =
    List.empty[ValidationError]

  private[validation] def fail(message: String) =
    List(ValidationError(List.empty, message))

  private[validation] def cond[A](errorMsg: String)(f: A => Boolean) = validator[A] { value =>
    if (f(value)) pass else fail(errorMsg)
  }

  private[validation] def in[A](values: List[A]) = validator[A] { value =>
    if (values contains value) pass else fail(s"$value must be in $values")
  }

  private[validation] def nonEmpty = validator[String] { value =>
    if (value == null || value.isEmpty) fail("must not be empty") else pass
  }

  private[validation] def field[A, B](field: String, func: A => B)(implicit inner: Validator[B]) = validator[A] { value =>
    inner(func(value)).map(_ prefix field)
  }

  val userValidation = field("name", (user: User) => user.name) {
    nonEmpty
  } and field("mobile", (user: User) => user.mobile) {
    cond("Mobile number can only be 10 digits")(m => null != m && m.length == 10)
  } and field("address", (user: User) => user.address) {
    nonEmpty
  } and field("userName", (user: User) => user.userName) {
    nonEmpty
  } and field("password", (user: User) => user.password) {
    cond("Password cannot be less than 6 characters")(p => null != p && p.length >= 6)
  }
}
