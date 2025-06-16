package com.orderservice.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cartservice.model.CartItem;
import java.util.List;


@FeignClient(name = "cartservice", url = "http://localhost:9092/cart")
public interface CartServiceFeignClient {

    @GetMapping("/history")
    List<CartItem> getCartItems(@RequestParam String username, @RequestParam String password);
    @GetMapping("/get/{id}")
    CartItem getProductById(@RequestParam Long id);
    @DeleteMapping("/clear")
    void clearCart(@RequestParam("username") String username, @RequestParam("password") String password);
    
}