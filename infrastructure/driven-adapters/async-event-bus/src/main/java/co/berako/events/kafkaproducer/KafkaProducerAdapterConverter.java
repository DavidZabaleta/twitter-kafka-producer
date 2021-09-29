package co.berako.events.kafkaproducer;

import co.berako.model.events.Event;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerAdapterConverter {
    private KafkaProducerAdapterConverter() {
    }

    public static <T> ProducerRecord<String, String> buildProducerRecord(Event<T> event) {
        return new ProducerRecord<String, String>(event.key(), new Gson().toJson(event.getValue()));
    }
}
