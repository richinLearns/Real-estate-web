package ca.sheridancollege.richin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.richin.model.Property;
import ca.sheridancollege.richin.service.PropertyService;
import jakarta.validation.Valid;

@Controller
public class PropertyController {
	private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("properties", service.getAll());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("property", new Property());
        return "add";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Property p = service.getById(id);
        if (p == null) {
            return "redirect:/";
        }
        model.addAttribute("property", p);
        return "edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Property property, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return property.getId() == null ? "add" : "edit";
        }
        service.save(property);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }

}
