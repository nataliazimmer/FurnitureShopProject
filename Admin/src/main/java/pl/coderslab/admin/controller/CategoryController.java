package pl.coderslab.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.admin.model.Category;
import pl.coderslab.admin.service.CategoryService;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String add (@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes){
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Dodano pomyślnie");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Nie dodano pomyślnie - powtarzające się dane");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Błąd");
        }
        return "redirect:/categories";

    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes){
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success","Uaktualniono pomyślnie");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Nie uaktualniono");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Błąd");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes){
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Usunięto pomyślnie");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Nie usunięto");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/available-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String available(Long id, RedirectAttributes attributes){
        try {
            categoryService.availableById(id);
            attributes.addFlashAttribute("success", "Dostępny");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Nie udało się przywrócić");
        }
        return "redirect:/categories";
    }


}