package michalz.shoppinglist

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import michalz.shoppinglist.repository.MongoProvider
import michalz.shoppinglist.services.ShoppingListHttpService
import spray.can.Http

import scala.concurrent.duration._

object ShoppingListApp extends App {
  implicit val system = ActorSystem("ShoppingListActorSystem")
  implicit val timeout = Timeout(5.seconds)

  val mongoProvider = new MongoProvider

  val shoppingListHttpService: ActorRef = system.actorOf(ShoppingListHttpService.props(mongoProvider), "shoppingListHttpService")


  IO(Http) ? Http.Bind(shoppingListHttpService, interface = "0.0.0.0", port = 8000)
  sys.addShutdownHook(system.shutdown)
}

// vim: set ts=4 sw=4 et:
