package michalz.whattobuy.routes

import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import michalz.whattobuy.domain.ShoppingList
import michalz.whattobuy.domain.ShoppingListMessages.{SaveOne, FindAll, FindOne}
import michalz.whattobuy.repository.mongo.MongoSupport
import michalz.whattobuy.services.ShoppingListRepositoryActor
import spray.routing.HttpService

import scala.concurrent.duration._

/**
 * Created by michal on 20.02.15.
 */
trait Api {
  this: Actor with HttpService with MongoSupport with JsonSupport =>

  val listRepositoryActorRef = context.actorOf(ShoppingListRepositoryActor.props(mongoProvider), "listsRepository")

  implicit val timeout = Timeout(5.seconds)
  implicit val ec = context.dispatcher

  val apiRouting = pathPrefix("api") {

    pathPrefix("shoppinglist") {
      pathEndOrSingleSlash {
        get {
          complete {
            ask(listRepositoryActorRef, FindAll).mapTo[List[ShoppingList]]
          }
        } ~
        post {
          entity(as[ShoppingList]) { shoppingList =>
            complete {
              ask(listRepositoryActorRef, SaveOne(shoppingList)).mapTo[ShoppingList]
            }
          }
        }
      } ~
      path(Segment) { listId =>
        get {
          complete {
            ask(listRepositoryActorRef, FindOne(listId)).mapTo[Option[ShoppingList]]
          }
        }
      }
    } ~
    pathEndOrSingleSlash {
      complete("There will be api!")
    }
  }
}
