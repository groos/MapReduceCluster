package MapReduce

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props

object MapReduceApp {
  def main(args: Array[String]): Unit = {
  
  	//startup some nodes
  	MapReduceFrontend.main(Seq("2551").toArray)
  	MapReduceBackend.main(Seq("2552").toArray)
  	
  }
}

