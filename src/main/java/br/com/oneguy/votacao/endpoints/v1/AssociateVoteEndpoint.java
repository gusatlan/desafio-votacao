package br.com.oneguy.votacao.endpoints.v1;

import br.com.oneguy.votacao.domain.dto.v1.AssociateVoteDTO;
import br.com.oneguy.votacao.services.IAssociateVoteService;
import br.com.oneguy.votacao.services.IEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static br.com.oneguy.votacao.utils.ApplicationConstants.ENDPOINT_VOTE_V1;

@RestController
@RequestMapping(ENDPOINT_VOTE_V1)
public class AssociateVoteEndpoint {

    private IAssociateVoteService associateVoteService;
    private IEndpointService endpointService;
    private static final String RETRIEVE_TEMPLATE = ENDPOINT_VOTE_V1 + "/%s";

    /**
     * Constructor
     *
     * @param associateVoteService
     * @param endpointService
     */
    @Autowired
    public AssociateVoteEndpoint(final IAssociateVoteService associateVoteService,
                                 final IEndpointService endpointService) {
        this.associateVoteService = associateVoteService;
        this.endpointService = endpointService;
    }

    /**
     * Add a poll
     *
     * @param value
     * @return poll added
     */
    @PostMapping("/")
    public ResponseEntity<String> add(@Valid @RequestBody final AssociateVoteDTO value, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, associateVoteService.add(value), request));
    }

}
