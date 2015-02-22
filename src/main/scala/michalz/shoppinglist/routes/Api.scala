package michalz.shoppinglist.routes

import akka.actor.Actor
import akka.pattern.ask
import michalz.shoppinglist.domain.ShoppingListMessages.{FindAll, ShoppingLists}
import michalz.shoppinglist.repository.MongoSupport
import michalz.shoppinglist.services.ShoppingListRepositoryActor
import spray.routing.HttpService

import scala.util.{Failure, Success}

/**
 * Created by michal on 20.02.15.
 */
trait Api { this: Actor with HttpService with MongoSupport with JsonSupport with RoutingSupport =>

  val listRepositoryActorRef = context.actorOf(ShoppingListRepositoryActor.props(mongoProvider), "listsRepository")

  val apiRouting = {
    pathPrefix("api") {
      path("shoppinglist") {
        get { ctx =>
          (listRepositoryActorRef ? FindAll).mapTo[ShoppingLists].onComplete {
            case Success(resp) => {
              ctx.complete(resp.lists)
            }
            case Failure(e) => {
              logAndFail(ctx, e)
            }
          }
        }
      } ~ pathEndOrSingleSlash {
        complete("There will be api!")
      }
    }
  }
}
