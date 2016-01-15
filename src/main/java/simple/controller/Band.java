package simple.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Band implements Serializable {
    private static final long serialVersionUID = 3358824117349512681L;
    @NonNull private Integer id;
    @NonNull private String name;
    @NonNull private String description;
    @NonNull private LocalDateTime localDateTime;
}
