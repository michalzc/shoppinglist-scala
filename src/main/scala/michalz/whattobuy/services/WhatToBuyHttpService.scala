package michalz.whattobuy.services

import akka.actor.Props
import michalz.whattobuy.repository.mongo.{MongoProvider, MongoSupport}
import michalz.whattobuy.routes.{Api, JsonSupport, RoutingSupport, Static}
import spray.routing.HttpServiceActor


class WhatToBuyHttpService(mongo: MongoProvider) extends HttpServiceActor with Api with Static with RoutingSupport with MongoSupport with JsonSupport {

  def mongoProvider: MongoProvider = mongo

  def receive: Receive = runRoute(staticRoute ~ apiRouting)
}

object WhatToBuyHttpService {
  def props(mongoProvider: MongoProvider) = Props(new WhatToBuyHttpService(mongoProvider))
}
