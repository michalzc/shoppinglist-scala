package michalz.shoppinglist.repository.mongo

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.default.BSONCollection

import scala.collection.JavaConversions._

/**
 * @author mzajac
 * @since 2015.02.20
 */
class MongoProvider(implicit system: ActorSystem) {
  import system.dispatcher

  private val config: Config = ConfigFactory.load()
  private val driver = new MongoDriver(system)
  private val connection = driver.connection(config.getStringList("shoppinglist.mongo.hosts"))
  private val dataBase = connection(config.getString("shoppinglist.mongo.database"))

  def getCollection(name: String): BSONCollection = dataBase.collection(name)
}
