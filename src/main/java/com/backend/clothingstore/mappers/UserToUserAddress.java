package com.backend.clothingstore.mappers;

import com.backend.clothingstore.model.Address;
import com.backend.clothingstore.model.User;

public class UserToUserAddress {

    public static Address map(User user) {
        if (user == null) {
            return null;
        }

        return Address.builder()
                .country(user.getCountry())
                .state(user.getState())
                .city(user.getCity())
                .zip(user.getZip())
                .addressLine(user.getAddressLine())
                .phone(user.getPhone())
                .build();
    }

}
