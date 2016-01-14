package kafka;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import prop.PropertiesStack;

public class KafkaConsumerObject extends KafkaConsumer<String, String> {

	private static Properties props = new Properties();

	static {
		props.put("bootstrap.servers", PropertiesStack.getKafkaBootstrapServers());
		props.put("group.id", PropertiesStack.getKafkaGroupId());
		// props.put("enable.auto.commit", "true");
		// props.put("auto.commit.interval.ms", "1000");
		// props.put("session.timeout.ms", "30000");
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		//props.put("partition.assignment.strategy", "range");
		props.put("auto.offset.reset", "earliest");
	}

	public KafkaConsumerObject() {
		super(props);
	}

	public static void main(String[] args) throws Exception {
		KafkaConsumerObject consumer = new KafkaConsumerObject();
		consumer.subscribe(Arrays.asList(PropertiesStack.getKafkaTopic()));
		ConsumerRecords<String, String> records = consumer.poll(1000);
		System.out.println(records.count());
		// System.out.println(records.keySet());
		for (ConsumerRecord<String, String> record : records)
			System.out.printf("offset = %d, key = %s, value = %s",
					record.offset(), record.key(), record.value());

		consumer.close();
	}

}
