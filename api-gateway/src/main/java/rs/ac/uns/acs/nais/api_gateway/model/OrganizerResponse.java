package rs.ac.uns.acs.nais.api_gateway.model;

import lombok.Data;
import rs.ac.uns.acs.nais.api_gateway.enums.Role;

@Data
public class OrganizerResponse {
    
    private Integer id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String biography;
    
}
