package br.com.oneguy.votacao.endpoints;

import br.com.oneguy.votacao.domain.dto.v1.PollMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.PollMeetingPU;
import br.com.oneguy.votacao.services.IEndpointService;
import br.com.oneguy.votacao.services.IPollMeetingService;
import br.com.oneguy.votacao.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

import static br.com.oneguy.votacao.utils.EndpointsUtil.ENDPOINT_POLL_MEETING_V1;

@RestController
@RequestMapping(ENDPOINT_POLL_MEETING_V1)
public class PollMeetingEndpoint {

    private IPollMeetingService pollMeetingService;
    private IEndpointService endpointService;

    /**
     * Constructor
     *
     * @param pollMeetingService
     * @param endpointService
     */
    @Autowired
    public PollMeetingEndpoint(final IPollMeetingService pollMeetingService,
                               final IEndpointService endpointService) {
        this.pollMeetingService = pollMeetingService;
        this.endpointService = endpointService;
    }

    /**
     * Add a poll
     *
     * @param value
     * @return poll added
     */
    @PostMapping("/")
    public ResponseEntity<Response<String>> add(@Valid @RequestBody final PollMeetingDTO value) {
        return endpointService.proccess((p) -> {
                    String key = null;
                    try {
                        key = pollMeetingService.add(p);
                    } catch (Exception e) {
                    }
                    return key;
                }
                , value
                , 30L
                , ENDPOINT_POLL_MEETING_V1,
                pollMeetingService.validateEntity(value));
    }

}
