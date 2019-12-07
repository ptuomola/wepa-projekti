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
@Table(name = "LIKES")
public class Like extends AbstractPersistable<Long> {
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "liked_image_id", nullable = true)
    @EqualsAndHashCode.Exclude 
    private Image likedImage;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "liked_message_id", nullable = true)
    @EqualsAndHashCode.Exclude 
    private Message likedMessage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "liking_account_id", nullable = false)
    @EqualsAndHashCode.Exclude 
    private Account likingAccount;
}
