package simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import se.patrikbergman.java.utility.resource.ResourceString;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class BandMarshalUnMarshalTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test //From Java pojo to JSON string == Serialize
    public void marshallBand() throws IOException {
        Band band = Band.builder()
                .name("Saxon")
                .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                .build();
        String jsonString = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(band);
        assertNotNull(jsonString);
        System.out.println(jsonString);
    }

    @Test //From JSON to Java pojo == Deserialize
    public void unmarshallBand() throws IOException {
        String jsonString = new ResourceString("band.json").toString();
        assertNotNull(jsonString);
        System.out.println(jsonString);

        Band band = mapper.readValue(jsonString, Band.class);
        assertNotNull(band);
        System.out.println(band);
    }
}
