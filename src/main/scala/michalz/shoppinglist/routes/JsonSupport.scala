package michalz.shoppinglist.routes

import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import spray.httpx.Json4sJacksonSupport

/**
 * Created by michal on 20.02.15.
 */
trait JsonSupport extends Json4sJacksonSupport {
  implicit def json4sJacksonFormats = Serialization.formats(NoTypeHints)
}
