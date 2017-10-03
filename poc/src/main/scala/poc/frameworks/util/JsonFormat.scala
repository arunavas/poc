package poc.frameworks.util

import java.util.{Date, TimeZone}

import net.liftweb.json._

/**
  * Created by arunavas on 13/12/16.
  */
object JsonFormat {

  object ContactzDateFormats extends Formats {
    import java.text.{ParseException, SimpleDateFormat}

    override val typeHints: TypeHints = NoTypeHints

    val dateFormat = new DateFormat {
      def parse(s: String) = try {
        Some(formatter.parse(s))
      } catch {
        case e: ParseException => None
      }

      def format(d: Date) = formatter.format(d)

      private def formatter = {
        val f = dateFormatter
        f.setTimeZone(TimeZone.getTimeZone("IST"))
        f
      }
    }

    protected def dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  }

  val formats = ContactzDateFormats
}
