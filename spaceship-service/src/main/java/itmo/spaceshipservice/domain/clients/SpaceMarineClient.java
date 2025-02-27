package itmo.spaceshipservice.domain.clients;

import itmo.spaceshipservice.domain.dto.SpaceMarine;
import itmo.spaceshipservice.domain.exceptions.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RestController
@Controller
public class SpaceMarineClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${SPACE_MARINE_SERVICE_URL}")
    private String SPACE_MARINE_SERVICE_URL;

    public SpaceMarine getSpaceMarine(Integer id) {
        try {
            return restTemplate.getForObject(SPACE_MARINE_SERVICE_URL + "/marines/" + id, SpaceMarine.class);
        } catch (HttpClientErrorException e) {
            if (e.getMessage().contains("not.found.marine")) return null;
            throw new ServiceUnavailableException("Сервис временно недоступен");
        } catch (Exception e) {
            throw new ServiceUnavailableException("Сервис временно недоступен");
        }
    }
}
