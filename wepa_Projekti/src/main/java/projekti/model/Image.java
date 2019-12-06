/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author ptuomola
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image extends AbstractPersistable<Long> {
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
    
    private String mediaType;
    
    private Long size;

    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account owner; 
}
