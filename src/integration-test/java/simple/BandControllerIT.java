package simple;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import se.patrikbergman.java.utility.JsonString;

import java.net.URL;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class BandControllerIT {

    @Value("${local.server.port}")
    private int port;
    private URL base;
    private RestTemplate template;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        template = new TestRestTemplate();
        mapper = new ObjectMapper();
    }

    @Test
    public void getAllBands() throws Exception {
        base = new URL("http://localhost:" + port + "/bands");
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        List<Band> bands = mapper.readValue(response.getBody(), new TypeReference<List<Band>>() {});
        Band band = bands.get(0);
        assertThat(band.getName(), equalTo("Saxon"));
        log.info("\n" + new JsonString(band).toString());
    }

    @Test
    public void getFilteredBands() throws Exception {
        base = new URL("http://localhost:" + port + "/bands?filtered");
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        List<Band> bands = mapper.readValue(response.getBody(), new TypeReference<List<Band>>() {});
        Band band = bands.get(0);
        assertThat(bands.get(0).getName(), equalTo("Saxon-filtered"));
        log.info("\n" + new JsonString(band).toString());
    }
}