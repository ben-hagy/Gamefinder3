
# Gamefinder3

Gamefinder3 is a relatively simple app that uses Jetpack Compose for UI and various contemporary libraries to expose data from the RAWG.io API to allow users to browse, bookmark, and rate video games.

I consider this my "final learning project" with regard to Android development, and intend for the repo to be used freely by anyone who wishes to learn.

![Cover Image](--path)

## Contents

* [App Video Demo](--path)
* [Setup Instructions](--path)
* [Project Architecture](--path)
* [Project Dependencies](--path)


## Preview (Video)

--path for preview video

## Run It

* Clone or download the repository
* Go to RAWG.io and generate your own free API key
* In the "Constants" file, uncomment the API const val and define it as your API key
* Everything should be good to go from here


## Project Architecture

![image](--path?) 

The image shows the project's folder structure. Clean Architecture guidelines were used to organize the app's structure as follows:


* data
* * Data layer classes live here. These include the remote API, the local database, and the repository implementation class. 
* di
* * Dependency injection modules live here. This app uses Dagger Hilt to handle dependencies across the whole of the app.
* domain
* * The domain layer acts as a kind of interfacial bridge between the Data and Presentation layers. Data models, the repo interface, and use cases live here. 
* presentation
* * Screens with view code and their relative viewmodel classes live here. Each screen - as well as certain persistent components like the Bottom Bar - lives here in its own package. Material ui theming classes are also stored here.
* util
* * A catch-all package for utility classes used across the app, such as the Constants, custom Parsers, and the Resource wrapper class.

The primary goal of common Android architecture patterns is to help ensure scalability via particular separations of concern.
Obviously, this is a relatively simple app, so certain Clean Architecture principles - like multi-modular organization - are less appropriate than they would be in a larger-scale production app.
However, this app still pays careful attention to architecture, most specifically in only allowing the Domain layer to speak to both the Data and Presentation layers.
This separation ensures that view-models are only given access to domain-level interfaces, which in turn ensures that we could completely re-write or change elements of the data or presentation layers without upsetting the entire app's structure.
This also makes it easier to introduce new features, to handle unit and instrumentation tests, and to ensure our app structure is readable to others.

## Dependencies

* Jetpack Compose
* * Primary UI paradigm used throughout the app. Jetpack Compose is a declarative UI framework that lets developers create UI with Kotlin code instead of XML.
* Material 3 (and Material 2)
* * Material3 is used to handle various UI components throughout the app; however, it was in alpha at the time I was making this app, so a few components required the old API (SwipeToDismiss, Snackbars)
* Material Icons
* * Material's pack of universal icons, used instead of Android Studio's default vectors for consistency.
* Compose Destinations
* * Used for navigation. This very popular plugin simplifies navigation boilerplate in Compose projects and lets us use simple annotations to create a navigation graph.
* Retrofit
* * Remote API library. Used to handle our OkHttp instances and make API calls
* GSON
* * Converter factory library that helps us cleanly convert JSON responses into Kotlin data objects.
* Lifecycle Dependencies for Compo
* * Used to support Viewmodel structures in Compose
* Coil for Compose
* * Used to handle Async image loading throughout the app
* Dagger Hilt
* * The dependency injection framework used throughout the app
* Paging 3
* * Used to handle our paginated list results on the home screen
* Room
* * Local database library. used to handle the app's local databasing for the Bookmarks screen
