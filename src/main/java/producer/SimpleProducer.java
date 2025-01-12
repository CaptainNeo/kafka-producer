package producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *  프로듀서를 안전하게 종료하기 위해서는 close() 메서드를 사용하여 어큐뮤레이터에 저장되어 있는 모든 데이터를 카프카 클러스터로 전송해야 한다.
 */
public class SimpleProducer {
	
	private final static Logger logger = LoggerFactory.getLogger(SimpleProducer.class);
	private final static String TOPIC_NAME = "test";	//  필수 
	private final static String BOOTSTRAP_SERVERS  = "my-kafka:9092";  // 필수

	public static void main(String[] args) {
		
		Properties configs = new Properties();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
		
		String messageStr = "doodooMessage3";
		
		ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageStr);
		producer.send(record);
		logger.info("{}", record);
		producer.flush();
		producer.close();
		

	}

}
