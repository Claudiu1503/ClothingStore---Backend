package com.backend.clothingstore.repositories;

import com.backend.clothingstore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
