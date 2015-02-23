package michalz.shoppinglist.repository.mongo.serialization

import michalz.shoppinglist.domain.ShoppingListItem
import reactivemongo.bson.{BSONDocument, BSONDocumentReader}

object ShoppingListItemSerializer {
  implicit object ShoppingListItemReader extends BSONDocumentReader[ShoppingListItem] {
    def read(doc: BSONDocument): ShoppingListItem = {
      val name = doc.getAs[String]("name").get
      val completed = doc.getAs[Boolean]("completed").get
      ShoppingListItem(name, completed)
    }
  }
}
