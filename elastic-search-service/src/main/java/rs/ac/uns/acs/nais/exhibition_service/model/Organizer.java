package rs.ac.uns.acs.nais.exhibition_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Organizer {

    //private String username; nije mi bas imalo smisla da pretrazujemo po username-u organizatora ili kustosa pa sam uklonio to

    private String firstName;

    private String lastName;
    
}
