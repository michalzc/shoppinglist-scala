package michalz.shoppinglist.repository

import michalz.shoppinglist.domain.ShoppingList

import scala.collection.immutable.List
import scala.concurrent.Future
/**
 * Created by michal on 18.02.15.
 */

trait ShoppingListRepository {
  def findAll: Future[List[ShoppingList]]
}


