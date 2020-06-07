package lm.springframework.sfgpetclinic.services;

import lm.springframework.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long>{

    Set<Owner> findByLastName(String lastName);
}
