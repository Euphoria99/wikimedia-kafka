package dev.pavanbhat.Kafka_Producer_Wikimedia;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    private final String wikimediaApiUrl;

    public WikimediaChangesProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${wikimedia.api.url}") String wikimediaApiUrl){
        this.kafkaTemplate = kafkaTemplate;
        this.wikimediaApiUrl = wikimediaApiUrl;
    }

    public void sendMessage() throws InterruptedException {

        String topic = "wikimedia_recentchange";

        //read real time stream data from wikimedia, we use event source
        BackgroundEventHandler eventHandler =  new WikimediaChangesHandler(kafkaTemplate, topic);
        URI uriUrl = URI.create(wikimediaApiUrl);
        EventSource.Builder esBuilder = new EventSource.Builder(uriUrl);
        BackgroundEventSource.Builder eventSource = new BackgroundEventSource.Builder(eventHandler,esBuilder);
        BackgroundEventSource source = eventSource.build();
        source.start();


        TimeUnit.MINUTES.sleep(10);
    }
}
