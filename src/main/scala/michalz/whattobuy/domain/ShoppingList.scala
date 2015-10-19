package michalz.whattobuy.domain

case class ShoppingList(id: Option[String], name: String, items: Option[List[ShoppingListItem]]) {
  require(name != null, "Name can not be null")
}
