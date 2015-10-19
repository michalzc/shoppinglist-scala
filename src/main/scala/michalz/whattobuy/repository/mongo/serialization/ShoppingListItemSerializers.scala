package michalz.whattobuy.repository.mongo.serialization

import michalz.whattobuy.domain.ShoppingListItem
import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader}

object ShoppingListItemSerializer {
  implicit object ShoppingListItemReader extends BSONDocumentReader[ShoppingListItem] {
    def read(doc: BSONDocument): ShoppingListItem = {
      val name = doc.getAs[String]("name").get
      val completed = doc.getAs[Boolean]("completed").get
      ShoppingListItem(name, completed)
    }
  }

  implicit object ShoppingListItemWriter extends BSONDocumentWriter[ShoppingListItem] {
    override def write(t: ShoppingListItem): BSONDocument = {
      BSONDocument(
        "name" -> t.name,
        "completed" -> t.completed
      )
    }
  }
}
