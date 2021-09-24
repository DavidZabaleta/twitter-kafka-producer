package co.berako.model.twitter;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class TweetsTerms {
    private List<String> terms;
}
