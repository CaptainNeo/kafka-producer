package producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import producer.partitioner.CustomPartitional;

/*
 * 레코드의 전송 결과를 확인할 수 있다 
 * KafkaProducer의 send() 메서드는 Future객체를 반환한다. 이 객체는 RecordMetadata의 비동기 결과를 표현하는 것으로 ProducerRecord가
 * 카프카 브로커에 정상적으로 적재되었는 지에 대한 데이터가 포함되어 있다.
 * get() 메서드를 사용하면 프로듀서로 보낸 데이터의 결과를 동기적으로 가져올 수 있다.
 * 
 * test-0@4 <== test 토픽에 0번 파티션에 4번 오프셋으로 데이터를 적재
 */
public class CheckRecordReturnProducer {
	private final static Logger logger = LoggerFactory.getLogger(SimpleProducer.class);
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
		
		
		ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "doodoo", "hi how are you3?");
		
		RecordMetadata metadata;
		try {
			metadata = producer.send(record).get();
			logger.info(metadata.toString());
			System.out.println(metadata.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			producer.flush();
			producer.close();
		}
		

	}

}
