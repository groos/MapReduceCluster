package MapReduce

import language.postfixOps
import scala.concurrent.duration._
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Terminated
import akka.pattern.ask
import akka.util.Timeout
import akka.routing.ConsistentHashingPool
import akka.routing.Broadcast
import akka.cluster.routing._


class MapReduceRouterService extends Actor {

	val router =  context.system.actorOf(
    ClusterRouterPool(ConsistentHashingPool(0), ClusterRouterPoolSettings(
      totalInstances = 20, maxInstancesPerNode = 5,
      allowLocalRoutees = false, useRole = Some("compute"))).props(Props[MapReduceBackend]),
    name = "mapRouter")


	override def receive = {
		case msg:String =>
		println(msg)
		router ! Broadcast(msg)
		println("in router service")
	}
} 