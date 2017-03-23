package org.springframework.data.solr.showcase.product.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.solr.showcase.product.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
@Scope("prototype")
public class ProductController {

	private ProductService productService;

	@RequestMapping("/product/{id}")
	public String search(Model model, @PathVariable("id") String id, HttpServletRequest request) {
		model.addAttribute("product", productService.findById(id));
		return "product";
	}

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

}
