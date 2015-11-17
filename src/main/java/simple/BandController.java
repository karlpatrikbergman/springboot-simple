package simple;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/*
GET     /bands           Read all bands
POST    /bands           Create a new band from information in the POST body
GET     /bands/new       Read the form to create a new band
GET     /bands/:id/edit  Read the form to edit an existing band
GET     /bands/:id       Read a single band
PUT     /bands/:id       Update a band with information in the POST body
DELETE  /bands:id        Delete the specified band
*/

@RestController
@RequestMapping(value = "/bands")
public class BandController {

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Band> getAllBands() {
        return Arrays.asList(
                Band.builder()
                        .name("Saxon")
                        .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                        .build(),
                Band.builder()
                        .name("Judas Priest")
                        .description("Judas Priest is a British heavy metal band formed in Birmingham, England, in 1970")
                        .build()
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createBand(@RequestBody Band band) {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Band getBand(@PathVariable("id") int id) {
        return Band.builder()
                .name("Saxon")
                .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire")
                .build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateBand(@RequestBody Band band) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces= MediaType.TEXT_PLAIN_VALUE)
    public String deleteBand(@PathVariable("id") int id) {
        return "delete band with id " + id;
    }

}
