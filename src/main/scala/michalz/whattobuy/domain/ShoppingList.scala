package michalz.whattobuy.domain

case class ShoppingList(id: String, name: String, items: Option[List[ShoppingListItem]])
