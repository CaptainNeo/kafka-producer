package producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/*
 * 레코드에 파티션 번호를 직접 지정하고 싶다면 토픽 이름, 파티션 번호, 메시지 키, 메시지 값을 순서대로 파라미터로 넣고 생성한다.
 * 파티션 번호는 토픽에 존재하는 파티션 번호로 설정해야 한다.
 */
public class ProducerConPartitonNum {
	
	private final static String TOPIC_NAME = "test";	//  필수 
	private final static String BOOTSTRAP_SERVERS  = "my-kafka:9092";  // 필수

	public static void main(String[] args) {
		
		Properties configs = new Properties();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
		
		int partitionNo = 0;
		
		ProducerRecord<String, String> record = 
				new ProducerRecord<>(TOPIC_NAME, partitionNo, "goPartition0", "this value go to no 0 partition");
		producer.send(record);
		
		producer.flush();
		producer.close();

	}

}

