package brainacad.services;

import brainacad.dto.PlaceDTO;
import brainacad.entities.Place;
import brainacad.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceServiceTest
{

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceService placeService;

    public PlaceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findOrCreatePlace_returnsExistingOrCreatesNewPlace()
    {
        PlaceDTO dto = new PlaceDTO("Arena", "Main Ave");
        Place existing = new Place();
        existing.setName("Arena");

        when(placeRepository.findByName("Arena")).thenReturn(Optional.of(existing));

        Place result = placeService.findOrCreatePlace(dto);

        assertEquals("Arena", result.getName());
        verify(placeRepository, never()).save(any());
    }
}
