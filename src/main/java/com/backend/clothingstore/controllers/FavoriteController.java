package com.backend.clothingstore.controllers;


import com.backend.clothingstore.model.Favorites;
import com.backend.clothingstore.model.Product;
import com.backend.clothingstore.model.User;
import com.backend.clothingstore.services.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    FavoritesService favoritesService;


    @GetMapping("/userid/{id}/get-all")
    public ResponseEntity<List<Favorites>> getAllFavoritesByUserId(@PathVariable int id) {
        List<Favorites> fav = favoritesService.getAllFavProductsByUserId(id);
        return ResponseEntity.ok(fav);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestParam Product product, @RequestParam User user) {
        favoritesService.add(product,user);
        return ResponseEntity.ok("Succesfuly added");
    }

    @DeleteMapping( "/delete-one-product")
    public ResponseEntity<?> deleteOneProductFromFavorite(@RequestParam Product product, @RequestParam User user) {
        favoritesService.deleteOneProductFromFav(product,user);
        return ResponseEntity.ok("Succesfuly deleted");
    }

    @DeleteMapping( "/delete-all-by-userid")
    public ResponseEntity<?> deleteAllFavorites(@RequestParam User user) {
        favoritesService.deleteAllFavoritesFromUser(user);
        return ResponseEntity.ok("Succesfuly deleted all favorites");
    }
}
