package itmo.utils.mapper;


import itmo.utils.dto.SpaceMarineCreateRequest;
import itmo.utils.dto.SpaceMarineUpdateRequest;
import jakarta.ejb.Remote;

@Remote
public interface SpaceMarineValidator {
    void validate(SpaceMarineCreateRequest spaceMarineCreateRequest);

    void validate(SpaceMarineUpdateRequest spaceMarineUpdateRequest);
}
