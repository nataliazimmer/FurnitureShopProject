package pl.coderslab.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.admin.model.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double costPrice;
    private int currentQuantity;
    private Category category;
    private String image;
    private boolean activated;
    private boolean deleted;
}
