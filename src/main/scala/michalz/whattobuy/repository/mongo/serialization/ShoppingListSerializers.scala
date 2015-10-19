package michalz.whattobuy.repository.mongo.serialization

import michalz.whattobuy.domain.{ShoppingList, ShoppingListItem}
import michalz.whattobuy.repository.mongo.serialization.ShoppingListItemSerializer.ShoppingListItemWriter
import reactivemongo.bson._

/**
 * @author mzajac
 * @since 2015.02.20
 */

object ShoppingListSerializer {
  import ShoppingListItemSerializer.ShoppingListItemReader
  import ShoppingListItemSerializer.ShoppingListItemWriter

  implicit object ShoppingListReader extends BSONDocumentReader[ShoppingList] {

    def read(doc: BSONDocument): ShoppingList = {
      val id = doc.getAs[BSONObjectID]("_id").get.stringify
      val name = doc.getAs[String]("name").get
      val items = doc.getAs[List[ShoppingListItem]]("items")
      ShoppingList(Some(id), name, items)
    }
  }

  implicit object ShoppingListWriter extends BSONDocumentWriter[ShoppingList] {
    override def write(shoppingList: ShoppingList): BSONDocument = {
//      BSONDocument("name" -> shoppingList.name) ++
//        shoppingList.id.map( id => BSONDocument("_id" -> BSONObjectID(id))).getOrElse(BSONDocument()) ++
//        shoppingList.items.map(items => BSONDocument("items" -> items.map(BSON.write(_)))).getOrElse(BSONDocument())

      BSONDocument(
        "name" -> shoppingList.name,
        "_id" -> shoppingList.id.map(BSONObjectID(_)),
        "items" -> shoppingList.items
      )
    }

  }

}
