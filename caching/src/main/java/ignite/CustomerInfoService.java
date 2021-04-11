package ignite;

import java.util.ArrayList;
import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import common.AMQPCommon;

public class CustomerInfoService {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		Channel channel = AMQPCommon.connect();
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume("name.q", true, consumer);

		TcpDiscoverySpi spi = new TcpDiscoverySpi();
	    TcpDiscoveryMulticastIpFinder finder = new TcpDiscoveryMulticastIpFinder();
	    List<String> addresses = new ArrayList<>();
	    addresses.add("127.0.0.1:47500..47505");
	    finder.setAddresses(addresses);
		spi.setIpFinder(finder);

		CacheConfiguration cfg = new CacheConfiguration("namesConfig");
		cfg.setCacheMode(CacheMode.REPLICATED);

		//remove
//		ClientConfiguration scfg = new ClientConfiguration().setAddresses("127.0.0.1:10800");
//		NearCacheConfiguration<String, String> mcfg = new NearCacheConfiguration<>();	
//		IgniteClient igniteClient = Ignition.startClient(scfg);
//	    IgniteCache<String, String> near_cache = igniteClient...getOrCreateCache(scfg, mcfg);	    
		
		IgniteConfiguration ic = new IgniteConfiguration();
	    ic.setClientMode(false);
	    ic.setDiscoverySpi(spi);
	    ic.setCacheConfiguration(cfg);		
  
		Ignite ignite = Ignition.start(ic);
	    IgniteCache<String, String> cache = ignite.getOrCreateCache("names");
	    
		if (args.length > 0 && args[0].equalsIgnoreCase("load")) {
	        cache.put("1", "Mark");
		}
		
		System.out.println("");
        System.out.println("(I) name in cache: " + cache.get("1"));

		try {
			while (true) {
				QueueingConsumer.Delivery msg = consumer.nextDelivery(1000);
				if (msg != null) {
					String data = new String(msg.getBody());
					System.out.println("");
					System.out.println("--> UPDATING NAME: " + data);
					System.out.println("--> DATAPUMP DATA...");
					System.out.println("");
					cache.put("1", new String(msg.getBody()));
					
					//now 'datapump' the data to the writer...
					byte[] message = msg.getBody();
					String routingKey = "datapump.q";
					channel.basicPublish("", routingKey, null, message);
				}
		        System.out.println("(I) name in cache: " + cache.get("1"));
			}			
		} finally {
	        ignite.close();
			AMQPCommon.close(channel);
		}
	}
}
