Pantry is a inventory app for your home, it contains a local database of your  recipes, ingredients, and utensils/tools

Pantry requires

re-natal https://github.com/drapanjanas/re-natal  
react-native https://facebook.github.io/react-native/  
lein https://leiningen.org/

Recommend rlwrap

`npm install react-native-local-mongo --save`  
`npm install react-navigation --save`

To run in development mode
* start an android emulator of your choice
* start react native `react-native start`
* run this command to launch lein and compile the project  
    - `lein clean; re-natal use-android-device avd; re-natal use-figwheel; rlwrap lein figwheel android`
* run `react-native run-android` to side load the app into the emulator