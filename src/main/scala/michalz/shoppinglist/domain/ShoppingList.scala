package michalz.shoppinglist.domain

case class ShoppingList(id: String, name: String, items: Option[List[ShoppingListItem]])
