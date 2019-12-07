/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class Comment extends AbstractPersistable<Long> {
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "commented_image_id", nullable = true)
    @EqualsAndHashCode.Exclude 
    private Image commentedImage;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "commented_message_id", nullable = true)
    @EqualsAndHashCode.Exclude 
    private Message commentedMessage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commenting_account_id", nullable = false)
    @EqualsAndHashCode.Exclude 
    private Account commentingAccount;

    private Date commentTime;
    
    private String commentText;

}
