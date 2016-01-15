package simple.repository;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BandRepositoryTest {

    private final BandRepository repository = BandRepository.INSTANCE;

    @Before
    public void setup() {
        repository.addBands(MockBandFactory.INSTANCE.mockSomeEightiesHeavyMetalBands());
    }

    //Create
    @Test
    public void createBand() {
        BandEntityTO bandEntityTO  = BandEntityTO.builder()
                .name("ABBA")
                .description("Swedish pop-band")
                .localDateTime(LocalDateTime.now().minusYears(25))
                .build();

        BandEntity abba = repository.createBand(bandEntityTO);
        assertNotNull(abba);
        assertEquals("ABBA", abba.getName());

        BandEntity baab = repository.getBand(abba.getId());
        assertNotNull(baab);
        assertEquals("ABBA", baab.getName());

        System.out.println("abba: " + abba);
        System.out.println("baab: " + baab);
    }

    //Read
    @Test
    public void getBand() {
        BandEntity band = repository.getBand(1);
        assertNotNull(band);
        System.out.println(band);
    }

    //Update
    @Test
    public void updateBand() {
        final String newBandName = "A new band name";
        final String newBandDescription = "A new band description";
        BandEntity bandEntityBeforeUpdate = repository.getBand(2);
        BandEntityTO updateBandTO = BandEntityTO.builder()
                .name(newBandName)
                .description(newBandDescription)
                .localDateTime(bandEntityBeforeUpdate.getLocalDateTime())
                .build();

        BandEntity bandEntityUpdated = repository.updateBand(bandEntityBeforeUpdate.getId(), updateBandTO);
        assertNotNull(bandEntityUpdated);
        assertEquals(bandEntityBeforeUpdate.getId(), bandEntityUpdated.getId());
        assertEquals(newBandName, bandEntityUpdated.getName());
        assertEquals(newBandDescription, bandEntityUpdated.getDescription());
        System.out.println(bandEntityUpdated);
    }

    //Delete
    @Test(expected = RuntimeException.class)
    public void deleteBand() {
        BandEntityTO bandEntityTO  = BandEntityTO.builder()
                .name("Secret Service")
                .description("Swedish pop-band")
                .localDateTime(LocalDateTime.now().minusYears(23))
                .build();

        BandEntity secretService = repository.createBand(bandEntityTO);
        assertNotNull(secretService);
        assertEquals("Secret Service", secretService.getName());

        BandEntity shallExist = repository.getBand(secretService.getId());
        assertNotNull(shallExist);
        repository.deleteBand(secretService.getId());

        //Should throw RuntimeException
        repository.getBand(secretService.getId());
    }

    @Test
    public void getBands() {
        List<BandEntity> bands = repository.getBands();
        assertNotNull(bands);
        System.out.println(bands);
    }
}
