package PlaceFinder.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "favourite_places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouritePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "fav_place_id")
    private UUID favPlaceId;

    @Column(name = "place_id")
    private String placeId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "created_at")
    private Instant createdAt;
}
