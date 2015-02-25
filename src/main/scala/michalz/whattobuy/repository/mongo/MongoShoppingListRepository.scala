package michalz.whattobuy.repository.mongo

import michalz.whattobuy.domain.ShoppingList
import michalz.whattobuy.repository.ShoppingListRepository
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext

/**
 * Created by michal on 23.02.15.
 */
trait MongoShoppingListRepository extends ShoppingListRepository {

  import michalz.whattobuy.repository.mongo.serialization.ShoppingListSerializer.ShoppingListReader

  def mongoCollection: BSONCollection
  implicit val executionContext: ExecutionContext

  override def findAll = {
    mongoCollection.find(BSONDocument()).cursor[ShoppingList].collect[scala.List]()
  }
}
