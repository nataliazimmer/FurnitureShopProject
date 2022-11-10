package pl.coderslab.admin.service;

import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.admin.model.Product;
import pl.coderslab.admin.model.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();
    Product save (MultipartFile imageProduct, ProductDto productDto);
    Product update (MultipartFile imageProduct, ProductDto productDto);
    ProductDto getById(Long id);
    void deleteById (Long id);
    void availableById (Long id);


}