package br.com.oneguy.votacao.endpoints.v1;

import br.com.oneguy.votacao.domain.dto.v1.PollMeetingDTO;
import br.com.oneguy.votacao.services.IEndpointService;
import br.com.oneguy.votacao.services.IPollMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static br.com.oneguy.votacao.utils.ApplicationConstants.ENDPOINT_POLL_MEETING_V1;

@RestController
@RequestMapping(ENDPOINT_POLL_MEETING_V1)
public class PollMeetingEndpoint {

    private IPollMeetingService pollMeetingService;
    private IEndpointService endpointService;
    private static final String RETRIEVE_TEMPLATE = ENDPOINT_POLL_MEETING_V1 + "/%s";

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
    public ResponseEntity<String> add(@Valid @RequestBody final PollMeetingDTO value, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, pollMeetingService.add(value), request));
    }

}
