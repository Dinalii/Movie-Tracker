# Movie-Tracker

###This is a Native Android Mobile Application which was developed as a Movie Tracker.

###This application contains with following functinalities :

Register Movies
Add/Remove Favorites Movies
Edit Registered Movies
Search Movies in Device Database by Movie Title or Movie Director
Get Movie Title/Ratings/Images from IMDB API

###Registering movie data will stored using a SQLite Database.

Important Notes
IMDB API key is defined on res -> values -> strings as MY_API_KEY. In order to do get data from IMDB, you need a valid API key.

#File Structure

```
.
├── app
│   ├── manifets
|        ├── AndroidManifest.xml
│   ├── java
|       ├── com.example.main
|            ├──  imdb
|            |   ├── ImageRequest
|            |   ├── ImdbMovie
|            |   └── ImdbMovieRequest
│            ├── validations
|            |    └── IFilter
│            ├── DisplayMovies
│            ├── EditMovie
│            ├── EditMovieIndex
│            ├── FavouriteMovies
│            ├── MainActivity
│            ├── RtaitngImage
│            ├── RatingsIndex
│            ├── RatingTitles
│            ├── RegisterMovie
│            ├── Search
│           ├── java
│           │   └── com
│           │       └── example
│           │           └── nimendra
│           │               ├── AdvancedActivity.java
│           │               ├── CarHintActivity.java
│           │               ├── CarImageActivity.java
│           │               ├── CarMakeActivity.java
│           │               ├── MainActivity.java
│           │               └── utils
│           │                   ├── ImageLoader.java
│           │                   ├── PopulateData.java
│       │   │                   ├── Styles.java
│       │   │                   └── ValidateImages.java
│       │   └── res
│       │       ├── drawable
│       │       └── values
│       │           ├── colors.xml
│       │           ├── dimen.xml
│       │           ├── strings.xml
│       │           └── styles.xml
│       └── test
├── build.gradle
├── gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
└── settings.gradle

```

