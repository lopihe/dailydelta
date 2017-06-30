#dailyΔelta
Learning tool to check unique words used in online pages.

##Requirements
Java 8
Gradle

##Build
./gradlew clean build

##Endpoints
###Check and add new words
/check
###Browse words already added
/browse

##Planned 
###Features
* Translation for words
* Time imported for words
* Locale info for words and contexts
* Memrise export
* Enhanced word filter when importing
* Enhanced sentence filter when importing

###Workflow
1. Log in 
2. Parse -> delta
   1. Update global word frequencies
   2. Collect all contexts
   3. Add new words to user's pending list
3. Show sorted pending words for user
   1. Time added
   2. Frequency
4. Move words around
   1. pending -> permanent
   2. pending -> ignored
   3. pending -> (delete)
   
