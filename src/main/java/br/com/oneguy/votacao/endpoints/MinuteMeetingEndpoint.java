package br.com.oneguy.votacao.endpoints;

import br.com.oneguy.votacao.domain.dto.v1.MinuteMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import br.com.oneguy.votacao.services.IEndpointService;
import br.com.oneguy.votacao.services.IMinuteMeetingService;
import br.com.oneguy.votacao.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

import static br.com.oneguy.votacao.utils.EndpointsUtil.ENDPOINT_MINUTE_MEETING_V1;

@RestController
@RequestMapping(ENDPOINT_MINUTE_MEETING_V1)
public class MinuteMeetingEndpoint {

    private IMinuteMeetingService minuteMeetingService;
    private IEndpointService endpointService;

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
    public ResponseEntity<Response<String>> add(@Valid @RequestBody final MinuteMeetingDTO value) {
        return endpointService.proccess((p) -> {
                    String key = null;
                    try {
                        key = minuteMeetingService.add(p);
                    } catch (Exception e) {
                    }
                    return key;
                }
                , value
                , 30L
                , ENDPOINT_MINUTE_MEETING_V1,
                minuteMeetingService.validateEntity(value));
    }

    /**
     * Update a minuteMeeting
     *
     * @param value
     * @return minuteMeeting added
     */
    @PutMapping
    public ResponseEntity<Response<String>> update(@Valid @RequestBody() final MinuteMeetingDTO value) {
            ResponseEntity<Response<String>> response = endpointService.proccess((p) -> {
                        String key = null;
                        try {
                            key = minuteMeetingService.update(p);
                        } catch (Exception e) {
                        }
                        return key;
                    }
                    , value
                    , 30L
                    , ENDPOINT_MINUTE_MEETING_V1,
                    minuteMeetingService.validateEntity(value));

        return response;
    }

    /**
     * Remove a minuteMeeting
     *
     * @param id
     * @return minuteMeeting added
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable(value = "id", required = true) final String id) {
        MinuteMeetingDTO value = new MinuteMeetingDTO(id);

        return endpointService.proccess((p) -> {
                    String key = null;
                    try {
                        key = minuteMeetingService.remove(p);
                    } catch (Exception e) {
                    }
                    return key;
                }
                , value
                , 30L
                , ENDPOINT_MINUTE_MEETING_V1,
                new HashSet<>());
    }


    /**
     * Return minuteMeeting by id
     *
     * @param id
     * @return minuteMeeting
     */
    @GetMapping("/{id}")
    public ResponseEntity<MinuteMeetingDTO> findById(@PathVariable(value = "id", required = true) final String id) {
        MinuteMeetingPU obj = minuteMeetingService.findById(id);
        MinuteMeetingDTO dto = new MinuteMeetingDTO();
        HttpStatus status = obj != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        try {
            dto = minuteMeetingService.convert(obj);
        } catch (Exception e) {
            dto = null;
        }

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
