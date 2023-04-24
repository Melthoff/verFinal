package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.dto.OrderSearchOptions;
import com.example.springsecurityapplication.dto.StatusChangeDto;
import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Category;
import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.services.OrdersService;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PersonService personService;
    @Autowired
    private OrdersService ordersService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("admin/product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }

        if (file_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);

        }

        if (file_two != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if (file_three != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if (file_four != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if (file_five != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin/adminEditProducts";
    }


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "admin/admin";
    }

    @GetMapping("/admin/adminEditProducts")
    public String adminEditProducts(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "admin/adminEditProducts";
    }

    @GetMapping("/admin/adminEditOrders")
    public String adminEditOrders(Model model) {
        model.addAttribute("orders", ordersService.getAllOrders());
        model.addAttribute("newStatus", new StatusChangeDto());
        model.addAttribute("orderSearch", new OrderSearchOptions());
        return "admin/adminEditOrders";
    }

    @GetMapping("/admin/adminEditUsers")
    public String adminEditUsers(Model model) {
        model.addAttribute("users", personService.getAll());
        return "admin/adminEditUsers";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    @PostMapping("admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin/adminEditProducts";
    }

    @PostMapping("/admin/order/{id}/setStatus")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("newStatus") StatusChangeDto newStatus) {
        ordersService.setStatus(id, newStatus.getNewStatus());
        return "redirect:/admin/adminEditOrders";
    }

    @PostMapping("/admin/order/search")
    public String editProduct( @ModelAttribute("orderSearch") OrderSearchOptions searchOptions, Model model) {
        model.addAttribute("orders", ordersService.findAll(searchOptions));
        model.addAttribute("newStatus", new StatusChangeDto());
        model.addAttribute("orderSearch",searchOptions);
        return "admin/adminEditOrders";
    }

    @GetMapping("/admin/user/makeAdmin/{id}")
    public String makeAdmin(@PathVariable("id") int id) {
        personService.makeAdmin(id);
        return "redirect:/admin/adminEditUsers";
    }

    @GetMapping("/admin/user/unMakeAdmin/{id}")
    public String unMakeAdmin(@PathVariable("id") int id) {
        personService.unMakeAdmin(id);
        return "redirect:/admin/adminEditUsers";
    }
}
