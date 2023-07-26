import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Example {
    KafkaProducer<String, String> producer;
    public Example() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ExampleProducerInterceptor.class.getName());

        producer = new KafkaProducer<>(props);
        String topic = "kafka-interceptors";

        for (int i = 0; i < 1000000; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key-" + i, "value-" + i);
            producer.send(record);
        }
    }

    public static void main(String[] args) {
        new Example();
    }
}
