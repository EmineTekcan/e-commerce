package com.eminetekcan.inventoryservice;

import com.eminetekcan.inventoryservice.model.Inventory;
import com.eminetekcan.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner saveInventory(InventoryRepository inventoryRepository){

		return args -> {
			Inventory inventory=new Inventory();
			inventory.setQuantity(1);
			inventory.setSkuCode("123a");
			inventoryRepository.save(inventory);

			Inventory inventory1=new Inventory();
			inventory.setQuantity(1);
			inventory.setSkuCode("456a");
			inventoryRepository.save(inventory1);
		};

	}*/
}
