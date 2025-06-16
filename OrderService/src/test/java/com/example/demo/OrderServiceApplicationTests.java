package com.example.demo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.cartservice.dto.UserDTO;
import com.cartservice.model.CartItem;
import com.orderservice.OrderServiceApplication;
import com.orderservice.controller.OrderController;
import com.orderservice.feign.CartServiceFeignClient;
import com.orderservice.feign.UserServiceFeignClient;
import com.orderservice.model.Order;
import com.orderservice.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
@SpringBootTest(classes = OrderServiceApplication.class)
class OrderServiceApplicationTests {
	



	    @InjectMocks
	    private OrderController orderController;

	    @Mock
	    private OrderService orderService;

	    @Mock
	    private UserServiceFeignClient userServiceFeignClient;

	    @Mock
	    private CartServiceFeignClient cartServiceFeignClient;

	    private UserDTO userDTO;
	    private CartItem cartItem;
	    private List<CartItem> cartItems;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        userDTO = new UserDTO("testUser", "testPassword");
	        cartItem = new CartItem(1L, "Product1", 100.0, 2);
	        cartItems = Arrays.asList(cartItem);
	    }

	   
		@Test
	    void placeOrder_ShouldReturnOk_WhenAuthenticatedAndCartNotEmpty() {
	        String username = "testUser";
	        String password = "testPassword";
	        String shippingAddress = "123 Main St";
	        when(userServiceFeignClient.loginUser(userDTO)).thenReturn(true);
	        when(cartServiceFeignClient.getCartItems(username, password)).thenReturn(cartItems);
	        when(orderService.placeOrder(username, password, shippingAddress)).thenReturn(new Order());
	        ResponseEntity<String> response = orderController.placeOrder(username, password, shippingAddress);
	        assertEquals(200, response.getStatusCodeValue());
	        assertEquals("Order Placed Successfully", response.getBody());
	        verify(orderService, times(1)).placeOrder(username, password, shippingAddress);
	    }

	    @Test
	    void placeOrder_ShouldReturnUnauthorized_WhenInvalidCredentials() {
	        String username = "testUser";
	        String password = "wrongPassword";
	        String shippingAddress = "123 Main St";
	        when(userServiceFeignClient.loginUser(userDTO)).thenReturn(false);
	        ResponseEntity<String> response = orderController.placeOrder(username, password, shippingAddress);
	        assertEquals(401, response.getStatusCodeValue());
	        assertEquals("Invalid username and password", response.getBody());
	        verify(orderService, times(0)).placeOrder(any(), any(), any());
	    }
	    
	    @Test
	    void getOrderStatus_ShouldReturnOk_WhenAuthenticated() {
	        String username = "testUser";
	        String password = "testPassword";
	        Long orderId = 1L;
	        Order mockOrder = new Order();
	        mockOrder.setOrderStatus("PLACED");
	        when(userServiceFeignClient.loginUser(userDTO)).thenReturn(true);
	        when(orderService.getOrderStatus(username, password, orderId)).thenReturn(mockOrder);
	        ResponseEntity<String> response = orderController.getOrderStatus(username, password, orderId);
	        assertEquals(200, response.getStatusCodeValue());
	        assertEquals("Order Placed Successfully", response.getBody());
	        verify(orderService, times(1)).getOrderStatus(username, password, orderId);
	    }

	    @Test
	    void getOrderStatus_ShouldReturnUnauthorized_WhenInvalidCredentials() {
	        String username = "testUser";
	        String password = "wrongPassword";
	        Long orderId = 1L;
	        when(userServiceFeignClient.loginUser(userDTO)).thenReturn(false);
	        ResponseEntity<String> response = orderController.getOrderStatus(username, password, orderId);
	        assertEquals(401, response.getStatusCodeValue());
	        assertEquals("Invalid username and password", response.getBody());
	        verify(orderService, times(0)).getOrderStatus(any(), any(), any());
	    }

	}
