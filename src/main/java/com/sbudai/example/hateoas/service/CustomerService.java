package com.sbudai.example.hateoas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sbudai.example.hateoas.entity.Customer;
import com.sbudai.example.hateoas.repository.CustomerRepository;

@Service
public class CustomerService {
//	@Autowired
	private CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	public List<Customer> fetchCustomerDataAsList() {
		// Fetch all customers using findAll
		return customerRepository.findAll();
	}

	public List<Customer> fetchFilteredCustomerDataAsList(String firstNameFilter, String lastNameFilter) {
		// Apply the filter for firstName and lastName
		return customerRepository.findByFirstNameLikeAndLastNameLike(firstNameFilter, lastNameFilter);
	}

	public Page<Customer> fetchCustomerDataAsPageWithFiltering(String firstNameFilter, String lastNameFilter, int page,
			int size) {
		// create Pageable object using the page and size
		Pageable pageable = PageRequest.of(page, size);
		// fetch the page object by additionally passing pageable with the filters
		return customerRepository.findByFirstNameLikeAndLastNameLike(firstNameFilter, lastNameFilter, pageable);
	}
	
	
    public Page<Customer> fetchCustomerDataAsPageWithFilteringAndSorting(String firstNameFilter, 
    		String lastNameFilter, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return customerRepository.findByFirstNameLikeAndLastNameLike(firstNameFilter, lastNameFilter, pageable);
    }
    
    public Customer getCustomerById(Long id) {
    	return customerRepository.findById(id).get();
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}