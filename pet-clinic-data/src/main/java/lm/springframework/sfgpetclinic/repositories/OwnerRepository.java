package lm.springframework.sfgpetclinic.repositories;

import lm.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface OwnerRepository extends CrudRepository<Owner,Long> {
    Set<Owner> findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);
}
