CREATE DATABASE PlaceFinderDB;
GO

IF DB_ID('placefinder') IS NULL
    CREATE DATABASE PlaceFinderDB;
GO

USE PlaceFinderDB;
GO

IF OBJECT_ID('dbo.favourite_places', 'U') IS NULL
BEGIN
    CREATE TABLE favourite_places (
        fav_place_id          UNIQUEIDENTIFIER NOT NULL
                    CONSTRAINT pk_favourite_places PRIMARY KEY
                    CONSTRAINT df_favourite_places_id DEFAULT NEWID(),
        place_id    NVARCHAR(255)    NOT NULL,
        name        NVARCHAR(255)    NOT NULL,
        address     NVARCHAR(500)    NULL,
        latitude         FLOAT            NOT NULL,
        longitude         FLOAT            NOT NULL,
        created_at  DATETIME2        NOT NULL
                    CONSTRAINT df_favourite_places_created_at DEFAULT SYSUTCDATETIME(),
        CONSTRAINT uk_favourite_places_place_id UNIQUE (place_id)
    );
END
GO

