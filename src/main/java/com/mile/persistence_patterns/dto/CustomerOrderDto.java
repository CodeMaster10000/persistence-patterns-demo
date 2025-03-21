package com.mile.persistence_patterns.dto;

import java.time.LocalDateTime;

public record CustomerOrderDto(Long customerId, String customerName, LocalDateTime dateTime) {}

