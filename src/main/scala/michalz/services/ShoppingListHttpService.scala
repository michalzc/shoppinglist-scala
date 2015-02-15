package michalz.services

import akka.actor.ActorLogging
import spray.routing.HttpServiceActor

/**
 * Created by michal on 15.02.15.
 */
class ShoppingListHttpService extends HttpServiceActor with ActorLogging {

  lazy val route = {
    pathPrefix("api") {
      pathEndOrSingleSlash {
        complete("There will be api!")
      }
    } ~
    pathEnd {
      get {
        getFromResource("index.html")
      }
    }
  }

  def receive = runRoute(route)

}
