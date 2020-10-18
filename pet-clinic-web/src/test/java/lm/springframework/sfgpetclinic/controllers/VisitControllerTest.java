package lm.springframework.sfgpetclinic.controllers;

import lm.springframework.sfgpetclinic.model.Owner;
import lm.springframework.sfgpetclinic.model.Pet;
import lm.springframework.sfgpetclinic.model.Visit;
import lm.springframework.sfgpetclinic.services.PetService;
import lm.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    private VisitService visitService;

    @Mock
    private PetService petService;

    @InjectMocks
    private VisitController visitController;

    private MockMvc mockMvc;

    List<Pet> pets;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        pets = new ArrayList<>();
        pets.add(Pet.builder().id(1L).name("Test Dog").owner(Owner.builder().id(1L).build()).build());
        pets.add(Pet.builder().id(2L).name("Test Cat").owner(Owner.builder().id(1L).build()).build());
    }


    @Test
    void processAddVisitForm() throws Exception {
        //given
        Visit visit = new Visit();
        visit.setId(1L);
        visit.setPet(pets.get(0));
        visit.setDescription("Add Test Visit");
        visit.setDate(LocalDate.parse("2020-10-18"));
        pets.get(0).getVisits().add(visit);


        //when
        when(petService.findById(anyLong())).thenReturn(pets.get(0));
        when(visitService.save(any())).thenReturn(visit);

        //then
        mockMvc.perform(post("/owners/1/pets/1/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService,times(1)).findById(anyLong());
        verify(visitService,times(1)).save(any());
    }
}