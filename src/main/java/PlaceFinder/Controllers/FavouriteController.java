package PlaceFinder.Controllers;

import PlaceFinder.Dtos.request.FavouriteRequest;
import PlaceFinder.Dtos.response.FavouriteResponse;
import PlaceFinder.Services.Interface.IFavouriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
@RequiredArgsConstructor
public class FavouriteController {

    private final IFavouriteService service;

    @GetMapping
    public List<FavouriteResponse> list() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavouriteResponse add(@Valid @RequestBody FavouriteRequest request) {
        return service.add(request);
    }

    @DeleteMapping("/{placeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String placeId) {
        service.remove(placeId);
    }
}
