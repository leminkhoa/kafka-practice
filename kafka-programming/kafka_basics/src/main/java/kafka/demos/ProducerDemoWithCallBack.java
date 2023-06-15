package kafka.demos;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.Callback;
// import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;


public class ProducerDemoWithCallBack {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallBack.class.getSimpleName());

    public static void main(String[] args) {
        log.info("I am a Kafka Producer!");

        // create Properties
        Properties properties = new Properties();
        
        // define Properties - connect to Localhost
        properties.setProperty("bootstrap.servers", "127.0.0.1:9093");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("batch.size", "400"); /* 400 KB, not 400 records */
        // properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName())

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        

        for (int j=0; j<10; j++){
            for (int i=0; i<30; i++){
                // create a Producer record
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java","HelloWorld " + i);

                // send data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        // executes every time a record successfully sent or an exception is thrown
                        if (e == null) {
                            // the record was successfully sent
                            log.info("Received new data \n" +
                                    "Topic: " + metadata.topic() + "\n" +
                                    "Partition: " + metadata.partition() + "\n" +
                                    "Offset: " + metadata.offset() + "\n" +
                                    "Timestamp: " + metadata.timestamp() );
                        } else {
                            log.error("Error while producing ", e);
                        }
                    }
                });
            }
        
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }    
        }

    

        // tell the producer to send all data and block until done --synchronous
        producer.flush();

        // flush and close the producer
        producer.close();
    }
}
