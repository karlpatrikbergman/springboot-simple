package simple.repository;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public enum MockBandFactory {
    INSTANCE;

    public List<BandEntityTO> mockSomeEightiesHeavyMetalBands() {
        return Arrays.asList(
                BandEntityTO.builder()
                        .name("Iron Maiden".concat("-").concat(RandomStringUtils.randomAlphanumeric(4)))
                        .description("Iron Maiden are an English heavy metal band formed in Leyton, east London, in " +
                                "1975 by bassist and primary songwriter Steve Harris.")
                        .localDateTime(LocalDateTime.now())
                        .build(),
                BandEntityTO.builder()
                        .name("Accept".concat("-").concat(RandomStringUtils.randomAlphanumeric(4)))
                        .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                        .localDateTime(LocalDateTime.now())
                        .build(),
                BandEntityTO.builder()
                        .name("Saxon".concat("-").concat(RandomStringUtils.randomAlphanumeric(4)))
                        .description("Accept is a German heavy metal band from the town of Solingen.")
                        .localDateTime(LocalDateTime.now())
                        .build(),
                BandEntityTO.builder()
                        .name("Judas Priest".concat("-").concat(RandomStringUtils.randomAlphanumeric(4)))
                        .description("Judas Priest is a British heavy metal band formed in Birmingham, England, in 1970")
                        .localDateTime(LocalDateTime.now())
                        .build()
        );
    }
}
