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
import com.typesafe.config.ConfigFactory
import java.util.concurrent.atomic.AtomicInteger

//#frontend
class MapReduceFrontend extends Actor {

  var backends = IndexedSeq.empty[ActorRef]
  var jobCounter = 0

  def receive = {
    case job: MapReduceJob if backends.isEmpty =>
      sender() ! JobFailed("Service unavailable, try again later", job)

    case job: MapReduceJob =>
      jobCounter += 1
      backends(jobCounter % backends.size) forward job

	// when a new backend node gets created
	// the new backend node notifies the front end nodes 
	// so they can send the backend nodes work
    case BackendRegistration if !backends.contains(sender()) =>
      context watch sender()
      backends = backends :+ sender()

    case Terminated(a) =>
      backends = backends.filterNot(_ == a)
  }
}
//#frontend

object MapReduceFrontend {
  def main(args: Array[String]): Unit = {

    val port = if (args.isEmpty) "0" else args(0)
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
      withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]")).
      withFallback(ConfigFactory.load())

    val system = ActorSystem("ClusterSystem", config)
    val frontend = system.actorOf(Props[MapReduceFrontend], name = "frontend")

    val counter = new AtomicInteger
    
    
    
    import system.dispatcher
    system.scheduler.schedule(2.seconds, 2.seconds) {
      implicit val timeout = Timeout(5 seconds)
      (frontend ? MapReduceJob("hello-" + counter.incrementAndGet())) onSuccess {
        case result => println(result)
      }
    }

  }
}