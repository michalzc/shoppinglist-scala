package michalz.whattobuy.services

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.pipe
import michalz.whattobuy.domain.ShoppingListMessages.{FindAll, ShoppingLists}
import michalz.whattobuy.repository.mongo.{MongoProvider, MongoShoppingListRepository}

/**
 * @author mzajac
 * @since 2015.02.20
 */
class ShoppingListRepositoryActor(mongoProvider: MongoProvider) extends Actor with MongoShoppingListRepository with ActorLogging {

  def mongoCollection = mongoProvider.getCollection("shoppingLists")

  implicit val executionContext = context.dispatcher

  def receive = {
    case FindAll =>
      log.info("Recived request from all lists")
      findAll map ShoppingLists pipeTo sender
      log.info("Result piped to sender")
  }

  log.debug("shoppingListsRepository created")
}

object ShoppingListRepositoryActor {
  def props(mongoProvider: MongoProvider) = Props(new ShoppingListRepositoryActor(mongoProvider))
}
