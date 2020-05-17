package dmz.crud.rest;

import dmz.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class RestTemplateUserController {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Autowired
    RestTemplateUserController(RestTemplate restTemplateUser, HttpHeaders headersUser){
        this.restTemplate = restTemplateUser;
        this.headers = headersUser;
    }

    @GetMapping(value="/api/users")
    public String getUsersByTemplate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("http://localhost:8081/api/users/" + ((User) authentication.getPrincipal()).getId(), HttpMethod.GET, entity, String.class).getBody();
    }
}
