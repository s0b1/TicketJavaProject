package brainacad.controllers;

import brainacad.dto.EventCreationDTO;
import brainacad.dto.TicketPackDTO;
import brainacad.entities.Event;
import brainacad.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public String listEvents(Model model)
    {
        List<Event> events = eventService.getUpcomingEvents();
        model.addAttribute("events", events);
        return "events/list";
    }

    @GetMapping("/add")
    public String addEventForm(Model model)
    {
        EventCreationDTO dto = new EventCreationDTO();

        dto.setTicketPacks(Arrays.asList(new TicketPackDTO(), new TicketPackDTO()));

        model.addAttribute("eventDto", dto);
        return "events/add";
    }

    @PostMapping("/add")
    public String createEvent(@ModelAttribute EventCreationDTO dto) {
        eventService.createEvent(dto);
        return "redirect:/events";
    }

    @GetMapping("/delete/confirm/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        Event event = eventService.getById(id);
        model.addAttribute("event", event);
        return "events/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id)
    {
        eventService.deleteById(id);
        return "redirect:/events";
    }

    @GetMapping("/details/{id}")
    public String viewEvent(@PathVariable Long id, Model model)
    {
        model.addAttribute("event", eventService.getById(id));
        return "events/view";
    }
}