package simple;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;

public class BandControllerRestAssuredTest {

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new BandController());
    }

    @Test
    public void getFilteredBands() {
        given().
                log().all().
                when().
                get("/bands?filtered").
                then().
                log().all().
                statusCode(200);
    }

   @Test
	public void getAllBands() {
        given().
            log().all().
        when().
            get("/bands").
        then().
            log().all().
            statusCode(200);
	}

    @Test
	public void createBand() {
        given().
            log().all().
            contentType(ContentType.JSON).
            body(Band.builder()
                    .name("Saxon")
                    .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                    .build()).
        when().
            post("/bands").
        then().
            log().all().
            statusCode(201);
	}

    @Test
	public void getBand() {
        given().
            log().all().
        when().
            get("/bands/0").
        then().
            log().all().
            statusCode(200);
	}

    @Test
	public void updateBand() {
        given().
            log().all().
            contentType(ContentType.JSON).
            body(Band.builder()
                    .name("Saxon")
                    .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                    .build()).
        when().
            put("/bands/0").
        then().
            log().all().
            statusCode(204);;
	}

    @Test
	public void deleteBand() {
        given().
            log().all().
        when().
            delete("/bands/0").
        then().
            log().all().
            statusCode(200);;
	}
}
