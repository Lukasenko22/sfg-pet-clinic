package lm.springframework.sfgpetclinic.services.map;

import lm.springframework.sfgpetclinic.model.Owner;
import lm.springframework.sfgpetclinic.model.Pet;
import lm.springframework.sfgpetclinic.services.OwnerService;
import lm.springframework.sfgpetclinic.services.PetService;
import lm.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default","map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    @Autowired
    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner save(Owner object) {
        if (object != null){
            for (Pet pet : object.getPets()) {
                if (pet.getPetType() != null) {
                    if (pet.getPetType().getId() == null){
                        pet.setPetType(petTypeService.save(pet.getPetType()));
                    }
                } else {
                    throw new RuntimeException("Pet Type is required");
                }

                if (pet.getId() == null){
                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                }
            }
            return super.save(object);
        }
        return null;
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Set<Owner> findByLastName(String lastName) {
        Set<Owner> owners = this.findAll().stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toSet());
        return owners.isEmpty() ? null : owners;
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastname) {
        // todo - not implemented
        return null;
    }
}
