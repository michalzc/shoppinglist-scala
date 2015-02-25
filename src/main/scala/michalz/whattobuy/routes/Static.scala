package michalz.whattobuy.routes

import spray.routing.HttpService

/**
 * Created by michal on 22.02.15.
 */
trait Static { this: HttpService =>

  val staticRoute = {
    pathPrefix("static") {
      getFromResourceDirectory("static")
    } ~ pathEndOrSingleSlash {
      getFromResource("index.html")
    }
  }

}
