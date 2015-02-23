package michalz.shoppinglist.domain

import reactivemongo.bson.{BSONDocument, BSONDocumentReader}

/**
 * @author mzajac
 * @since 2015.02.20
 */
case class ShoppingListItem(name: String, completed: Boolean)
