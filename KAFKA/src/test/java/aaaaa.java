import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class aaaaa {
    public static void main(String[] args) throws InterruptedException {
        //1. 初始化配置信息
        Map<String,Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.8:9092");
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        map.put(ConsumerConfig.GROUP_ID_CONFIG,"g1");
        //2. 创建Consumer
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer(map);
        //订阅 topic-user的数据
        kafkaConsumer.subscribe(Arrays.asList("liu666"));

        while (true){
            //3. 消费数据
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
            Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);

                for (int i = 0; i < 100; i++) {
                    System.out.println("i = " + i);
                    i++;
                }
                Thread.sleep(8000);

            }
        }

    }
}
