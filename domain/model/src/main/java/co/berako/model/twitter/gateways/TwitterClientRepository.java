package co.berako.model.twitter.gateways;

import co.berako.model.twitter.TweetsTerms;
import reactor.core.publisher.Mono;

public interface TwitterClientRepository {
    Mono<Boolean> getTweets(TweetsTerms tweetsTerms);
}
