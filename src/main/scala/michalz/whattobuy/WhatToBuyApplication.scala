package michalz.whattobuy

import akka.actor.{ActorRef, ActorSystem}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import michalz.whattobuy.repository.mongo.MongoProvider
import michalz.whattobuy.services.WhatToBuyHttpService
import org.slf4j.LoggerFactory
import spray.can.Http

import scala.concurrent.duration._

object WhatToBuyApplication extends App {
  val logger = LoggerFactory.getLogger(this.getClass)
  implicit val system = ActorSystem("ShoppingListActorSystem")
  implicit val timeout = Timeout(5.seconds)

  val mongoProvider = new MongoProvider

  val shoppingListHttpService: ActorRef = system.actorOf(WhatToBuyHttpService.props(mongoProvider), "shoppingListHttpService")


  logger.info("Starting application")
  IO(Http) ? Http.Bind(shoppingListHttpService, interface = "0.0.0.0", port = 8000)

  sys.addShutdownHook({
    system.shutdown
  })
}

// vim: set ts=4 sw=4 et:
