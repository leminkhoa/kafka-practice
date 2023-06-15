package kafka.demos;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("hello world");

        // create Properties
        Properties properties = new Properties();
        
        // define Properties - connect to Localhost
        properties.setProperty("bootstrap.servers", "127.0.0.1:9093");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create a Producer record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java","HelloWorld");

        // send data
        producer.send(producerRecord);

        // tell the producer to send all data and block until done --synchronous
        producer.flush();

        // flush and close the producer
        producer.close();
    }
}