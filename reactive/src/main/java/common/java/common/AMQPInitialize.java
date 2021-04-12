package common.java.common;

import com.rabbitmq.client.Channel;

public class AMQPInitialize {

	public static void main(String[] args) throws Exception {
		Channel channel = AMQPCommon.connect();
		
		//create the durable exchanges
		channel.exchangeDeclare("flow.fx", "fanout", true);
		channel.exchangeDeclare("orders.dx", "direct", true);
		System.out.println("exchanges created.");

		//create the durable queues
		channel.queueDeclare("trade.request.q", true, false, false, null);
		channel.queueDeclare("trade.response.q", true, false, false, null);
		channel.queueDeclare("trade.validation.request.q", true, false, false, null);
		channel.queueDeclare("trade.validation.response.q", true, false, false, null);
		channel.queueDeclare("config.q", true, false, false, null);
		channel.queueDeclare("flow.q", true, false, false, null);
		channel.queueDeclare("trade.eq.q", true, false, false, null);
		channel.queueDeclare("trade.1.q", true, false, false, null);
		channel.queueDeclare("trade.2.q", true, false, false, null);
		channel.queueDeclare("workflow.q", true, false, false, null);
		channel.queueDeclare("sync.q", true, false, false, null);
		channel.queueDeclare("name.q", true, false, false, null);
		channel.queueDeclare("datapump.q", true, false, false, null);
		channel.queueDeclare("product.q", true, false, false, null);
		channel.queueDeclare("wishlist.request.q", true, false, false, null);
		channel.queueDeclare("wishlist.response.q", true, false, false, null);
		System.out.println("queues created.");

		//create the bindings
		channel.queueBind("flow.q", "flow.fx", "");
		System.out.println("bindings created.");
		
		AMQPCommon.close(channel);
	}
}

