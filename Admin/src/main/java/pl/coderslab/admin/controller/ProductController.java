package pl.coderslab.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.admin.model.Category;
import pl.coderslab.admin.model.dto.ProductDto;
import pl.coderslab.admin.service.CategoryService;
import pl.coderslab.admin.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/products")
    public String products (Model model) {
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("products", productDtoList);
        model.addAttribute("size", productDtoList.size());
        return "products";

    }

    @GetMapping("/add-product")
    public String addProductForm (Model model) {
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "add-product";

    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product")ProductDto productDto,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes attributes) {
        try {
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Dodano produkt");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Błąd dodania produktu");
        }
        return "redirect:/products";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        List<Category> categories = categoryService.findAllByActivated();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct")MultipartFile imageProduct,
                                RedirectAttributes attributes) {
        try {
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Zmodyfikowano zmiany");
        } catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Błąd wprowadzania zmian");
        }
        return "redirect:/products";

    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Usunięto pomyślnie");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Błąd usuwania");
        }
        return "redirect:/products";
    }


    @RequestMapping(value = "/available-product/{id}", method = {RequestMethod.PUT , RequestMethod.GET})
    public String availableProduct(@PathVariable("id")Long id, RedirectAttributes attributes){
        try {
            productService.availableById(id);
            attributes.addFlashAttribute("success", "Dostępny");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Nie udało się przywrócić");
        }
        return "redirect:/products";
    }

}

