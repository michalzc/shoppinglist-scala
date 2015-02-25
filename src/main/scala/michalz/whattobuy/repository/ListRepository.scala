package michalz.whattobuy.repository

import michalz.whattobuy.domain.ShoppingList

import scala.collection.immutable.List
import scala.concurrent.Future
/**
 * Created by michal on 18.02.15.
 */

trait ShoppingListRepository {
  def findAll: Future[List[ShoppingList]]
}


