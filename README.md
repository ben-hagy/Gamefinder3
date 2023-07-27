
# Gamefinder3

Gamefinder3 is a search app that uses Jetpack Compose for UI and various contemporary libraries to expose data from the RAWG.io API to allow users to browse, bookmark, rate, and keep notes on video games.

The repo is offered freely as a learning project for anyone. The app is also available on the Google Play Store.

![Screenshots]
<img width="307" alt="home screen light" src="https://github.com/ben-hagy/Gamefinder3/assets/107595511/25c96a0a-f4f2-4c17-998d-12d5f64b5e4a">
<img width="307" alt="details screen light" src="https://github.com/ben-hagy/Gamefinder3/assets/107595511/91148050-b466-4759-a708-f92cc16857ad">
<img width="307" alt="bookmarks screen light" src="https://github.com/ben-hagy/Gamefinder3/assets/107595511/d36dff86-caa0-462b-a792-0ccdf0a16283">
<img width="307" alt="home screen dark" src="https://github.com/ben-hagy/Gamefinder3/assets/107595511/9632f789-bb64-4484-afcb-2bdb9eba3606">
<img width="307" alt="details screen dark" src="https://github.com/ben-hagy/Gamefinder3/assets/107595511/2e9f8541-0441-41af-a5c3-dd6f3f8c09ba">
<img width="307" alt="details screen dark" src="https://github.com/ben-hagy/Gamefinder3/assets/107595511/f2dd3fd3-193b-4871-b9d7-0bc6ecae5d8b">


## Preview (Video)

```https://drive.google.com/file/d/1gVJPoNBOfhjkr4S8TRxTH7as1maEBRVk/view?t=1s```

## Instructions to Run

* Clone or download the repository
* Go to ```RAWG.io``` and generate your own free API key
* In the "Constants" file, uncomment "API_KEY" and define it as your own
* As long as everything is correct, the app should now run!


## Project Architecture

 <img width="307" alt="folder structure" src="https://github.com/ben-hagy/Gamefinder3/assets/107595511/32878cc7-6fad-459f-87a0-f762601fd8d5">

The above image shows the project's folder structure. Clean Architecture guidelines were used to organize the app's structure as follows:

* **data**: Data layer classes live here. These include the remote API service, the local database, and the repository implementation class. 
* **di**: Dependency injection modules live here. This app uses Dagger Hilt to handle dependencies across the whole of the app.
* **domain**: Data models, the repository interface, and use cases live here. The domain layer acts as a kind of interfacial bridge between the Data and Presentation layers - like a tube between the data and the screen its inflated to.
* **presentation**: View code for screens and their relative viewmodel classes live here. Material ui theming classes are also stored here.
* **util**: A catch-all package for utility classes used across the app, such as the Constants class, custom Parsers class, and the Resource wrapper class.

The primary goal of common Android architecture patterns is to help ensure scalability via particular separations of concern.
Obviously, this is a relatively simple app, so certain Clean Architecture principles - like multi-modular organization - are less appropriate than they would be in a larger-scale production app.
However, this app still pays careful attention to architecture, most specifically in only allowing the Domain layer to speak to both the Data and Presentation layers.
This separation ensures that view-models are only given access to domain-level interfaces, which in turn ensures that we could completely re-write or change elements of the data or presentation layers without upsetting the entire app's structure.
This also makes it easier to introduce new features, to better handle testing, and to ensure our app structure is easily navigable and readable by others.

Please also note that two sample tests are included in this project. These are proof-of-concept tests that also serve as examples of how to test suspend functions.

## Dependencies

* **Jetpack Compose**: Primary UI paradigm used throughout the app. Jetpack Compose is a declarative UI framework that lets developers create UI with Kotlin code instead of XML.
* **Material 3 (and Material 2)**: Material3 is used to handle various UI components throughout the app; however, it was in alpha at the time I was making this app, so a few components required the old API (SwipeToDismiss, Snackbars)
* **Material Icons**: Material's pack of universal icons, used instead of Android Studio's default vectors for consistency.
* **Compose Destinations**: Used for navigation. This very popular plugin simplifies navigation boilerplate in Compose projects and lets us use simple annotations to create a navigation graph.
* **Retrofit**: Remote API library. Used to handle our OkHttp instances and make API calls
* **GSON**: Converter factory library that helps us cleanly convert JSON responses into Kotlin data objects.
* **Lifecycle Dependencies for Compose**: Used to support viewmodel architecture for compose screens
* **Coil for Compose**: Used to handle Async image loading throughout the app
* **Dagger Hilt**: The dependency injection framework used throughout the app
* **Paging 3**: Used to handle paginated list results on the home screen
* **Room**: Local database library. used to handle the app's local databasing for the Bookmarks screen
* **SmartToolFactory Compose RatingBar**: Custom RatingBar composable used in the app
* **Mockito**: Mocking infrastructure for testing
