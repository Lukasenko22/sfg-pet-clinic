package lm.springframework.sfgpetclinic.services.map;

import lm.springframework.sfgpetclinic.model.Speciality;
import lm.springframework.sfgpetclinic.model.Vet;
import lm.springframework.sfgpetclinic.services.SpecialityService;
import lm.springframework.sfgpetclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private SpecialityService specialityService;

    @Autowired
    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet object) {

        if (object.getSpecialities().size() > 0){
            for (Speciality spec : object.getSpecialities()) {
                if (spec.getId() == null){
                    Speciality savedSpeciality = specialityService.save(spec);
                    spec.setId(savedSpeciality.getId());
                }
            }
        }
        return super.save(object);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
