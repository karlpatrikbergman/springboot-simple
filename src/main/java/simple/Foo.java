package simple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

/**
 * This is a test on how to use lombok @Value, @Builder with
 * jackson @JsonPOJOBuilder
 *
 * Resources:
 * https://groups.google.com/forum/#!topic/project-lombok/PGyFNUP-Ofs:
 * "You can create an empty BookBuilder class annotated with @JsonPOJOBuilder inside the book class.
 * Lombok will use that class and add the missing fields and method."
 *
 * http://wiki.fasterxml.com/JacksonFeatureBuilderPattern
 */

@Value
@Builder
@JsonDeserialize(builder=Foo.FooBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Foo {
    private String name;
    private String description;

    @JsonPOJOBuilder(withPrefix="")
    public static class FooBuilder {}
}