package simple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import se.patrikbergman.java.utility.resource.ResourceString;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class FooTest {

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    }

    @Test //From Java pojo to JSON string == Serialize
    public void marshallFoo() throws JsonProcessingException {
        Foo foo = Foo.builder()
                .name("foo-name")
                .description("foo-description")
                .build();
        String jsonString = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(foo);
        assertNotNull(jsonString);
        System.out.println(jsonString);
    }

    @Test //From JSON to Java pojo == Deserialize
    public void unmarshallFoo() throws IOException {
        String jsonString = new ResourceString("foo.json").toString();
        assertNotNull(jsonString);
        System.out.println(jsonString);

        Foo foo = mapper.readValue(jsonString, Foo.class);
        assertNotNull(foo);
        System.out.println(foo);
    }
}
