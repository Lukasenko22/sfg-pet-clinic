package lm.springframework.sfgpetclinic.services;

import lm.springframework.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService {

    Owner findById(Long id);
    Owner save(Owner owner);
    Set<Owner> findAll();
    Set<Owner> findByLastName(String lastName);
}
