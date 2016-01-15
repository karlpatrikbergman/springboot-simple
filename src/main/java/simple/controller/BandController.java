package simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simple.repository.BandEntity;
import simple.repository.BandEntityTO;
import simple.repository.BandNotFoundException;
import simple.service.BandService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import java.util.Set;

/*
GET     /bands           Read all bands
POST    /bands           Create a new band from information in the POST body
GET     /bands/new       Read the form to create a new band
GET     /bands/:id/edit  Read the form to edit an existing band
GET     /bands/:id       Read a single band
PUT     /bands/:id       Update a band with information in the POST body
DELETE  /bands:id        Delete the specified band

Information on which response codes to return:
http://www.infoq.com/articles/designing-restful-http-apps-roth

TODO:
I don't want object input to create and update band to have id field!
*/

@Validated
@RestController
@RequestMapping(value = "/bands", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BandController {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BandService bandService;

    @Autowired
    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    //CREATE
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Band> createBand(@RequestBody Band band) {
        BandEntityTO bandEntityTO = BandEntityTO.builder()
                .name(band.getName())
                .description(band.getDescription())
                .localDateTime(band.getLocalDateTime())
                .build();
        BandEntity createdBandEntity = bandService.createBand(bandEntityTO);
        return createResponseEntity(createdBandEntity, HttpStatus.CREATED);
    }

    //READ
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Band> getBand(@Min(value = 1, message = "Id cannot be below 1") @PathVariable("id") int id) {
        BandEntity retrievedBandEntity = bandService.getBand(id);
        return createResponseEntity(retrievedBandEntity, HttpStatus.OK);
    }

    //UPDATE
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Band> updateBand(@PathVariable int id, @RequestBody Band band) {
        BandEntityTO bandEntityTO = BandEntityTO.builder()
                .name(band.getName())
                .description(band.getDescription())
                .localDateTime(band.getLocalDateTime())
                .build();
        BandEntity updatedBandEntity = bandService.updateBand(id, bandEntityTO);
        return createResponseEntity(updatedBandEntity, HttpStatus.OK);
    }

    //DELETE
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteBand(@PathVariable("id") int id) {
        bandService.deleteBand(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity<Band> createResponseEntity(BandEntity bandEntity, HttpStatus responseCode) {
        return new ResponseEntity<>(Band.builder()
                .id(bandEntity.getId())
                .name(bandEntity.getName())
                .description(bandEntity.getDescription())
                .localDateTime(bandEntity.getLocalDateTime())
                .build(), responseCode);
    }

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<BandEntity> getAllBands() {
        return bandService.getBands();
    }

    @ExceptionHandler({IllegalStateException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(BandNotFoundException.class)
    void handleBandNotFoundFound(HttpServletResponse response, BandNotFoundException exception) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ExceptionHandler(UnsupportedTemporalTypeException.class)
    void handleWrongDateFormat(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Date request parameters must have format " + DATETIME_FORMAT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    void handleValidationError(HttpServletResponse response, ConstraintViolationException e) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), getConstraintValidationErrorMessage(e));
    }

    private String getConstraintValidationErrorMessage(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage()).append(System.getProperty("line.separator"));
        }
        return strBuilder.toString();
    }

    /**
     * Bonus method
     */
//    @RequestMapping(params = { "maxNrOfEvents", "offset", "sortAscending", FROM, TO }, method = RequestMethod.GET)
//    public ResponseEntity<Collection<Event>> getFiltered(@RequestParam(value = "maxNrOfEvents", defaultValue = "10") int maxNrOfEvents,
//                                                         @RequestParam(value = "offset", defaultValue = "0") int offset,
//                                                         @RequestParam(value = "sortAscending", defaultValue = "true") boolean sortAscending,
//                                                         @RequestParam(FROM) @DateTimeFormat(pattern = YYYY_MM_DD) LocalDate stopDate,
//                                                         @RequestParam(TO) @DateTimeFormat(pattern = YYYY_MM_DD) LocalDate startDate) {
//        log.debug("Retrieving filtered events");
//        final Collection<Event> events = eventExportService.getFiltered(
//                EventFilter.builder().maxNrOfEvents(maxNrOfEvents).offset(offset).sortAscending(sortAscending).startDate(startDate).stopDate(stopDate)
//                        .build());
//        log.debug("Found {} filtered events", events.size());
//        return new ResponseEntity<>(events, HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_UTF8_VALUE, params={"filtered"})
//    public List<BandEntityTO> getAllBandsFiltered() {
//        return Arrays.asList(
//                BandEntityTO.builder()
//                        .name("Saxon-filtered")
//                        .description("Saxon are an English heavy metal band formed in 1976, in South Yorkshire-filtered")
//                        .build(),
//                BandEntityTO.builder()
//                        .name("Judas Priest-filtered")
//                        .description("Judas Priest is a British heavy metal band formed in Birmingham, England, in 1970-filtered")
//                        .build()
//        );
//    }
}
