/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.Type;
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
    @Basic(fetch = FetchType.EAGER)
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] content;
    
    private String mediaType;
    
    private Long size;

    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Account owner; 
    
    private int numLikes;
    
    @OneToMany(mappedBy = "commentedImage", cascade = CascadeType.REMOVE)
    @OrderBy("commentTime ASC")
    @EqualsAndHashCode.Exclude 
    Set<Comment> comments; 
}
