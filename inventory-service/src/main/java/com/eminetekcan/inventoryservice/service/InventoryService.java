package com.eminetekcan.inventoryservice.service;

import com.eminetekcan.inventoryservice.dto.InventoryDto;

import java.util.List;

public interface InventoryService {

    public List<InventoryDto> isInStock(List<String> skuCode);
}
