call gradlew clean
call gradlew build
call gradlew :sample-module:clean
call gradlew :sample-module:run
node sample-module\build\classes\kotlin\main\sample-module.js
