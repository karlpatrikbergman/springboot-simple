package simple.controller;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import simple.repository.BandRepository;
import simple.repository.MockBandFactory;
import simple.service.BandService;

import java.time.LocalDateTime;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class BandControllerRestAssuredMockMvcTest {

    @Before
    public void setup() {
        BandRepository.INSTANCE.addBands(MockBandFactory.INSTANCE.mockSomeEightiesHeavyMetalBands());
        BandService bandService = new BandService(BandRepository.INSTANCE);
        RestAssuredMockMvc.standaloneSetup(new BandController(bandService));
    }
    @Test
	public void createBand() {
        Band band =
            given().
                log().all().
                contentType(ContentType.JSON).
                body(Band.builder()
                        .id(0)
                        .name("Saxon")
                        .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                        .localDateTime(LocalDateTime.now())
                        .build()).
            when().
                post("/bands").
            then().
                log().all().
                statusCode(201).
                extract().
                as(Band.class);
        assertNotNull(band);
	}

    @Test
    public void getBand() {
        Band band =
            given().
                log().all().
            when().
                get("/bands/0").
            then().
                log().all().
                statusCode(200).
                extract().
                as(Band.class);
        assertNotNull(band);
    }

    @Test
	public void updateBand() {
        Band band =
            given().
                log().all().
                contentType(ContentType.JSON).
                body(Band.builder()
                        .id(0)
                        .name("Saxon")
                        .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                        .localDateTime(LocalDateTime.now())
                        .build()).
            when().
                put("/bands/0").
            then().
                log().all().
                statusCode(200).
                extract().
                as(Band.class);
        assertNotNull(band);
        assertEquals("Saxon", band.getName());
	}

     @Test
	public void updateBandThatDoesNotExist() {
            given().
                log().all().
                contentType(ContentType.JSON).
                body(Band.builder()
                        .id(0)
                        .name("Saxon")
                        .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                        .localDateTime(LocalDateTime.now())
                        .build()).
            when().
                put("/bands/99999").
            then().
                log().all().
                statusCode(403);

	}

    @Test
	public void deleteBand() {
        given().
            log().all().
        when().
            delete("/bands/0").
        then().
            log().all().
            statusCode(200);

         given().
                log().all().
            when().
                get("/bands/0").
            then().
                log().all().
                statusCode(403);
	}
//
//    @Test
//    public void getAllBands() {
//        when(bandService.getBands()).thenReturn(MockBandRepository.getBands().subList(0, 5));
//        List<BandEntityTO> actualBands =
//                Arrays.asList(
//                        given().
//                                log().all().
//                                when().
//                                get("/bands").
//                                then().
//                                log().all().
//                                statusCode(200).
//                                extract().
//                                as(BandEntityTO[].class)
//                );
//        assertNotNull(actualBands);
//    }

//    @Test
//    public void getFilteredBands() {
//        given().
//                log().all().
//                when().
//                get("/bands?filtered").
//                then().
//                log().all().
//                statusCode(200);
//    }
//

//
//
}
