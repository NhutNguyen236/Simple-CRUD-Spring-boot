package com.example.cmsshoppingcart.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cmsshoppingcart.models.PageRepository;
import com.example.cmsshoppingcart.models.data.Page;

/**
 * AdminPagesController
 */
@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    @Autowired
    private PageRepository pageRepo;

    // public AdminPagesController(PageRepository pageRepo) {
    //     this.pageRepo = pageRepo;
    // }

    @GetMapping
    public String index(Model model){
        
        List<Page> pages = pageRepo.findAll();
        
        model.addAttribute("pages", pages);
        
        return "admin/pages/index";
    }
    
    @GetMapping("/add")
    public String add(@ModelAttribute Page page){
        //model.addAttribute("page", new Page());
        return "admin/pages/add";
    }

    // POST adding page
    @PostMapping("/add")
    public String add(@Valid Page page, BindingResult bidingResult, RedirectAttributes redirectAttributes, Model model){
        if(bidingResult.hasErrors()){
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().replace(" ", "-");

        // find if slug exists or not
        Page slugExists = pageRepo.findBySlug(slug);

        if(slugExists != null){
            redirectAttributes.addFlashAttribute("message", "Slug exists, pick something else");
            redirectAttributes.addFlashAttribute("alertClass", "alert-dangger");
        }
        else{
            page.setSlug(slug);
            page.setSorting(100);

            pageRepo.save(page);
        }
        return "redirect:/admin/pages/add";
    }

    // GET edit page
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        
        Page page = pageRepo.getOne(id);
        
        model.addAttribute("page", page);

        return "admin/pages/edit";

    }

    // POST edit page
    @PostMapping("/edit")
    public String edit(@Valid Page page, BindingResult bidingResult, RedirectAttributes redirectAttributes, Model model){
        if(bidingResult.hasErrors()){
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message", "Page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ", "-");

        // find if slug exists or not
        Page slugExists = pageRepo.findBySlug(page.getId(), slug);

        if(slugExists != null){
            redirectAttributes.addFlashAttribute("message", "Slug exists, pick something else");
            redirectAttributes.addFlashAttribute("alertClass", "alert-dangger");
        }
        else{
            page.setSlug(slug);

            pageRepo.save(page);
        }
        return "redirect:/admin/pages/edit/" + page.getId();
    }

    // GET for delete a page
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes){
        
        pageRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Page deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        
        return "redirect:/admin/pages";
    }
}