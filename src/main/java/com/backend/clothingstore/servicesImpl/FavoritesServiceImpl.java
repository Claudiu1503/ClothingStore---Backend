package com.backend.clothingstore.servicesImpl;

import com.backend.clothingstore.model.Favorites;
import com.backend.clothingstore.model.Product;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.repositories.FavoritesRepository;
import com.backend.clothingstore.services.FavoritesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    FavoritesRepository favoritesRepository;

    @Override
    public List<Favorites> getAllFavProductsByUserId(int id) {
        return favoritesRepository.findByUserId(id);
    }

    @Override
    public void add(Product product, User user) {
        Favorites favFind = favoritesRepository.findByUserIdAndProductId(user.getId(), product.getId());
        if (favFind != null) {
            Favorites fav = new Favorites();
            fav.setProduct(product);
            fav.setUser(user);
            favoritesRepository.save(fav);
        }
       else throw new IllegalArgumentException("Product already added");

    }

    @Override
    public void deleteOneProductFromFav(Product product, User user) {
        Favorites fav = favoritesRepository.findByUserIdAndProductId(user.getId(), product.getId());
        favoritesRepository.delete(fav);
    }

    @Override
    @Transactional
    public void deleteAllFavoritesFromUser(User user) {
        favoritesRepository.deleteAllByUserId(user.getId());
    }
}
