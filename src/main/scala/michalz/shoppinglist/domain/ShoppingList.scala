package michalz.shoppinglist.domain

import reactivemongo.bson.{BSONDocument, BSONDocumentReader}

/**
 * @author mzajac
 * @since 2015.02.20
 */
case class ShoppingList(id: String, name: String, items: List[ShoppingListItem])

object ShoppingList {
  implicit object ShoppingListReader extends BSONDocumentReader[ShoppingList] {
    def read(doc: BSONDocument): ShoppingList = {
      val id = doc.getAs[String]("_id").get
      val name = doc.getAs[String]("name").get
      val items = doc.getAs[List[ShoppingListItem]]("items").get
      
      ShoppingList(id, name, items)
    }
  }
}