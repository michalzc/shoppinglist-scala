package michalz.shoppinglist.services

import akka.actor.{Props, ActorLogging, Actor}
import akka.pattern.pipe
import michalz.shoppinglist.domain.ShoppingListMessages.{ShoppingLists, FindAll}
import michalz.shoppinglist.repository.{MongoProvider, MongoShoppingListRepository}

import scala.concurrent.Future

/**
 * @author mzajac
 * @since 2015.02.20
 */
class ShoppingListRepositoryActor(mongoProvider: MongoProvider) extends Actor with MongoShoppingListRepository with ActorLogging {

  def mongoCollection = mongoProvider.getCollection("shoppingLists")

  implicit val executionContext = context.system.dispatcher

  def receive = {
    case FindAll => {
      log.info("Received request for all lists")
      val future: Future[ShoppingLists] = findAll map (ShoppingLists)
      log.info("I have a future: {}", future)
      future pipeTo sender
    }
  }

  log.debug("shoppingListsRepository created")
}

object ShoppingListRepositoryActor {
  def props(mongoProvider: MongoProvider) = Props(new ShoppingListRepositoryActor(mongoProvider))
}
