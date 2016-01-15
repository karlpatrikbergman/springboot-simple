package simple.repository;

import java.time.LocalDateTime;

public interface BandEntity {
    Integer getId();
    String getName();
    String getDescription();
    LocalDateTime getLocalDateTime();
}
