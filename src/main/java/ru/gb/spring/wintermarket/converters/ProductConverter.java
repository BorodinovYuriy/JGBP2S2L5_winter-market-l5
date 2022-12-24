package ru.gb.spring.wintermarket.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.spring.wintermarket.dto.ProductDto;
import ru.gb.spring.wintermarket.entity.Category;
import ru.gb.spring.wintermarket.entity.Product;
import ru.gb.spring.wintermarket.exceptions.ResourceNotFoundException;
import ru.gb.spring.wintermarket.services.CategoryService;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    @Transactional
    public Product dtoToEntity(ProductDto productDto){

        Category category = categoryService.
                findByTitle(productDto.getCategoryTitle()).
                orElse(categoryService.
                        saveCategory(productDto.getCategoryTitle()));

        Product p = new Product();
                p.setId(productDto.getId());
                p.setTitle(productDto.getTitle());
                p.setPrice(productDto.getPrice());
                p.setCategory(category);

        return p;
    }

    public ProductDto entityToDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getCategory().getTitle()
        );
    }
}

