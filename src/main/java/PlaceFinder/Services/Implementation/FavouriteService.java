package PlaceFinder.Services.Implementation;

import PlaceFinder.Dtos.request.FavouriteRequest;
import PlaceFinder.Dtos.response.FavouriteResponse;
import PlaceFinder.Entities.FavouritePlace;
import PlaceFinder.Services.Interface.IFavouriteService;
import PlaceFinder.repositories.FavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteService implements IFavouriteService {

    private final FavouriteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<FavouriteResponse> findAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public FavouriteResponse add(FavouriteRequest request) {
        return repository.findByPlaceId(request.getPlaceId())
                .map(this::toResponse)
                .orElseGet(() -> save(request));
    }

    @Override
    @Transactional
    public void remove(String placeId) {
        repository.deleteByPlaceId(placeId);
    }

    private FavouriteResponse save(FavouriteRequest request) {
        try {
            return toResponse(repository.saveAndFlush(toEntity(request)));
        } catch (DataIntegrityViolationException e) {
            // Concurrent insert of the same place: return the existing row
            return repository.findByPlaceId(request.getPlaceId())
                    .map(this::toResponse)
                    .orElseThrow(() -> e);
        }
    }

    /* ---------- helpers ---------- */

    private FavouritePlace toEntity(FavouriteRequest request) {
        return FavouritePlace.builder()
                .placeId(request.getPlaceId())
                .name(request.getName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .createdAt(Instant.now())
                .build();
    }

    private FavouriteResponse toResponse(FavouritePlace entity) {
        return FavouriteResponse.builder()
                .placeId(entity.getPlaceId())
                .name(entity.getName())
                .address(entity.getAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
