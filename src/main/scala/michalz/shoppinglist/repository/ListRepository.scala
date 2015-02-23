package michalz.shoppinglist.repository

import michalz.shoppinglist.domain.ShoppingList
import reactivemongo.bson.BSONDocument

import scala.collection.immutable.List
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.collections.default.BSONCollection
/**
 * Created by michal on 18.02.15.
 */

trait ShoppingListRepository {
  def findAll: Future[List[ShoppingList]]
}


