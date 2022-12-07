package com.deliver.api.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliver.api.dto.OrderDTO;
import com.deliver.api.dto.ProductDTO;
import com.deliver.api.entities.Order;
import com.deliver.api.entities.OrderStatus;
import com.deliver.api.entities.Product;
import com.deliver.api.repository.OrderRepository;
import com.deliver.api.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly=true)
	public List<OrderDTO>findAll(){
		List<Order> list = repository.findOrderWithProducts();
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order  order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(), OrderStatus.PENDING);
		
		for (ProductDTO p : dto.getProducts()) {
			
			//pegar o produto o seu ID a sociando no pedido
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		
		order = repository.save(order);
		return new OrderDTO(order);
		
	}
	
	@Transactional
	public OrderDTO setDelivered(Long id) {
		
		//pegar o id da order
		Order order = repository.getById(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = repository.save(order);
		
		return new OrderDTO(order);
		
	}
	
}
