package com.sbudai.example.hateoas.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbudai.example.hateoas.assembler.CustomerModelAssembler;
import com.sbudai.example.hateoas.entity.Customer;
import com.sbudai.example.hateoas.model.CustomerModel;
import com.sbudai.example.hateoas.service.CustomerService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/api")
public class CustomerController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

	private CustomerService customerService;

	private CustomerModelAssembler customerModelAssembler;

	private PagedResourcesAssembler<Customer> pagedResourcesAssembler;

	/**
	 * 
	 * @param customerService         CustomerService
	 * @param customerModelAssembler  CustomerModelAssembler
	 * @param pagedResourcesAssembler PagedResourcesAssembler
	 */
	public CustomerController(CustomerService customerService, CustomerModelAssembler customerModelAssembler,
			PagedResourcesAssembler<Customer> pagedResourcesAssembler) {
		super();
		this.customerService = customerService;
		this.customerModelAssembler = customerModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;

	}

	/**
	 * @return List of all customers
	 */
	@GetMapping("/v0/customers")
	public List<Customer> fetchCustomersAsList() {
		return customerService.fetchCustomerDataAsList();
	}

	/**
	 * @param firstNameFilter Filter for the first Name if required
	 * @param lastNameFilter  Filter for the last Name if required
	 * @return List of filtered customers
	 */
	@GetMapping("/v1/customers")
	public List<Customer> fetchCustomersAsFilteredList(@RequestParam(defaultValue = "") String firstNameFilter,
			@RequestParam(defaultValue = "") String lastNameFilter) {
		return customerService.fetchFilteredCustomerDataAsList(firstNameFilter, lastNameFilter);
	}

	/**
	 * @param firstNameFilter Filter for the first Name if required
	 * @param lastNameFilter  Filter for the last Name if required
	 * @param page            number of the page returned
	 * @param size            number of entries in each page
	 * @return Page object with customers after filtering
	 */
	@GetMapping("/v2/customers")
	public Page<Customer> fetchCustomersWithPageInterface(@RequestParam(defaultValue = "") String firstNameFilter,
			@RequestParam(defaultValue = "") String lastNameFilter, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "30") int size) {
		return customerService.fetchCustomerDataAsPageWithFiltering(firstNameFilter, lastNameFilter, page, size);
	}

	/**
	 * @param firstNameFilter Filter for the first Name if required
	 * @param lastNameFilter  Filter for the last Name if required
	 * @param page            number of the page returned
	 * @param size            number of entries in each page
	 * @param sortList        list of columns to sort on
	 * @param sortOrder       sort order. Can be ASC or DESC
	 * @return Page object with customers after filtering and sorting
	 */
	@GetMapping("/v3/customers")
	public Page<Customer> fetchCustomersWithPageInterfaceAndSorted(
			@RequestParam(defaultValue = "") String firstNameFilter,
			@RequestParam(defaultValue = "") String lastNameFilter, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "30") int size, @RequestParam(defaultValue = "") List<String> sortList,
			@RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
		return customerService.fetchCustomerDataAsPageWithFilteringAndSorting(firstNameFilter, lastNameFilter, page,
				size, sortList, sortOrder.toString());
	}

	/**
	 * @param firstNameFilter Filter for the first Name if required
	 * @param lastNameFilter  Filter for the last Name if required
	 * @param page            number of the page returned
	 * @param size            number of entries in each page
	 * @param sortList        list of columns to sort on
	 * @param sortOrder       sort order. Can be ASC or DESC
	 * @return PagedModel object in Hateoas with customers after filtering and
	 *         sorting
	 */
	@GetMapping("/v4/customers")
	public PagedModel<CustomerModel> fetchCustomersWithPagination(
			@RequestParam(defaultValue = "") String firstNameFilter,
			@RequestParam(defaultValue = "") String lastNameFilter, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "30") int size, @RequestParam(defaultValue = "") List<String> sortList,
			@RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
		Page<Customer> customerPage = customerService.fetchCustomerDataAsPageWithFilteringAndSorting(firstNameFilter,
				lastNameFilter, page, size, sortList, sortOrder.toString());

		customerPage.get()
				.forEach(
						customer -> LOG
								.debug(" id {}",
										linkTo(methodOn(CustomerController.class).fetchCustomersWithPagination(
												firstNameFilter, lastNameFilter, page, size, sortList, sortOrder))
												.withSelfRel()));
		// Use the pagedResourcesAssembler and customerModelAssembler to convert data to
		// PagedModel format

		return pagedResourcesAssembler.toModel(customerPage, customerModelAssembler);
	}

	/**
	 * 
	 * @param id Long
	 * @return EntityModel
	 */
	@GetMapping(path = "/v4/customers/{id}")
	public EntityModel<?> getById(@PathVariable Long id) {
		Customer customer = customerService.getCustomerById(id);
		String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

		StringBuilder str = new StringBuilder(baseUri);

		str.append("/v4/customers/");
		str.append(id);

		return EntityModel.of(customer, Link.of(str.toString(), IanaLinkRelations.SELF));

	}

}
