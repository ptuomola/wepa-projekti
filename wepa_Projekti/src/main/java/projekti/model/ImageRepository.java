/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.model;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ptuomola
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    @EntityGraph(attributePaths = {"comments"})
    public List<Image> findByOwnerOrderByIdAsc(Account owner);
    public Long countByOwner(Account owner);
}
