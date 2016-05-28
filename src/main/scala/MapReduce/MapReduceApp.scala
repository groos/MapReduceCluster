package MapReduce

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.UnreachableMember;

/*
MapReduce Applications
counting the number of occurrences of words in a set of text files (the example used in Lecture 3)

computing the reverse index for proper names in a set of text files (what you did in Homework 3)

computing the number of incoming hyperlinks for each html file in a set of html files 
	(the first step in computing its PageRank)
*/


object MapReduceApp {
  def main(args: Array[String]): Unit = {
  
  	// args = [data text file, map function file, reduce function file, port]
  	
  
  	//startup some nodes
  	// TODO - update for length == 3 when that's set up
  	if (args.length == 0){
		// Frontend nodes will create the jobs and send them to the backend nodes
		MapReduceFrontend.main(Seq("2551").toArray)
		
		// Backend nodes will accept and perform jobs and output their results to the sender
		MapReduceBackend.main(Seq("2552").toArray)
  	} else {
  		MapReduceFrontend.main(Seq(args(3)).toArray)
  		MapReduceBackend.main(Seq(args(3)).toArray)
  	}

  	
  	// get reference to all front end nodes and send out job?
  	
  	var content = List[String]()
  	content = "file1.txt"::content
  	content = "file2.txt"::content
  	content = "file3.txt"::content
  }
}

