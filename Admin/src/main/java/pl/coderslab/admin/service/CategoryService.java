package pl.coderslab.admin.service;

import pl.coderslab.admin.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void availableById(Long id);
    List<Category> findAllByActivated();

}
