package fi.haagahelia.ecommerce.service;

import fi.haagahelia.ecommerce.dto.CategoryDTO;
import fi.haagahelia.ecommerce.dto.OrderProductDTO;
import fi.haagahelia.ecommerce.dto.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import fi.haagahelia.ecommerce.domain.Category;
import fi.haagahelia.ecommerce.domain.CategoryRepository;
import fi.haagahelia.ecommerce.domain.OrderProduct;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryDTO convertToCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId().toString());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    private OrderProductDTO convertToOrderProductDTO(OrderProduct orderProduct) {
        if (orderProduct == null) {
            return null;
        }

        OrderProductDTO dto = new OrderProductDTO();
        dto.setId(orderProduct.getId().toString());
        dto.setProductId(orderProduct.getProduct().getId().toString());
        dto.setProductName(orderProduct.getProduct().getName());
        dto.setQuantity(orderProduct.getQuantity());
        return dto;
    }

    private ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId().toString());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(convertToCategoryDTO(product.getCategory()));
        dto.setOrderProducts(product.getOrderProducts() != null ? product.getOrderProducts().stream()
                .map(this::convertToOrderProductDTO)
                .filter(orderProductDTO -> orderProductDTO != null) // Filter out null values
                .collect(Collectors.toList()) : null);
        return dto;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setImageUrl(productDTO.getImageUrl());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(categoryRepository.findById(new ObjectId(productDTO.getCategory().getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + productDTO.getCategory().getId())));
        return product;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(new ObjectId(id));
    }

    public ProductDTO createNewProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        productRepository.save(product);
        return convertToDTO(product);
    }

    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setCategory(categoryRepository.findById(new ObjectId(productDTO.getCategory().getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + productDTO.getCategory().getId())));

        productRepository.save(existingProduct);
        return convertToDTO(existingProduct);
    }
}