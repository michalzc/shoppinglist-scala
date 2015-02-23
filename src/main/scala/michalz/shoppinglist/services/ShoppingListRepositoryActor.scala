package michalz.shoppinglist.services

import akka.actor.{Props, ActorLogging, Actor}
import akka.pattern.pipe
import michalz.shoppinglist.domain.ShoppingListMessages.{ShoppingLists, FindAll}
import michalz.shoppinglist.repository.mongo.{MongoShoppingListRepository, MongoProvider}

import scala.concurrent.Future

/**
 * @author mzajac
 * @since 2015.02.20
 */
class ShoppingListRepositoryActor(mongoProvider: MongoProvider) extends Actor with MongoShoppingListRepository with ActorLogging {

  def mongoCollection = mongoProvider.getCollection("shoppingLists")

  implicit val executionContext = context.dispatcher

  def receive = {
    case FindAll => {
      log.info("Recived request from all lists")
      findAll map (ShoppingLists) pipeTo sender
      log.info("Result piped to sender")
    }
  }

  log.debug("shoppingListsRepository created")
}

object ShoppingListRepositoryActor {
  def props(mongoProvider: MongoProvider) = Props(new ShoppingListRepositoryActor(mongoProvider))
}
