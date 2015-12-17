package simple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Band {
    String name;
    String description;
}

//Without Lombok
/*
@Builder
@Value
@EqualsAndHashCode
public class Band {
    private final String name;
    private final String description;

    @JsonCreator
    public Band(@JsonProperty("name") String name,
                @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }
}
*/