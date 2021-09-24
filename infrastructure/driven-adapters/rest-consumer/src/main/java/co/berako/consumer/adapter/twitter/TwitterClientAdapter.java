package co.berako.consumer.adapter.twitter;

import co.berako.model.twitter.TweetsTerms;
import co.berako.model.twitter.gateways.TwitterClientRepository;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Slf4j
@Repository
public class TwitterClientAdapter implements TwitterClientRepository {

    private final BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);

    @Value("$twitter.credentials.consumerKey")
    private String consumerKey;
    @Value("$twitter.credentials.consumerSecret")
    private String consumerSecret;
    @Value("$twitter.credentials.token")
    private String token;
    @Value("$twitter.credentials.secret")
    private String secret;

    @Override
    public Mono<Boolean> getTweets(TweetsTerms tweetsTerms) {
        return Mono.fromCallable(() -> {
            Client client = createTwitterClient(tweetsTerms.getTerms(), msgQueue);
            client.connect();

            while (!client.isDone()) {
                String msg = null;

                try {
                    msg = msgQueue.poll(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    client.stop();
                }

                if (nonNull(msg))
                    log.info(msg);
            }

            return Boolean.TRUE;
        });
    }

    private Client createTwitterClient(List<String> terms, BlockingQueue<String> msgQueue) {

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);

        return new ClientBuilder()
                .name("Hosebird-Client-01")
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue))
                .build();
    }
}
