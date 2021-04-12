package common.java.common;

import java.util.Random;

import com.rabbitmq.client.Channel;

public class AMQPSender {

	public static void main(String[] args) throws Exception {
		Channel channel = AMQPCommon.connect();

		int numMsgs = args.length > 0 ? new Integer(args[0]).intValue() : 1;
		for (int i=0; i<numMsgs; i++) {
			long shares = ((long) ((new Random().nextDouble() * 4000) + 1));
			String text = "name for customer id " + shares;
			byte[] message = text.getBytes();
			String routingKey = "trade.eq.q";
			System.out.println("get " + text);
			channel.basicPublish("", routingKey, null, message);
		}
		
		AMQPCommon.close(channel);
	}
}









