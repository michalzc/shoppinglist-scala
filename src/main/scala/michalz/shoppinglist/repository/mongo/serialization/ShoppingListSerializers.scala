package michalz.shoppinglist.repository.mongo.serialization

import michalz.shoppinglist.domain.{ShoppingList, ShoppingListItem}
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONObjectID}

/**
 * @author mzajac
 * @since 2015.02.20
 */

object ShoppingListSerializer {
  import ShoppingListItemSerializer.ShoppingListItemReader

  implicit object ShoppingListReader extends BSONDocumentReader[ShoppingList] {

    def read(doc: BSONDocument): ShoppingList = {
      val id = doc.getAs[BSONObjectID]("_id").get.stringify
      val name = doc.getAs[String]("name").get
      val items = doc.getAs[List[ShoppingListItem]]("items")
      ShoppingList(id, name, items)
    }
  }

}
