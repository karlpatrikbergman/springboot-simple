package simple.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import se.patrikbergman.java.utility.resource.ResourceString;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

public class BandMarshalUnMarshalTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }

    @Test //From Java pojo to JSON string == Serialize
    public void marshallBand() throws IOException {
        Band band = Band.builder()
                .id(0)
                .localDateTime(LocalDateTime.now())
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
