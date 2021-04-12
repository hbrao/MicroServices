package monitor.java.monitor;

import com.rabbitmq.client.Channel;
import common.AMQPCommon;

public class AMQPMonitor {

	public static void main(String[] args) {

		try {
			Channel channel = AMQPCommon.connect();			
			while (true) {
				long consumers = channel.consumerCount("trade.eq.q");
				long queueDepth = channel.messageCount("trade.eq.q");
				System.out.println("consumers: " + consumers + ", pending msgs:" + queueDepth);
				Thread.sleep(1000);
			}

//			DeclareOk queue = channel.queueDeclare("trade.eq.q", true, false, false, null);
//			long consumers = queue.getConsumerCount();
//			long queueDepth = queue.getMessageCount();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}





