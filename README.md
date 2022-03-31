# NewsTestApp

## Stack: 
- Single Activity
- MVVM | MVI (with one state for each Screen)
- Room for store favorites
- Koin for DI
- Network: GSON, Retrofit, OkHttp
- Coroutine for async work
- SPlash Screen Api for Splash Screen
- Navigation : Android Navigation

## Description: 
- tap to an avatar to activate the sign-in process to GoogleAccount | Or you can sign in from favoritePage
- if you already signed in, a new tap on the avatar activates sign out from your Google Account

For List with data, I used a way to have one adapter which can work with any item type. And you need to define ItemTypes(Fingerprints) when you set up your adapter. It is similar to library AdapterDelegates or other.

Current API key stored in build.gradle. In real projects, better way to store it in the local.properties file. And don't share on the internet.