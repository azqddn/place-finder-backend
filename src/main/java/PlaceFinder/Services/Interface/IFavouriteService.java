package PlaceFinder.Services.Interface;

import PlaceFinder.Dtos.request.FavouriteRequest;
import PlaceFinder.Dtos.response.FavouriteResponse;

import java.util.List;

public interface IFavouriteService {
    public List<FavouriteResponse> findAll();
    public FavouriteResponse add(FavouriteRequest request);
    public void remove(String placeId);
}
