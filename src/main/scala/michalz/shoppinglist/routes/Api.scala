package michalz.shoppinglist.routes

import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import michalz.shoppinglist.domain.ShoppingListMessages.{FindAll, ShoppingLists}
import michalz.shoppinglist.repository.mongo.MongoSupport
import michalz.shoppinglist.services.ShoppingListRepositoryActor
import spray.routing.HttpService

import scala.util.{Failure, Success}
import scala.concurrent.duration._

/**
 * Created by michal on 20.02.15.
 */
trait Api { this: Actor with HttpService with MongoSupport with JsonSupport =>

  val listRepositoryActorRef = context.actorOf(ShoppingListRepositoryActor.props(mongoProvider), "listsRepository")

  implicit val timeout = Timeout(5.seconds)
  implicit val ec = context.dispatcher

  val apiRouting = {
    pathPrefix("api") {
      path("shoppinglist") {
        get {
          complete{
            ask(listRepositoryActorRef, FindAll).mapTo[ShoppingLists].map {
              result => result.lists
            }
          }
        }
      } ~ pathEndOrSingleSlash {
        complete("There will be api!")
      }
    }
  }
}
