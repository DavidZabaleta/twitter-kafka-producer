package co.berako.usecase.twitter;

import co.berako.model.twitter.TweetsTerms;
import co.berako.model.twitter.gateways.TwitterClientRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PublishTweetsUseCase {
    private final TwitterClientRepository twitterClientRepository;

    public Mono<Boolean> publish(TweetsTerms tweetsTerms) {
        return twitterClientRepository.getTweets(tweetsTerms);
    }
}
