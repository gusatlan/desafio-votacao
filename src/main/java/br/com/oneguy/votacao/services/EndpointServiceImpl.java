package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

@Component
public class EndpointServiceImpl implements IEndpointService {

    /**
     * Process request
     * @param exec
     * @param value
     * @param timeout
     * @param uriTemplate
     * @param violations
     * @param <T>
     * @return response
     */
    @Override
    public <T> ResponseEntity<Response<String>> proccess(final Function<T, String> exec, final T value, final Long timeout, final String uriTemplate, final Collection<String> violations) {
        Response<String> response = new Response<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(violations != null && !violations.isEmpty()) {
            response.addMessages(violations);
            return new ResponseEntity<>(response, status);
        }

        try {
            CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> exec.apply(value));
            response.setResponse(String.format(uriTemplate + "/%s", task.get(timeout, TimeUnit.SECONDS)));
            status = HttpStatus.ACCEPTED;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            status = HttpStatus.REQUEST_TIMEOUT;
            response.addMessage("Ocorreu timeout");
        } catch(NullPointerException | EntityNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
            response.addMessage(e.getMessage());
        } catch(EntityExistsException e) {
            status = HttpStatus.CONFLICT;
            response.addMessage(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.addMessage(e.getMessage());
        }

        response.setStatus(status);

        //return new ResponseEntity<>(response, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
