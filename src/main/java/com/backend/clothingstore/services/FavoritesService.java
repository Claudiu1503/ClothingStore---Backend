package com.backend.clothingstore.services;

import com.backend.clothingstore.model.Favorites;
import com.backend.clothingstore.model.Product;
import com.backend.clothingstore.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FavoritesService {
    List<Favorites> getAllFavProductsByUserId(int id);

    void add(Product product, User user);

    void deleteOneProductFromFav(Product product, User user);

    void deleteAllFavoritesFromUser(User user);
}
