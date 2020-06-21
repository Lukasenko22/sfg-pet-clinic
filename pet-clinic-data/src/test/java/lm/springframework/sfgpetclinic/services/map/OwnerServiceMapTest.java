package lm.springframework.sfgpetclinic.services.map;

import lm.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    Long ownerId = 1L;
    String lastName = "Smith";
    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeMapService(), new PetServiceMap());
        ownerServiceMap.save(Owner.builder().id(ownerId).lastName("Smith").build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();

        assertEquals(1,ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(ownerId);

        assertEquals(1L,owner.getId());
    }

    @Test
    void saveExistingId() {
        Owner owner2 = Owner.builder().id(2L).build();
        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(2L,savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner owner = Owner.builder().build();
        Owner savedOwner = ownerServiceMap.save(owner);

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(ownerId));

        assertEquals(0,ownerServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);

        assertEquals(0,ownerServiceMap.findAll().size());
    }

    @Test
    void findByLastName() {
        Set<Owner> owners = ownerServiceMap.findByLastName(lastName);

        assertTrue(!owners.isEmpty());
    }

    @Test
    void findByLastNameNotFound() {
        Set<Owner> owners = ownerServiceMap.findByLastName("foo");

        assertNull(owners);
    }

}