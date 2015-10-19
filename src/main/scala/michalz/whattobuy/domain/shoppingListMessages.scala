package michalz.whattobuy.domain


/**
 * Created by michal on 20.02.15.
 */

object ShoppingListMessages {

  object FindAll
  case class FindOne(listId: String)
  case class SaveOne(shoppingList: ShoppingList)
}
