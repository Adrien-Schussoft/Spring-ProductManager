package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.adrien.pmanager.Product;
import com.adrien.pmanager.ProductService;

@Component
@Controller
public class ApplicationController {

	@Autowired
	private ProductService service;
	
	@Bean
	public ProductService ps() {
		return new ProductService();
	}
	
		
	@RequestMapping("/")
	public String HomePage(Model model) {
		List<Product> listProducts =service.listAll();
		model.addAttribute("listProducts", listProducts);		
		return "index";
	}
	
	@RequestMapping("/new")
	public String newProductPage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product")Product product) {
		service.save(product);
		return "redirect:/";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = service.get(id);
		mav.addObject("product", product);
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name="id")int id) {
		service.delete(id);
		return "redirect:/";
	}
}
