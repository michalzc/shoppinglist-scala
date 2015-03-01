package michalz.whattobuy.repository.mongo

import akka.actor.ActorLogging
import michalz.whattobuy.domain.ShoppingList
import michalz.whattobuy.repository.ShoppingListRepository
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONObjectID, BSONDocument}

import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Failure, Success}

/**
 * Created by michal on 23.02.15.
 */
trait MongoShoppingListRepository extends ShoppingListRepository {
  this: ActorLogging =>

  import michalz.whattobuy.repository.mongo.serialization.ShoppingListSerializer.ShoppingListReader

  def mongoCollection: BSONCollection
  implicit val executionContext: ExecutionContext

  override def findAll = {
    mongoCollection.find(BSONDocument()).cursor[ShoppingList].collect[scala.List]()
  }

  override def findById(id: String) = {
    BSONObjectID.parse(id) match {
      case Success(listId) => mongoCollection.find(BSONDocument("_id" -> listId)).one[ShoppingList]
      case Failure(exception) => {
        log.warning("Invalid list id: {}", exception)
        Future(None)
      }
    }
  }
}
