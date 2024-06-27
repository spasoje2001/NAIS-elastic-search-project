package rs.ac.uns.acs.nais.api_gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import rs.ac.uns.acs.nais.api_gateway.model.Event;
import rs.ac.uns.acs.nais.api_gateway.model.EventElastic;
import rs.ac.uns.acs.nais.api_gateway.model.EventResponse;
import rs.ac.uns.acs.nais.api_gateway.model.Organizer;
import rs.ac.uns.acs.nais.api_gateway.model.Room;
import rs.ac.uns.acs.nais.api_gateway.service.ElasticService;
import rs.ac.uns.acs.nais.api_gateway.service.RelationalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/saga")
public class SAGAController {

    @Autowired
    private RelationalService relationalService;
    
    @Autowired
    private ElasticService elasticService;

    @PostMapping("/create-event")
    public Mono<ResponseEntity<?>> createEvent(@RequestBody Event entity) {
        return relationalService.createEvent(entity)
                .flatMap(result -> elasticService.createEvent(mapToElastic(result))
                .map(result1 -> ResponseEntity.ok().body(result))
                .onErrorResume(error -> relationalService.compensateCreateEvent(result.getId())
                    .then(Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()))));
    }

    private EventElastic mapToElastic(EventResponse eventResponse) {
        var eventElastic = new EventElastic();

        eventElastic.setDescription(eventResponse.getDescription());
        eventElastic.setDurationMinutes(eventResponse.getDurationMinutes());
        eventElastic.setName(eventResponse.getName());
        eventElastic.setOrganizer(new Organizer(eventResponse.getOrganizer().getFirstName(), eventResponse.getOrganizer().getLastName()));
        eventElastic.setPrice(eventResponse.getPrice());
        eventElastic.setRoom(new Room(eventResponse.getRoomReservation().getRoom().getFloor(), eventResponse.getRoomReservation().getRoom().getNumber()));
        eventElastic.setStartDateTime(eventResponse.getStartDateTime().toLocalDate());

        return eventElastic;
    }
    

    
}
