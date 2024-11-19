package com.backend.clothingstore.repositories;

import com.backend.clothingstore.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
    List<Favorites> findByUserId(int id);
    Favorites findByUserIdAndProductId(int userId, int productId);
    void deleteAllByUserId(int userId);
}
