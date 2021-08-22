package br.com.oneguy.votacao.endpoints.v1;

import br.com.oneguy.votacao.domain.dto.v1.MinuteMeetingDTO;
import br.com.oneguy.votacao.services.IEndpointService;
import br.com.oneguy.votacao.services.IMinuteMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

import static br.com.oneguy.votacao.utils.ApplicationConstants.ENDPOINT_MINUTE_MEETING_V1;

@RestController
@RequestMapping(ENDPOINT_MINUTE_MEETING_V1)
public class MinuteMeetingEndpoint {

    private IMinuteMeetingService minuteMeetingService;
    private IEndpointService endpointService;
    private static final String RETRIEVE_TEMPLATE = ENDPOINT_MINUTE_MEETING_V1 + "/%s";

    /**
     * Constructor
     *
     * @param minuteMeetingService
     * @param endpointService
     */
    @Autowired
    public MinuteMeetingEndpoint(final IMinuteMeetingService minuteMeetingService, final IEndpointService endpointService) {
        this.minuteMeetingService = minuteMeetingService;
        this.endpointService = endpointService;
    }

    /**
     * Add a minuteMeeting
     *
     * @param value
     * @return minuteMeeting added
     */
    @PostMapping("/")
    public ResponseEntity<String> add(@Valid @RequestBody final MinuteMeetingDTO value, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, minuteMeetingService.add(value), request));
    }

    /**
     * Update a minuteMeeting
     *
     * @param value
     * @return minuteMeeting added
     */
    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody() final MinuteMeetingDTO value, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, minuteMeetingService.update(value), request));
    }

    /**
     * Remove a minuteMeeting
     *
     * @param id
     * @return minuteMeeting added
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable(value = "id", required = true) final String id, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, minuteMeetingService.remove(new MinuteMeetingDTO(id)), request));
    }


    /**
     * Return minuteMeeting by id
     *
     * @param id
     * @return minuteMeeting
     */
    @GetMapping("/{id}")
    public ResponseEntity<MinuteMeetingDTO> findById(@PathVariable(value = "id", required = true) final String id) throws Exception {
        MinuteMeetingDTO dto = minuteMeetingService.convert(minuteMeetingService.findById(id));
        HttpStatus status = dto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(dto, status);
    }

    /**
     * Return minuteMeetings
     *
     * @return minuteMeetings
     */
    @GetMapping()
    public ResponseEntity<Collection<MinuteMeetingDTO>> findAll() {
        Collection<MinuteMeetingDTO> items = minuteMeetingService.convert(minuteMeetingService.findAll());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
