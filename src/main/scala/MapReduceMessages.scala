package MapReduce

//#messages
final case class MapReduceJob(text: String)
final case class MapReducer(Map: () => String, Reduce: () => String, Content: List[String])
final case class TransformationResult(text: String)
final case class JobFailed(reason: String, job: MapReduceJob)
case object BackendRegistration
//#messages 
