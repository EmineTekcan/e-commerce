package com.eminetekcan.inventoryservice.service.impl;

import com.eminetekcan.inventoryservice.dto.InventoryDto;
import com.eminetekcan.inventoryservice.model.Inventory;
import com.eminetekcan.inventoryservice.repository.InventoryRepository;
import com.eminetekcan.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public List<InventoryDto> isInStock(List<String> skuCode) {
        List<InventoryDto> inventoryDtos=new ArrayList<>();
        skuCode.forEach(s -> {
            Inventory inventory=inventoryRepository.findBySkuCode(s);
            if (inventory==null)
                return;
            InventoryDto inventoryDto=InventoryDto.builder()
                    .isInStock(inventory.getQuantity()>0)
                    .skuCode(inventory.getSkuCode())
                    .build();
            inventoryDtos.add(inventoryDto);
        });
        return inventoryDtos;
    }
}
