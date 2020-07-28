package lm.springframework.sfgpetclinic.controllers;

import lm.springframework.sfgpetclinic.model.Owner;
import lm.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setDisallowedField(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String showfindOwnersPage(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId){
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner",ownerService.findById(ownerId));
        return modelAndView;
    }

    @GetMapping("")
    public String processFindOwnersForm(Owner owner, BindingResult bindingResult, Model model){
        if (owner.getLastName() == null){
            owner.setLastName("");
        }

        List<Owner> ownerList = ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");
        if (ownerList.isEmpty()){
            bindingResult.rejectValue("lastName","notFound","Not Found");
            return "owners/findOwners";
        } else if (ownerList.size() == 1){
            return "redirect:/owners/"+ownerList.get(0).getId();
        }
        model.addAttribute("selections",ownerList);
        return "owners/ownersList";
    }
}
