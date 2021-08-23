package br.com.oneguy.votacao.endpoints.v1;

import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.services.IAssociateService;
import br.com.oneguy.votacao.services.IEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.oneguy.votacao.utils.ApplicationConstants.ENDPOINT_ASSOCIATE_V1;

@RestController
@RequestMapping(ENDPOINT_ASSOCIATE_V1)
public class AssociateEndpoint {

    private IAssociateService associateService;
    private IEndpointService endpointService;
    private static final String RETRIEVE_TEMPLATE = ENDPOINT_ASSOCIATE_V1 + "/%s";

    /**
     * Constructor
     *
     * @param associateService
     * @param endpointService
     */
    @Autowired
    public AssociateEndpoint(final IAssociateService associateService, final IEndpointService endpointService) {
        this.associateService = associateService;
        this.endpointService = endpointService;
    }

    /**
     * Add a associate
     *
     * @param value
     * @return associate added
     */
    @PostMapping("/")
    public ResponseEntity<String> add(@Valid @RequestBody final AssociateDTO value, final HttpServletRequest request) throws Exception {
        String uri = endpointService.process(RETRIEVE_TEMPLATE, associateService.add(value), request);
        return ResponseEntity.ok(uri);
    }

    /**
     * Update a associate
     *
     * @param value
     * @return associate added
     */
    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody() final AssociateDTO value, final HttpServletRequest request) throws Exception {
        String uri = endpointService.process(RETRIEVE_TEMPLATE, associateService.update(value), request);
        return ResponseEntity.ok(uri);
    }

    /**
     * Remove a associate
     *
     * @param id
     * @return associate added
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable(value = "id", required = true) final String id, final HttpServletRequest request) throws Exception {
        AssociateDTO value = new AssociateDTO(id, null);
        String uri = endpointService.process(RETRIEVE_TEMPLATE, associateService.remove(value), request);
        return ResponseEntity.ok(uri);
    }

    /**
     * Return associates by identification
     *
     * @param identification
     * @return associates
     */
    @GetMapping(path = "/identification/{identification}")
    public ResponseEntity<Collection<AssociateDTO>> findByIdentification(@PathVariable(value = "identification", required = true) final String identification) {
        return ResponseEntity.ok(convert(associateService.findByIdentification(identification)));
    }

    /**
     * Return associate by id
     *
     * @param id
     * @return associate
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssociateDTO> findById(@PathVariable(value = "id", required = true) final String id) throws Exception {
        AssociateDTO dto = associateService.convert(associateService.findById(id));
        HttpStatus status = dto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(dto, status);
    }

    /**
     * Return associates
     *
     * @return associates
     */
    @GetMapping
    public Page<AssociateDTO> findAll(final Pageable pageable) {
        Page<AssociatePU> pagePU = associateService.findAll(pageable);
        Page<AssociateDTO> page = new PageImpl<>(convert(pagePU.getContent()), pagePU.getPageable(), pagePU.getTotalElements());

        return page;
    }

    /**
     * Convert collection of AssociatePU in AssociateDTO
     *
     * @param values
     * @return associates
     */
    private List<AssociateDTO> convert(final Collection<AssociatePU> values) {
        return values.stream()
                .filter(p -> p != null && p.isValid())
                .map(p -> associateService.convert(p))
                .collect(Collectors.toList());
    }

}
