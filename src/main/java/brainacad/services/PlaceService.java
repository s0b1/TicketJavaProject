package brainacad.services;


import brainacad.dto.PlaceDTO;
import brainacad.entities.Place;
import brainacad.repositories.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService
{

    private final PlaceRepository placeRepository;

    public Place findOrCreatePlace(PlaceDTO dto)
    {
        return placeRepository.findByName(dto.getName())
                .orElseGet(() ->
                {
                    Place place = new Place();
                    place.setName(dto.getName());
                    place.setAddress(dto.getAddress());
                    return placeRepository.save(place);
                });
    }
}
