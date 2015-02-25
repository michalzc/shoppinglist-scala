package michalz.whattobuy.routes

import akka.actor.Actor
import akka.util.Timeout
import spray.http.StatusCodes._
import spray.routing.RequestContext
import spray.util.LoggingContext

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


/**
 * Created by michal on 20.02.15.
 */
trait RoutingSupport { this: Actor =>

  implicit def executionContext: ExecutionContext = context.dispatcher

  implicit val tiemout = Timeout(5.seconds)

  def logAndFail(ctx: RequestContext, e: Throwable)(implicit log: LoggingContext): Unit = {
    log.error(e, "Can't complete request: {}", ctx.request)
    ctx.complete(InternalServerError)
  }
}
