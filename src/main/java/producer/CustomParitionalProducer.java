package producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import producer.partitioner.CustomPartitional;

/*
 * 프로듀서 사용환경에 따라 특정 데이터를 가지는 레코드를 특정 파티션에 보내야 할 경우가 생긴다.
 * 예를 들어 doodoo라는 값을 가진 메시지 키는 0번 파티션으로 들어가야한다고 가정해보자. 
 * 기본 설정 파티셔너를 사용할 경우는 메시지 키 해시값으로 파티션에 매칭하여 해당 파티션에 레코드가 전송되어 어느 파티션에 들어간지 모른다.
 * 이때 Partitioner 인터페이스를 사용하여 사용자 정의 파티셔너를 생성하면 doodoo라는 키에 대해서 무조건 파티션 0번으로 지정하도록 설정할 수 있다.
 * 이렇게 지정할 경우 토픽의 파티션 개수가 변경되더라도 doodoo라는 메시지 키를 가진 데이터는 파티션 0번에 적재된다.
 */
public class CustomParitionalProducer {
	
	private final static String TOPIC_NAME = "test";	//  필수 
	private final static String BOOTSTRAP_SERVERS  = "my-kafka:9092";  // 필수

	public static void main(String[] args) {
		
		Properties configs = new Properties();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// 커스텀파티셔너 셋
		configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitional.class);
		
		
		KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
		
		
		ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "doodoo", "hi this value determined 0 partition");
		producer.send(record);
		
		producer.flush();
		producer.close();

	}

}
