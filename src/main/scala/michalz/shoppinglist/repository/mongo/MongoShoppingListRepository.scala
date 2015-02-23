package michalz.shoppinglist.repository.mongo

import michalz.shoppinglist.domain.ShoppingList
import michalz.shoppinglist.repository.ShoppingListRepository
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext

/**
 * Created by michal on 23.02.15.
 */
trait MongoShoppingListRepository extends ShoppingListRepository {

  import michalz.shoppinglist.repository.mongo.serialization.ShoppingListSerializer.ShoppingListReader

  def mongoCollection: BSONCollection
  implicit val executionContext: ExecutionContext

  override def findAll = {
    mongoCollection.find(BSONDocument()).cursor[ShoppingList].collect[scala.List]()
  }
}
