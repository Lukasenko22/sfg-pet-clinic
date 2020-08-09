package lm.springframework.sfgpetclinic.controllers;

import lm.springframework.sfgpetclinic.model.Owner;
import lm.springframework.sfgpetclinic.model.Pet;
import lm.springframework.sfgpetclinic.model.PetType;
import lm.springframework.sfgpetclinic.services.OwnerService;
import lm.springframework.sfgpetclinic.services.PetService;
import lm.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    PetTypeService petTypeService;

    @Mock
    PetService petService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    List<PetType> petTypes;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        petTypes = new ArrayList<>();
        petTypes.add(PetType.builder().id(1L).name("Cat").build());
        petTypes.add(PetType.builder().id(2L).name("Dog").build());
        petTypes.add(PetType.builder().id(3L).name("Bird").build());
    }

    @Test
    void processCreatePetForm() throws Exception {
        //given
        Owner owner = Owner.builder().id(1L).build();

        Pet newPet = new Pet();
        newPet.setId(1L);
        newPet.setName("Test Pet");
        newPet.setPetType(petTypes.get(2));

        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petService.save(any())).thenReturn(newPet);

        //then
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService,times(1)).save(any());
        verify(ownerService,times(1)).findById(anyLong());
    }

    @Test
    void processUpdatePetForm() throws Exception {
        //given
        Pet existingPet = new Pet();
        existingPet.setId(1L);
        existingPet.setName("Existing Pet");
        existingPet.setPetType(petTypes.get(2));

        Owner owner = Owner.builder().id(1L).build();
        owner.getPets().add(existingPet);

        Pet editedPet = new Pet();
        existingPet.setId(1L);
        existingPet.setName("Edited Existing Pet");
        existingPet.setPetType(petTypes.get(2));

        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petService.save(any())).thenReturn(editedPet);


        //then
        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService,times(1)).save(any());
        verify(ownerService,times(1)).findById(anyLong());
    }

}