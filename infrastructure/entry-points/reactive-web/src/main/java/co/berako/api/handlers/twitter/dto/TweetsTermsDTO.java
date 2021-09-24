package co.berako.api.handlers.twitter.dto;

import co.berako.model.twitter.TweetsTerms;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class TweetsTermsDTO {
    private List<String> terms;

    public static TweetsTerms convertDTOToDomain(TweetsTermsDTO tweetsTermsDTO) {
        return TweetsTerms.builder()
                .terms(tweetsTermsDTO.getTerms())
                .build();
    }
}
