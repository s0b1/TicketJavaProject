package brainacad.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class EventCreationDTO
{
    private String name;
    private LocalDate eventDate;
    private PlaceDTO place;
    private List<TicketPackDTO> ticketPacks;
}
