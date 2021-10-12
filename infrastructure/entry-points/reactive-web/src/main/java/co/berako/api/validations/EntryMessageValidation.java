package co.berako.api.validations;

import co.berako.model.exceptions.validations.FieldsValidationException;
import co.berako.model.exceptions.validations.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EntryMessageValidation {

    private static final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public EntryMessageValidation() {
    }

    public static Mono<String> validateLocationWeatherParams(String entryMessageDTO) {
        return validateJson(entryMessageDTO, "LocationWeatherParamsSchema.json")
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.error(FieldsValidationException.Type.INVALID_JSON_STRUCTURE::build);
                });
    }

    private static Mono<String> validateJson(String entryMessageDTO, String path) {
        List<ValidationMessage> result = validateJson(entryMessageDTO, schemaFactory.getSchema(getClasspath(path)));

        return result.isEmpty()
                ? Mono.just(entryMessageDTO)
                : Mono.error(new ValidationException(result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","))));
    }

    private static List<ValidationMessage> validateJson(String data, JsonSchema schema) {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return new ArrayList<>(schema.validate(node));
    }

    private static InputStream getClasspath(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
