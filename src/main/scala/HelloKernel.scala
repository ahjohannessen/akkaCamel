import akka.actor.{ Actor, ActorSystem, Props }
import akka.kernel.Bootable
import akka.camel.{CamelExtension, CamelMessage, Consumer}


class Consumer1 extends Consumer {
  def endpointUri = "file:/Users/bardur/camel?delay=3000"
  def receive = {
    case msg: CamelMessage => println("received '%s'" format msg.bodyAs[String])
  }
}





case object Start
 
class HelloActor extends Actor {
  val worldActor = context.actorOf(Props[WorldActor])
 
  def receive = {
    case Start ⇒ worldActor ! "Hello"
    case message: String ⇒
      println("Received message '%s'" format message)
  }
}
 
class WorldActor extends Actor {
  def receive = {
    case message: String ⇒ sender ! (message.toUpperCase + " world!")
  }
}
 
class HelloKernel extends Bootable {
  val system = ActorSystem("hellokernel")
  val camel = CamelExtension(system)
  val camelContext = camel.context

 
  def startup() {
    val r = system.actorOf(Props[Consumer1])
  }
 
  def shutdown() {
    system.shutdown()
  }
}