package br.com.oneguy.votacao.endpoints;

import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.services.IAssociateService;
import br.com.oneguy.votacao.services.IEndpointService;
import br.com.oneguy.votacao.utils.ConverterUtil;
import br.com.oneguy.votacao.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import static br.com.oneguy.votacao.utils.EndpointsUtil.ENDPOINT_ASSOCIATE_V1;

@RestController
@RequestMapping(ENDPOINT_ASSOCIATE_V1)
public class AssociateEndpoint {

    private IAssociateService associateService;
    private IEndpointService endpointService;

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
    public ResponseEntity<Response<String>> add(@Valid @RequestBody final AssociateDTO value) {
        return endpointService.proccess((p) -> {
                    String key = null;
                    try {
                        key = associateService.add(p);
                    } catch (Exception e) {
                    }
                    return key;
                }
                , value
                , 30L
                , ENDPOINT_ASSOCIATE_V1,
                associateService.validateEntity(value));
    }

    /**
     * Update a associate
     *
     * @param value
     * @return associate added
     */
    @PutMapping
    public ResponseEntity<Response<String>> update(@Valid @RequestBody() final AssociateDTO value) {
            ResponseEntity<Response<String>> response = endpointService.proccess((p) -> {
                        String key = null;
                        try {
                            key = associateService.update(p);
                        } catch (Exception e) {
                        }
                        return key;
                    }
                    , value
                    , 30L
                    , ENDPOINT_ASSOCIATE_V1,
                    associateService.validateEntity(value));

        return response;
    }

    /**
     * Remove a associate
     *
     * @param id
     * @return associate added
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable(value = "id", required = true) final String id) {
        AssociateDTO value = new AssociateDTO(id, null);

        return endpointService.proccess((p) -> {
                    String key = null;
                    try {
                        key = associateService.remove(p);
                    } catch (Exception e) {
                    }
                    return key;
                }
                , value
                , 30L
                , ENDPOINT_ASSOCIATE_V1,
                new HashSet<>());
    }

    /**
     * Return associates by identification
     *
     * @param identification
     * @return associates
     */
    @GetMapping("/identification/{identification}")
    public ResponseEntity<Collection<AssociateDTO>> findByIdentification(@PathVariable(value = "identification", required = true) final String identification) {
        return convert(associateService.findByIdentification(identification));
    }

    /**
     * Return associate by id
     *
     * @param id
     * @return associate
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssociateDTO> findById(@PathVariable(value = "id", required = true) final String id) {
        AssociatePU obj = associateService.findById(id);
        AssociateDTO dto = new AssociateDTO();
        HttpStatus status = obj != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        try {
            dto.setId(obj.getId());
            dto.setIdentification(obj.getIdentification());
        } catch (Exception e) {
            dto = null;
        }

        return new ResponseEntity<>(dto, status);
    }

    /**
     * Return associates
     *
     * @return associates
     */
    @GetMapping()
    public ResponseEntity<Collection<AssociateDTO>> findAll() {
        return convert(associateService.findAll());
    }

    /**
     * Convert collection of AssociatePU in AssociateDTO
     *
     * @param values
     * @return associates
     */
    private ResponseEntity<Collection<AssociateDTO>> convert(final Collection<AssociatePU> values) {
        return ResponseEntity.ok(values.stream()
                .filter(p -> p != null && p.isValid())
                .map(p -> ConverterUtil.convert(p))
                .collect(Collectors.toSet()));
    }

}
