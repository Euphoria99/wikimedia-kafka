package dev.pavanbhat.Kafka_Producer_Wikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
//import com.launchdarkly.eventsource.background.BackgroundEventHandler;
//import com.launchdarkly.eventsource.background.BackgroundEventSource;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
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

        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);

        //Add headers
        Headers headers = new Headers.Builder()
                .add("User-Agent", "MyKafkaProducer/1.0 (yourname@example.com)") // Required
                .build();

        OkHttpClient client = new OkHttpClient();

        EventSource eventSource = new EventSource.Builder(eventHandler, URI.create(wikimediaApiUrl))
                .client(client)
                .headers(headers)
                .build();

        eventSource.start();

        LOGGER.info("Connected to Wikimedia stream with headers...");
        TimeUnit.MINUTES.sleep(10);
    }

}
