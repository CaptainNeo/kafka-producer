package producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 메시지 키가 포함된 레코드를 전송하고 싶다면 ProducerRecord 생성 시 파라미터로 추가해야 한다. 토픽이름, 메시지 키, 메시지 값을 
 * 순서대로 파라미터로 넣고 생성했을 경우 메시지 키가 지정된다.
 * 
 */
public class KeyProducer {
	
	private final static String TOPIC_NAME = "test";	//  필수 
	private final static String BOOTSTRAP_SERVERS  = "my-kafka:9092";  // 필수

	public static void main(String[] args) {
		
		Properties configs = new Properties();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
		
		ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "doodookey1", "hi kafka");
		producer.send(record);
		ProducerRecord<String, String> record2 = new ProducerRecord<>(TOPIC_NAME, "doodookey2", "hi kafka");
		producer.send(record);
		
		producer.flush();
		producer.close();


	}

}

