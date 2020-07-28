package lm.springframework.sfgpetclinic.controllers;

import lm.springframework.sfgpetclinic.model.Owner;
import lm.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    private Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void showOwner() throws Exception {
        //given
        Owner owner = Owner.builder().id(1L).build();

        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);

        //then
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("id",is(1L))));

        verify(ownerService,times(1)).findById(anyLong());
    }

    @Test
    public void findOwnersReturnMoreThanOneOwner() throws Exception {
        //given
        List<Owner> ownerList = new ArrayList<>();
        ownerList.addAll(owners);

        //when
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);

        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections",hasSize(2)));
    }

    @Test
    public void findOwnersReturnOneOwner() throws Exception {
        //given
        List<Owner> ownerList = new ArrayList<>();
        ownerList.add(Owner.builder().id(1L).build());

        //when
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);

        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void findOwnersReturnEmpty() throws Exception {
        //given
        List<Owner> ownerList = new ArrayList<>();

        //when
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);

        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeHasFieldErrors("owner","lastName"))
                .andExpect(model().attributeHasFieldErrorCode("owner","lastName","notFound"));
    }
}