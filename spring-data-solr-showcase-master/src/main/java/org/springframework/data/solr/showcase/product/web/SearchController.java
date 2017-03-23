package org.springframework.data.solr.showcase.product.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.showcase.constants.Constants;
import org.springframework.data.solr.showcase.product.ProductService;
import org.springframework.data.solr.showcase.product.model.Product;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
@Controller
@Component
@Scope("prototype")
public class SearchController implements Constants {

	private ProductService productService;
	private Product product;
	
	@RequestMapping("/search")
	public String search(Model model, @RequestParam Map<String,String> requestParams,@PageableDefault(
			page = 0, size = ProductService.DEFAULT_PAGE_SIZE) Pageable pageable, HttpServletRequest request) {
		
		/*
		 * Fetching the request parameters 
		 */
		String query=requestParams.get(Constants.QUERY_STRING);
		String filterQuery=requestParams.get(Constants.QUERY_FIELD);
		String Id=requestParams.get(Constants.ID_FIELD_NAME);
		String Name=requestParams.get(Constants.NAME_FIELD_NAME);
		String Price=requestParams.get(Constants.PRICE_FIELD_NAME);
		String Popularity=requestParams.get(Constants.POPULARITY_FIELD_NAME);
		
		Collection<String> queryFilter = new ArrayList<String>();
		Collection<Object> productResult = new ArrayList<Object>();
		List<Product> productList = new ArrayList<Product>();
		
		/*
		 * The Indexed data is queried as per the user's inputs 
		 * 
		 */
		if(!StringUtils.isBlank(filterQuery) && !StringUtils.isBlank(query)){
			if(filterQuery.equalsIgnoreCase(Constants.NAME_FIELD_NAME)){
				productList=productService.findByName(query);
				model.addAttribute(Constants.PRODUCT, productService.findByName(query, pageable));
			}
			
			if(filterQuery.equalsIgnoreCase(Constants.CATEGORY)){
				productList = productService.findByCategories(query);
			}
			
			if(filterQuery.equalsIgnoreCase(Constants.ID_FIELD_NAME)){
				product=productService.findById(query);
				productList.add(product);
			}
				if(Name!=null)
					queryFilter.add(Name);
				if(Id!=null)
					queryFilter.add(Id);
				if(Price!=null)
					queryFilter.add(Price);
				if(Popularity!=null)
					queryFilter.add(Popularity);
				/*
				 * The Result set is filtered as per the user's inputs using Java Reflection
				 */
				if(!productList.isEmpty()){
					for (Product product : productList) {
						for (String field : queryFilter) {
							try {
								Method method = product.getClass().getMethod("get"+field);
								productResult.add(method.invoke(product));
							} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			
			/*
			 * The Final Result Set is sent to the Modal in the MVC Framework
			 */
			if(filterQuery.equalsIgnoreCase(Constants.CATEGORY)){
				model.addAttribute(Constants.PRODUCT, productService.findByCategories(query));
				model.addAttribute(Constants.PRODUCT_RESULT, productResult);
				return Constants.PRODUCT;
			}
			if(filterQuery.equalsIgnoreCase(Constants.ID_FIELD_NAME)){
				model.addAttribute(Constants.PRODUCT, product);
				model.addAttribute(Constants.PRODUCT_RESULT, productResult);
				return Constants.PRODUCT;
			}
			if(filterQuery.equalsIgnoreCase(Constants.NAME_FIELD_NAME)){
				model.addAttribute(Constants.PAGE, productService.findByName(query, pageable));
				model.addAttribute(Constants.PRODUCT_RESULT, productResult);
				return Constants.PRODUCT;
			}
		 }
		
		/*
		 * If the search query is blank,display all the indexed documents by default
		 */
			model.addAttribute(Constants.PAGE, productService.findByName(query, pageable));
			model.addAttribute(Constants.PAGEABLE, pageable);
			model.addAttribute(Constants.QUERY, query);
			return Constants.SEARCH;
	}	
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

}
