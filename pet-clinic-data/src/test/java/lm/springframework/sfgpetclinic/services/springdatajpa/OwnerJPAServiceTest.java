package lm.springframework.sfgpetclinic.services.springdatajpa;

import lm.springframework.sfgpetclinic.model.Owner;
import lm.springframework.sfgpetclinic.repositories.OwnerRepository;
import lm.springframework.sfgpetclinic.repositories.PetRepository;
import lm.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerJPAServiceTest {

    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerJPAService ownerJPAService;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
        Set<Owner> ownersReturned = new HashSet<>();
        ownersReturned.add(returnOwner);

        when(ownerRepository.findByLastName(any())).thenReturn(ownersReturned);

        Set<Owner> owners = ownerJPAService.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME,owners.iterator().next().getLastName());
        verify(ownerRepository,times(1)).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> returnedOwners = ownerJPAService.findAll();

        assertNotNull(returnedOwners);
        assertEquals(2,returnedOwners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(returnOwner));

        Owner owner = ownerJPAService.findById(1L);
        assertNotNull(owner);
        assertEquals(1L,owner.getId());
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(2L).lastName("Molcan").build();
        when(ownerRepository.save(any())).thenReturn(ownerToSave);

        Owner savedOwner = ownerJPAService.save(ownerToSave);
        assertNotNull(savedOwner);
    }

    @Test
    void delete() {
        ownerJPAService.delete(returnOwner);
        verify(ownerRepository,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerJPAService.deleteById(1L);
        verify(ownerRepository,times(1)).deleteById(any());
    }

}