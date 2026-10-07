package PlaceFinder.Repositories;

import PlaceFinder.Entities.FavouritePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavouriteRepository extends JpaRepository<FavouritePlace, UUID> {

    Optional<FavouritePlace> findByPlaceId(String placeId);
    List<FavouritePlace> findAllByOrderByCreatedAtDesc();
    long deleteByPlaceId(String placeId);
}
