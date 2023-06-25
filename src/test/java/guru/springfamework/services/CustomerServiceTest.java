package guru.springfamework.services;


import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private final Long ID = 1L;
    private final String FIRST_NAME = "Joe";
    private final String LAST_NAME = "Smith";
    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }

    @Test
    public void testGetAllCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(ID);
        customer1.setFirstname(FIRST_NAME);
        customer1.setLastname(LAST_NAME);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Mary");
        customer2.setLastname("Smith");

        List<Customer> customers = Arrays.asList(customer1,customer2);

        //when
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(2, customerDTOS.size());

    }

    @Test
    public void testGetCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        //when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //then
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertEquals(FIRST_NAME, customerDTO.getFirstname());

    }
}