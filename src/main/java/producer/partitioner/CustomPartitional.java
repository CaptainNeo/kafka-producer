package producer.partitioner;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;


/*
 * doodoo 메시지 키로 포함된경우 파티션을 0으로 가게 리턴 
 * 그외는 키해싱값으로 가게 함
 */

public class CustomPartitional implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

		if(keyBytes == null) {
			throw new InvalidRecordException("Nedd message key");
		}
		
		if(((String)key).equals("doodoo")) return 0;
		
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}


}
