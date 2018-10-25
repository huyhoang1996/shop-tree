package com.softech.shop.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softech.shop.model.Categories;
import com.softech.shop.model.Images;
import com.softech.shop.model.Products;
import com.softech.shop.services.CategoryService;
import com.softech.shop.services.ImageService;
import com.softech.shop.services.LevelService;
import com.softech.shop.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ImageService imageService;

	private static String UPLOADED_FOLDER = "C://images//";

	@GetMapping("")
	public String index(ModelMap modelMap) {
		List<Products> list = productService.findAll();

		modelMap.addAttribute("listProduct", list);

		return "admin/listProduct";
	}

	@GetMapping("/new")
	public String addProduct(Model model) {
		Products prod = new Products();
		model.addAttribute("product", prod);
		model.addAttribute("type", "new");
		model.addAttribute("category", categoryService.findAll());
		return "admin/addOrSaveProduct";
	}

	@GetMapping("/edit/{id}")
	public String editProduct(Model model, @PathVariable String id) {
		Optional<Products> products = productService.findById(Integer.parseInt(id));
		model.addAttribute("product", products);
		List<Images> imagesCol = (List<Images>) imageService.findByProductId(Integer.parseInt(id));
		model.addAttribute("imagesCol", imagesCol);
		model.addAttribute("category", categoryService.findAll());
		model.addAttribute("type", "edit");
		return "admin/addOrSaveProduct";
	}

	@PostMapping("/save")
	public String saveProduct(Model model, @Valid Products product, BindingResult result,
			RedirectAttributes redirectAttributes, @RequestParam("images") MultipartFile[] files, HttpServletRequest req) {
		String type = req.getParameter("type");
		if (result.hasErrors()) {
			Products prod = new Products();
			model.addAttribute("product", prod);
			model.addAttribute("category", categoryService.findAll());
			model.addAttribute("errorMsg", "Có lỗi xãy ra, vui lòng thử lại");
			return "admin/addOrSaveProduct";
		}
		ClassLoader classLoader = getClass().getClassLoader();
		File fileUrl = new File(classLoader.getResource("").getFile());
		String pathImage = fileUrl.getPath();
		String pathImageBuild = "";
		pathImage = pathImage.replace("\\target\\classes", "\\src\\main\\resources\\static\\images\\product");
		pathImageBuild = pathImage.replace("\\src\\main\\resources\\static\\images\\product", "\\target\\classes\\static\\images\\product");
		if (files[0].equals("") && type.equals("add")) {
			model.addAttribute("product", product);
			model.addAttribute("category", categoryService.findAll());
			model.addAttribute("errorMsg", "Bạn chưa chọn hình ảnh");
			return "admin/addOrSaveProduct";
		}
		Products products = productService.save(product);
		if(!files[0].equals("")) {
			Collection<Images> imagesCollection = new ArrayList<>();
			products.setStatus(true);
			int index = 0;
			try {
				for (MultipartFile file : files) {
					byte[] bytes = file.getBytes();
					Images image = new Images();
					Path path = Paths.get(pathImage + "\\" +file.getOriginalFilename());
					Path pathBuild = Paths.get(pathImageBuild + "\\" +file.getOriginalFilename());
					image.setProductId(products);
                                        image.setUrl(file.getOriginalFilename());
//					image.setUrl("/images/product/" + file.getOriginalFilename());//xóa đường dẫn "/images/product/" để lưu vào data cho giống a đạt 
                                        //bên listProduct a đổi đường dẫn cho hiển thị rồi nên e xóa cái "/images/product/" là ok
					Files.write(path, bytes);
					Files.write(pathBuild, bytes);
					if (type.equals("edit") && index == 0) {
						List<Images> imagesCol = (List<Images>) imageService.findByProductId(products.getProductId());
						for (Images item : imagesCol) {
							imageService.deleteById(item.getImageId());
						}
						index = 1;
					}
					Images images = imageService.save(image);
					imagesCollection.add(images);
				}
			} catch (IOException e) {
				if (type.equals("add")) {
					productService.deleteById(product.getProductId());
				}
				model.addAttribute("product", product);
				model.addAttribute("category", categoryService.findAll());
				model.addAttribute("errorMsg", "He thong khong the xu ly ");
				return "admin/addOrSaveProduct";
			}
			if (type.equals("edit") && !files[0].equals("") || type.equals("add")) {
				products.setImagesCollection(imagesCollection);
			}
		}
		productService.save(products);
		redirectAttributes.addFlashAttribute("successMsg", "Xử lý thành công");
		return "redirect:/products";
	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
		try {
			Optional<Products> products = productService.findById(Integer.parseInt(id));
			Products product = products.get();
			product.setStatus(false);
			productService.save(product);
			redirectAttributes.addFlashAttribute("successMsg", "Đã xóa Sản phẩm thành công");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMsg", "Sản phẩm có liên kết khóa ngoại nên không thể xóa. Vui lòng xóa cả các dữ liệu có liên quan đến sản phẩm này này rồi hãy thực hiện xóa Sản phẩm này");
			
		}

		return "redirect:/products";
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
