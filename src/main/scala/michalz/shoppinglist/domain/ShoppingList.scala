package michalz.shoppinglist.domain

import org.slf4j.LoggerFactory
import reactivemongo.bson.{BSONObjectID, BSONArray, BSONDocument, BSONDocumentReader}

/**
 * @author mzajac
 * @since 2015.02.20
 */
case class ShoppingList(id: String, name: String, items: Option[List[ShoppingListItem]])

object ShoppingList {
  val log = LoggerFactory.getLogger(classOf[ShoppingList])

  implicit object ShoppingListReader extends BSONDocumentReader[ShoppingList] {

    import michalz.shoppinglist.domain.ShoppingListItem.ShoppingListItemReader

    def read(doc: BSONDocument): ShoppingList = {
      val id = doc.getAs[BSONObjectID]("_id").get.stringify
      val name = doc.getAs[String]("name").get
      val items = doc.getAs[List[ShoppingListItem]]("items")
      ShoppingList(id, name, items)
    }
  }

}
