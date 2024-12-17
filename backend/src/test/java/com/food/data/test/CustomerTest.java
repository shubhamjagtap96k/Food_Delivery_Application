package com.food.data.test;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
 
import com.food.dao.CustomerRepository;
import com.food.dao.IOrderRepo;
import com.food.dao.IRatingRepo;
import com.food.dao.IRestaurantRepo;
import com.food.dto.CustomerDTO;
import com.food.dto.OrderDTO;
import com.food.dto.RatingDTO;
import com.food.dto.RestaurantDTO;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Customer;
import com.food.model.Order;
import com.food.model.Rating;
import com.food.model.Restaurant;
import com.food.service.CustomerService;
 
public class CustomerTest {
 
    @Mock
    private CustomerRepository customerRepository;
 
    @Mock
    private IOrderRepo orderRepository;
 
    @Mock
    private IRatingRepo ratingRepository;
 
    @Mock
    private IRestaurantRepo restaurantRepository;
 
    @Mock
    private ModelMapper modelMapper;
 
    @InjectMocks
    private CustomerService customerService;
 
    private Customer customer;
    private CustomerDTO customerDTO;
    private Order order;
    private OrderDTO orderDTO;
    private Rating rating;
    private RatingDTO ratingDTO;
    private Restaurant restaurant;
    private RestaurantDTO restaurantDTO;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("John Doe");
        customer.setCustomerEmail("john@example.com");
 
        customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setCustomerName("John Doe");
        customerDTO.setCustomerEmail("john@example.com");
 
        customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("John Doe");
        customerDTO.setCustomerEmail("john@example.com");
 
        order = new Order();
        order.setOrderId(1);
 
        orderDTO = new OrderDTO();
        orderDTO.setOrderId(1);
 
        rating = new Rating();
        rating.setRatingId(1);
 
        ratingDTO = new RatingDTO();
        ratingDTO.setRatingId(1);
 
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1);
 
        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurantId(1);
    }
 
    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);
 
        List<CustomerDTO> result = customerService.getAllCustomers();
 
        assertEquals(1, result.size());
        assertEquals(customerDTO, result.get(0));
        verify(customerRepository, times(1)).findAll();
    }
 
    @Test
    void testGetCustomerById_Success() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);
 
        CustomerDTO result = customerService.getCustomerById(1);
 
        assertEquals(customerDTO, result);
        verify(customerRepository, times(1)).findById(1);
    }
 
    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerById(1);
        });
        assertEquals("Customer with id 1 is not found", thrown.getMessage());
        verify(customerRepository, times(1)).findById(1);
    }
 
 
    @Test
    void testDeleteCustomer_Success() {
        when(customerRepository.existsById(1)).thenReturn(true);
 
        customerService.deleteCustomer(1);
 
        verify(customerRepository, times(1)).existsById(1);
        verify(customerRepository, times(1)).deleteById(1);
    }
 
    @Test
    void testDeleteCustomer_NotFound() {
        when(customerRepository.existsById(1)).thenReturn(false);
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.deleteCustomer(1);
        });
        assertEquals("Customer with id 1 is not found", thrown.getMessage());
        verify(customerRepository, times(1)).existsById(1);
        verify(customerRepository, times(0)).deleteById(1);
    }
 
    @Test
    void testSaveCustomer() {
        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);
 
        CustomerDTO result = customerService.saveCustomer(customerDTO);
 
        assertEquals(customerDTO, result);
        verify(modelMapper, times(1)).map(customerDTO, Customer.class);
        verify(customerRepository, times(1)).save(customer);
        verify(modelMapper, times(1)).map(customer, CustomerDTO.class);
    }
 
    @Test
    void testRemoveFavoriteRestaurant_Success() {
        Set<Restaurant> favoriteRestaurants = new HashSet<>(Collections.singletonList(restaurant));
        customer.setFavoriteRestaurants(favoriteRestaurants);
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
 
        customerService.removeFavoriteRestaurant(1, 1);
 
        verify(customerRepository, times(1)).findById(1);
        verify(restaurantRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).save(customer);
    }
 
    @Test
    void testRemoveFavoriteRestaurant_NotFoundCustomer() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.removeFavoriteRestaurant(1, 1);
        });
        assertEquals("Customer not found with id: 1", thrown.getMessage());
        verify(customerRepository, times(1)).findById(1);
        verify(restaurantRepository, times(0)).findById(anyInt());
        verify(customerRepository, times(0)).save(any(Customer.class));
    }
 
    @Test
    void testRemoveFavoriteRestaurant_NotFoundRestaurant() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());
 
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.removeFavoriteRestaurant(1, 1);
        });
        assertEquals("Restaurant not found with id: 1", thrown.getMessage());
        verify(customerRepository, times(1)).findById(1);
        verify(restaurantRepository, times(1)).findById(1);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }
 
    @Test
    void testGetCustomerReviews() {
        List<Rating> ratings = Collections.singletonList(rating);
        List<RatingDTO> ratingDTOs = Collections.singletonList(ratingDTO);
        when(ratingRepository.findByCustomersCustomerId(1)).thenReturn(ratings);
        when(modelMapper.map(rating, RatingDTO.class)).thenReturn(ratingDTO);
 
        List<RatingDTO> result = customerService.getCustomerReviews(1);
 
        assertEquals(ratingDTOs, result);
        verify(ratingRepository, times(1)).findByCustomersCustomerId(1);
        verify(modelMapper, times(1)).map(rating, RatingDTO.class);
    }
 
    @Test
    void testAuthenticateCustomer_Success() {
        when(customerRepository.findByCustomerName("john")).thenReturn(customer);
        customer.setPassword("password");
        CustomerDTO result = customerService.authenticateCustomer("john", "password");
 
        assertNotNull(result);
        verify(customerRepository, times(1)).findByCustomerName("john");
    }
 
    @Test
    void testAuthenticateCustomer_Failure() {
        when(customerRepository.findByCustomerName("john")).thenReturn(null);
 
        CustomerDTO result = customerService.authenticateCustomer("john", "wrongpassword");
 
        assertNull(result);
        verify(customerRepository, times(1)).findByCustomerName("john");
    }
}