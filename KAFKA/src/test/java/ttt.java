import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class ttt {
    public static void main(String[] args) throws Exception {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.2:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaProducer<String,String> producer = new KafkaProducer<>(configs);
        for (int i=0;i<1;i++){
            producer.send(new ProducerRecord<>("topicttt", ",v,804,01-01011674993-01,00000001000000010000000121047080,2021-08-29 23:59:03,2021-08-26 00:00:00,597,,tuichuyecainixihuan,0,,2,6,1,00000001000000100000000000231906,退出页猜你喜欢,,,u,86400,,,,00000001000000010000000121047080,0,3.1.3.1-U,8888,101,0999,2021-08-25 23:50:03,,,,,,,,"), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(exception == null){
                        System.out.println("发送成功："+metadata.partition());//数据所在分区
                        System.out.println("发送成功："+metadata.topic());//数据所对应的topic
                        System.out.println("发送成功："+metadata.offset());//数据的offset
                    }
                }
            });
        }

        producer.close();
    }

}
