package simple;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class BandControllerTest {

    private MockMvc mvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new BandController()).build();
    }

    @Test
    public void getAllBands() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/bands").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
//                .andExpect(content().string(equalTo("get all bands")));
    }

    @Test
    public void createBand() throws Exception {
        Band band = Band.builder()
                .name("Saxon")
                .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                .build();
        String jsonString = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(band);
        mvc.perform(MockMvcRequestBuilders.post("/bands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isCreated());

    }

    @Test
    public void getBand() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/bands/0").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
//                .andExpect(content().string(equalTo("get band with id 0")));
    }

    @Test
    public void updateBand() throws Exception {
          Band band = Band.builder()
                .name("Saxon")
                .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                .build();
        String jsonString = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(band);
        mvc.perform(MockMvcRequestBuilders.put("/bands/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteBand() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/bands/0").accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("delete band with id 0")));
    }

}