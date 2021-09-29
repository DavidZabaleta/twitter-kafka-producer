package co.berako.events.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Mono;

import java.util.Properties;
import java.util.concurrent.Future;

import static java.util.Objects.nonNull;

@Slf4j
public abstract class KafkaProducerTemplate<K, V> {

    //@Value(value = "${spring.kafka.bootstrap-servers}")
    private static String bootstrapServers = "127.0.0.1:9092";

    private final KafkaProducer<K, V> kafkaProducer;

    private Properties properties;

    public KafkaProducerTemplate() {
        createProducerProperties(bootstrapServers);
        this.kafkaProducer = new KafkaProducer<>(properties);
    }

    private void createProducerProperties(String bootstrapServers) {
        properties = new Properties();

        /*Basic Producer properties*/
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /*Safe Producer properties*/
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        /*High throughput Producer properties*/
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "10");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
    }

    protected Mono<Boolean> closeFlushing() {
        return Mono.fromCallable(() -> {
            try {
                kafkaProducer.flush();
                kafkaProducer.close();
                return Boolean.TRUE;
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        });
    }

    protected Mono<Future<RecordMetadata>> sendRecord(ProducerRecord<K, V> record) {
        return Mono.fromCallable(() -> kafkaProducer
                .send(record, (recordMetadata, e) -> {
                    if (nonNull(e))
                        log.error("Error while producing the message: ", e);

                    log.info("Received new metadata. \n"
                            .concat(String.format("Topic: %s \n", recordMetadata.topic()))
                            .concat(String.format("Partition: %s \n", recordMetadata.partition()))
                            .concat(String.format("Offset: %s \n", recordMetadata.offset()))
                            .concat(String.format("Timestamp: %s \n", recordMetadata.timestamp()))
                    );
                }));
    }
}
