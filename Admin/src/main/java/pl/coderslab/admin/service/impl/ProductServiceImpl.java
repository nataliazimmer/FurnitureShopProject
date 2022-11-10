package pl.coderslab.admin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.admin.model.Product;
import pl.coderslab.admin.model.dto.ProductDto;
import pl.coderslab.admin.repository.ProductRepository;
import pl.coderslab.admin.service.ProductService;
import pl.coderslab.admin.utils.ImageUploadUtil;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUploadUtil imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for(Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(productDto.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImage(productDto.getImage());
            productDto.setActivated(productDto.isActivated());
            productDto.setDeleted(productDto.isDeleted());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = new Product();
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                if (imageUpload.uploadImage(imageProduct)) {
                    //System.out.println("Dodano");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = productRepository.getById(productDto.getId());
            if (imageProduct == null) {
                product.setImage(product.getImage());
            } else {
                if (imageUpload.checkExisted(imageProduct) == false) {
                    imageUpload.uploadImage(imageProduct);
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(product.getDescription());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(product.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.is_deleted());
        productDto.setActivated(product.is_activated());
        return productDto;
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    @Override
    public void availableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);

    }

}

