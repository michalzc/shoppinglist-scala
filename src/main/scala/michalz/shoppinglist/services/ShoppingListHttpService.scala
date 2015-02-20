package michalz.shoppinglist.services

import akka.actor.{ActorLogging, Props}
import akka.pattern.ask
import akka.util.Timeout
import michalz.shoppinglist.domain.ShoppingList
import michalz.shoppinglist.domain.ShoppingListMessages.{ShoppingLists, FindAll}
import michalz.shoppinglist.repository.MongoProvider
import org.json4s.{NoTypeHints, Formats}
import org.json4s.jackson.Serialization
import spray.httpx.Json4sJacksonSupport
import spray.routing.{RequestContext, HttpServiceActor}
import spray.http.StatusCodes._
import spray.util.LoggingContext
import scala.util.{Failure, Success}

import scala.concurrent.duration._

/**
 * Created by michal on 15.02.15.
 */
class ShoppingListHttpService(mongoProvider: MongoProvider) extends HttpServiceActor with Json4sJacksonSupport {

  val listRepositoryActorRef = context.actorOf(ShoppingListRepositoryActor.props(mongoProvider), "listsRepository")

  implicit val timeout = Timeout(5.seconds)
  implicit val executionContext = context.system.dispatcher

  implicit def json4sJacksonFormats = Serialization.formats(NoTypeHints)

  lazy val route = {
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
    } ~ pathPrefix("static") {
      //      getFromResourceDirectory("META-INF/resources/webjars") ~
      getFromResourceDirectory("static")
    } ~ pathEndOrSingleSlash {
      getFromResource("index.html")
    }
  }

  def receive = runRoute(route)

  def logAndFail(ctx: RequestContext, e: Throwable)(implicit log: LoggingContext): Unit = {
    log.error(e, "Can't complete request: {}", ctx.request)
    ctx.complete(InternalServerError)
  }
}

object ShoppingListHttpService {
  def props(mongoProvider: MongoProvider) = Props(new ShoppingListHttpService(mongoProvider))
}
