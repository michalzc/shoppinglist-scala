package michalz.shoppinglist.domain

import reactivemongo.bson.{BSONDocument, BSONDocumentReader}

/**
 * @author mzajac
 * @since 2015.02.20
 */
case class ShoppingListItem(name: String, completed: Boolean)

object ShoppingListItem {
  implicit object ShoppingListItemReader extends BSONDocumentReader[ShoppingListItem] {
    def read(doc: BSONDocument): ShoppingListItem = {
      val name = doc.getAs[String]("name").get
      val completed = doc.getAs[Boolean]("completed").get
      ShoppingListItem(name, completed)
    }
  }
}