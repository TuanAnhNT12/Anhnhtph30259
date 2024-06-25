package com.example.demo.controller;

import com.example.demo.custom.BrandCustom;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    Product_BrandRepository product_brandRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    SubcategoryRepository subcategoryRepository;

    @GetMapping("/hien-thi-product")
    public String hienthi(Model model,
                          @RequestParam(defaultValue = "0") Integer p,
                          @RequestParam(value = "keySearch", required = false) String keySearch,
                          @RequestParam(value = "statusSearch", required = false) String statusSearch,
                          @RequestParam(value = "cateSearch", required = false) String cateSearch,
                          @ModelAttribute("product") Product product) {
        List<Product> lstP = productRepository.findAll();
        List<Product_brand> lstProduct_brands= product_brandRepository.findAll();
        List<BrandCustom> lstBrandCustoms= new ArrayList<>();
        for(Product_brand b: lstProduct_brands){
            BrandCustom brandCustom= new BrandCustom();
            brandCustom.setId(b.getBrand_id().getId());
            brandCustom.setName(b.getBrand_id().getBrand_name());
            brandCustom.setIdproduct(b.getProduct_id().getId());
            lstBrandCustoms.add(brandCustom);
        }
        Pageable pageable = PageRequest.of(p, lstP.size());
        Page<Product> page = productRepository.findAllSearch(keySearch, statusSearch, cateSearch, pageable);
        model.addAttribute("page", page);
        model.addAttribute("product_brand", product_brandRepository.findAll());
        model.addAttribute("status", statusRepository.findAll());
        model.addAttribute("brand", brandRepository.findAll());
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("statusSearch", statusSearch);
        model.addAttribute("cateSearch", cateSearch);
        model.addAttribute("keySearch", keySearch);
        model.addAttribute("subcategory", subcategoryRepository.findAll());
        model.addAttribute("brandCustom",lstBrandCustoms);
        return "admin/HienThi";
    }

    @PostMapping("/them-product")
    public String them(@ModelAttribute("product") Product product,
                       @RequestParam("brand") String brand,
                       @RequestParam("subcategory") String subcategory) {
        Integer IdBrand = Integer.parseInt(brand);
        Integer IdSubcate = Integer.parseInt(subcategory);

        Status status = statusRepository.findStatusById(1);
        Sub_category sub_category = subcategoryRepository.findSub_categoryById(IdSubcate);
        product.setStatus_id(status);
        product.setSubcate_id(sub_category);
        productRepository.save(product);

        Product productNew = productRepository.findProductMax();
        Brand brand1 = brandRepository.findBrandById(IdBrand);
        Product_brand product_brand = new Product_brand();
        product_brand.setProduct_id(productNew);
        product_brand.setBrand_id(brand1);
        product_brandRepository.save(product_brand);
        return "redirect:/hien-thi-product";
    }
    @RequestMapping("/delete-product/{Id}")
    public String xoa(@PathVariable("Id") Integer Id){
        Product product= productRepository.findProductById(Id);
        List<Product_brand> lst= product_brandRepository.findProduct_brandByIdProduct(Id);
        for(Product_brand p : lst){
            product_brandRepository.delete(p);
        }
        productRepository.delete(product);
        return "redirect:/hien-thi-product";
    }
    @PostMapping("/cap-nhat-product/{Id}")
    public String capnhat(
                       @RequestParam("brandcapnhat") String brand,
                       @RequestParam("subcategorycapnhat") String subcategory,
                          @RequestParam("statuscapnhat") String statuscapnhat,
                          @RequestParam("namecapnhat") String namecapnhat,
                       @RequestParam("colorcapnhat") String colorcapnhat,
                        @RequestParam("quantitycapnhat") String quantitycapnhat,
                       @RequestParam("sell_pricecapnhat") String sell_pricecapnhat,
                       @RequestParam("origin_pricecapnhat") String origin_pricecapnhat,
                          @PathVariable("Id") Integer Id) {
        Product product= new Product();
        product.setId(Id);
        product.setProduct_name(namecapnhat.trim());
        product.setColor(colorcapnhat.trim());
        product.setQuantity(Integer.parseInt(quantitycapnhat));
        product.setSell_price(new BigDecimal(sell_pricecapnhat));
        product.setOrigin_price(new BigDecimal(origin_pricecapnhat));

        Integer IdBrand = Integer.parseInt(brand);
        Integer IdSubcate = Integer.parseInt(subcategory);
        Integer IdStatus= Integer.parseInt(statuscapnhat);

        Status status = statusRepository.findStatusById(IdStatus);
        Sub_category sub_category = subcategoryRepository.findSub_categoryById(IdSubcate);
        product.setStatus_id(status);
        product.setSubcate_id(sub_category);
        productRepository.save(product);

        List<Product_brand> lstProduct_brands= product_brandRepository.findProduct_brandByIdProduct(product.getId());
        for (Product_brand p: lstProduct_brands) {
            p.setId(p.getId());
            p.setProduct_id(product);
            p.setBrand_id(brandRepository.findBrandById(IdBrand));
            product_brandRepository.save(p);
        }
        return "redirect:/hien-thi-product";
    }
}
