package com.poc.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.lowagie.text.DocumentException;
import com.poc.model.LoginUser;
import com.poc.service.CustomerService;
import com.poc.service.PdfService;

@Controller
public class CustomerController {

	private CustomerService customerService;
	private PdfService pdfService;

	@Autowired
	public CustomerController(CustomerService customerService, PdfService pdfService) {
		this.customerService = customerService;
		this.pdfService = pdfService;
	}

	@PostMapping("/submitCustomerDetails")
	public ModelAndView loginDetails(@ModelAttribute LoginUser loginUser, ModelAndView modelAndView) {
		customerService.setLoginUser(loginUser);
		modelAndView.addObject("customers", customerService.getCustomers());
		modelAndView.setViewName("customers");
		return modelAndView;
	}

	@PostMapping("/customers")
	public ModelAndView studentsView(@ModelAttribute LoginUser loginUser, ModelAndView modelAndView) {
		modelAndView.addObject("customers", customerService.getCustomers());
		if (loginUser == null) {
			loginUser = new LoginUser();
		}
		customerService.setLoginUser(loginUser);
		modelAndView.addObject("loginUser", loginUser);
		modelAndView.setViewName("customers");
		return modelAndView;
	}

	@GetMapping("/download-pdf")
	public void downloadPDFResource(HttpServletResponse response) {
		try {
			Path file = Paths.get(pdfService.generatePdf().getAbsolutePath());
			if (Files.exists(file)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			}
		} catch (DocumentException | IOException ex) {
			ex.printStackTrace();
		}
	}
}
