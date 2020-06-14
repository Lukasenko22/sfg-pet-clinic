package lm.springframework.sfgpetclinic.services.springdatajpa;

import lm.springframework.sfgpetclinic.model.Speciality;
import lm.springframework.sfgpetclinic.repositories.SpecialityRepository;
import lm.springframework.sfgpetclinic.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("spring-data-jpa")
public class SpecialityJPAService implements SpecialityService{

    private final SpecialityRepository specialityRepository;

    @Autowired
    public SpecialityJPAService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specs = new HashSet<>();
        specialityRepository.findAll().forEach(specs::add);
        return specs;
    }

    @Override
    public Speciality findById(Long aLong) {
        return specialityRepository.findById(aLong).orElse(null);
    }

    @Override
    public Speciality save(Speciality object) {
        return specialityRepository.save(object);
    }

    @Override
    public void delete(Speciality object) {
        specialityRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        specialityRepository.deleteById(aLong);
    }
}
