package com.sbudai.example.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.sbudai.example.hateoas.controller.CustomerController;
import com.sbudai.example.hateoas.entity.Customer;
import com.sbudai.example.hateoas.model.CustomerModel;

/**
 * This class extends RepresentationModelAssemblerSupport which is required for
 * Pagination. It converts the Customer Entity to the Customer Model and has the
 * code for it
 */
@Component
public class CustomerModelAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerModel> {

	/**
	 * 
	 */
	public CustomerModelAssembler() {
		super(CustomerController.class, CustomerModel.class);
	}

	/**
	 * 
	 */
	@Override
	public CustomerModel toModel(Customer entity) {
		CustomerModel model = new CustomerModel();

		model.add(linkTo(CustomerController.class).slash("v4").slash("customers").slash(entity.getId()).withSelfRel());
		BeanUtils.copyProperties(entity, model);
		return model;
	}

}
