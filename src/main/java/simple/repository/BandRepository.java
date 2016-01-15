package simple.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Mimics a database/storage
 */
@Repository
public class BandRepository {

    private final Map<Integer, BandEntity> bands;
    private final AtomicInteger idCounter;

    public BandRepository() {
        idCounter = new AtomicInteger(0);
        bands = new ConcurrentHashMap<>();
        addBands(MockBandFactory.INSTANCE.mockSomeEightiesHeavyMetalBands());
    }

    public BandEntity createBand(BandEntityTO bandEntityTO) {
        BandEntity newBandEntity = of(bandEntityTO);
        bands.put(newBandEntity.getId(), newBandEntity);
        return newBandEntity;
    }

    public BandEntity getBand(int id) {
        if(!bands.containsKey(id)) {
            throw new BandNotFoundException("Found no band with id " + id);
        }
        return bands.get(id);
    }

    public BandEntity updateBand(int id, BandEntityTO bandEntityTO) {
        if(!bands.containsKey(id)) {
            throw new BandNotFoundException("Found no band with id " + id);
        }
        bands.remove(id);
        BandEntity updatedBandEntity = of(id, bandEntityTO);
        bands.put(id, updatedBandEntity);
        return updatedBandEntity;
    }

    public void deleteBand(int id) {
        if(!bands.containsKey(id)) {
            throw new RuntimeException("Found no band with id " + id);
        }
        bands.remove(id);
    }

    public List<BandEntity> getBands() {
        return new ArrayList<>(bands.values());
    }

    public void addBands(List<BandEntityTO> bands) {
        bands.stream().forEach(this::createBand);
    }

    /*******************************************************************************/

    private Integer getNextId() {
        return idCounter.getAndIncrement();
    }

    /**
     * Supposedly the only place where BandEntity:s are created
     */
    BandEntity of(BandEntityTO bandEntityTO) {
        return BandEntityImpl.builder()
                .id(getNextId())
                .name(bandEntityTO.getName())
                .description(bandEntityTO.getDescription())
                .localDateTime(bandEntityTO.getLocalDateTime())
                .build();
    }

    BandEntity of(int id, BandEntityTO bandEntityTO) {
        return BandEntityImpl.builder()
                .id(id)
                .name(bandEntityTO.getName())
                .description(bandEntityTO.getDescription())
                .localDateTime(bandEntityTO.getLocalDateTime())
                .build();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class BandEntityImpl implements BandEntity{
        @NonNull Integer id;
        @NonNull String name;
        @NonNull String description;
        @NonNull LocalDateTime localDateTime;
    }
}
