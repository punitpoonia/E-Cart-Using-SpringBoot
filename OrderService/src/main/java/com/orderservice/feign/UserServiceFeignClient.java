package com.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cartservice.dto.UserDTO;

@FeignClient(name = "user", url = "http://localhost:9096/users")
public interface UserServiceFeignClient {

	@PostMapping("/login")
    Boolean loginUser(@RequestBody UserDTO user);
}
