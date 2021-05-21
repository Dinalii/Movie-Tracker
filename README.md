# Movie-Tracker

This is a Native Android Mobile Application which was developed as a Movie Tracker.

This application contains with following functinalities :</br>
:small_orange_diamond:Register Movies</br>
:small_orange_diamond:Add/Remove Favorites Movies</br>
:small_orange_diamond:Edit Registered Movies</br>
:small_orange_diamond:Search Movies in Device Database by Movie Title or Movie Director</br>
:small_orange_diamond:Get Movie Title/Ratings/Images from IMDB API

Registering movie data will stored using a SQLite Database.</br>
Before run the downloaded project you will need to create a free account on this web service(https://imdb-api.com/) which gives you a unique key.</br>
Then copy paste that IMDB API key on imdb -> ImdbMovieRequest -> line 72. In order to do get data from IMDB, you need a valid API key.

## Directory Layout

```

├── app
│   ├── manifets
|   |    ├── AndroidManifest.xml
│   ├── java
|   |   ├── com.example.main
|   |   |   ├──  imdb
|   |   |   |   ├── ImageRequest
|   |   |   |   ├── ImdbMovie
|   |   |   |   └── ImdbMovieRequest
│   |   |   ├── validations
|   |   |   |    └── IFilter
│   |   |   ├── DisplayMovies
│   |   |   ├── EditMovie
│   |   |   ├── EditMovieIndex
│   |   |   ├── FavouriteMovies
│   |   |   ├── MainActivity
│   |   |   ├── RtaitngImage
│   |   |   ├── RatingsIndex
│   |   |   ├── RatingTitles
│   |   |   ├── RegisterMovie
│   |   |   └── Search
│   |   ├── database
│   |   |   ├── Database
│   |   |   └── Movie Table
│   |   ├── models
│   |   |   └── Movie
│   |   ├── views
│   |      └── MLItem
│   ├── java
|   |   ├── com.example.main
|   |        └──  BuildConfig
│   ├── res
|       ├── drawable
|       ├── font
│       ├── layout
│       ├── mipmap
│       └── values
└── Gradle Scripts

```

