package PlaceFinder.Dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteResponse {

    private String placeId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
