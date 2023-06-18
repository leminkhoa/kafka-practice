package kafka.demos;

import java.net.URI;
import java.util.Properties;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import com.launchdarkly.eventsource.EventSource;

public class WikimediaChangesProducer {
    public static void main(String[] args) {
        
        String bootstrapServers = "127.0.0.1:9093";

         // create Properties
        Properties properties = new Properties();
        
        // define Properties - connect to Localhost
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers );
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        
        String topic = "wikimedia.recentchange";

        EventHandler eventHandler = TODO;
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();

        // start the producer in another thread
        eventSource.start();

    }
}