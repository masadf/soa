package itmo.spaceshipservice.domain.clients;

import itmo.spaceshipservice.domain.dto.SpaceMarine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpaceMarineClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${SPACE_MARINE_SERVICE_URL}")
    private String SPACE_MARINE_SERVICE_URL;

    public SpaceMarine getSpaceMarine(Integer id) {
        try {
            return restTemplate.getForObject(SPACE_MARINE_SERVICE_URL + "/marines/" + id, SpaceMarine.class);
        }catch (Exception e){
            return null;
        }
    }
}
