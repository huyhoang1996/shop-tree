package com.softech.shop.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softech.shop.model.Categories;
import com.softech.shop.model.Products;
import com.softech.shop.services.CategoryService;
import com.softech.shop.services.LevelService;
import com.softech.shop.services.ProductService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public String index(ModelMap modelMap) {
		List<Categories> list = categoryService.findAll();

		modelMap.addAttribute("listCategory", list);

		return "admin/listCategory";
	}

	@GetMapping("/new")
	public String addCategory(Model model) {
		model.addAttribute("category", new Categories());
		return "admin/addOrSaveCategory";
	}

	@GetMapping("/edit/{id}")
	public String editCategory(Model model, @PathVariable String id) {
		model.addAttribute("category", categoryService.findById(Integer.parseInt(id)));
		return "admin/addOrSaveCategory";
	}

	@PostMapping("/save")
	public String saveCategory(Model model, @Valid Categories category, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("category", new Categories());
			model.addAttribute("errorMsg", "Có lỗi xãy ra, vui lòng thử lại");
			return "admin/addOrSaveCategory";
		}
		categoryService.save(category);
		redirectAttributes.addFlashAttribute("successMsg", "Xử lý thành công");
		return "redirect:/categories";

	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(Model model, @PathVariable String id) {
		try {
			categoryService.deleteById(Integer.parseInt(id));
			model.addAttribute("successMsg", "Đã xóa Category thành công");
		} catch (Exception ex) {
			model.addAttribute("errorMsg", "Category có liên kết khóa ngoại nên không thể xóa. Vui lòng xóa tất cả các sản phẩm có chuyên mục này rồi hãy thực hiện xóa Category này");
		}
		List<Categories> list = categoryService.findAll();

		model.addAttribute("listCategory", list);
		return "admin/listCategory";
	}


//	@GetMapping("view/{productId}")
//	public String viewProduct(ModelMap modelMap, @PathVariable String productId) {
//		Optional<Products> prod = productService.findById(Integer.parseInt(productId));
//		if (prod != null) {
//			modelMap.addAttribute("listProduct", prod.get());
//		} else {
//		}
//		return "admin/viewProduct";
//	}

}
