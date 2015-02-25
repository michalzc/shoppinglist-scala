package michalz.shoppinglist.services

import akka.actor.Props
import michalz.shoppinglist.repository.mongo.{MongoProvider, MongoSupport}
import michalz.shoppinglist.routes.{Api, JsonSupport, RoutingSupport, Static}
import spray.routing.HttpServiceActor


class ShoppingListHttpService(mongo: MongoProvider) extends HttpServiceActor with Api with Static with RoutingSupport with MongoSupport with JsonSupport {

  def mongoProvider: MongoProvider = mongo

  def receive: Receive = runRoute(staticRoute ~ apiRouting)
}

object ShoppingListHttpService {
  def props(mongoProvider: MongoProvider) = Props(new ShoppingListHttpService(mongoProvider))
}
