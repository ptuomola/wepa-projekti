/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author ptuomola
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account extends AbstractPersistable<Long> {
    
    @NotEmpty
    @Column(unique = true)
    @Size(min = 3, max = 30)
    private String username;   
    
    @NotEmpty
    @Size(min = 3, max = 256)
    private String password;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @NotEmpty
    @Column(unique = true)
    @Pattern(regexp="[a-zA-Z0-9.-]+", message="The URL string contains illegal characters")
    @Size(min = 3, max = 30)
    private String urlString;
}
