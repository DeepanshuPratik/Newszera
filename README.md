
# Newzera

It's a News app with features of searching of news and some presets are also been provided with easy control over app.

Most important or key part of this project is API calling and creating adapter for Recycler View.

## Concepts Covered

- Recycler View
- Api Calling 
- Creating Adapters
- Splash Screen Implementation


## API Reference

#### One of the good free API for news

```java
  http://newsapi.org/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Some of the ways for calling different type of News!

```java
  1) https://newsapi.org/v2/top-headlines?country=us&apiKey=$YOUR_API_KEY
  2) https://newsapi.org/v2/everything?q=tesla&from=2021-10-14&apiKey=$YOUR_API_KEY
  3) https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=$YOUR_API_KEY
  4) https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=$YOUR_API_KEY 
```




## Procedure

- Created Splash Screen and then directed it to 'Category' Activity.              
- Created Search View and Card Views that would be implemented for category and search query part. 
- Applied onClick listner to category part and passed category as a string that would be used in modifying url for fetching data!      
- Extracted input string from search bar and send it as an query through url using the way 2) of calling api.
- On the third Activity, fetching of data is being taken care of and as it returns array of Json object and using adapter displayed it over Recycler view
- The other 'horizontal' Recycler View' data is provided manually through an class providing 2 things: 
    
        1) Image url -> for background image.
        2) Text -> Category of News.
        
## Screenshots
![SplashScreen](https://user-images.githubusercontent.com/65304599/141692184-17dceb56-5330-4f67-a2c0-71192270d679.jpeg) ![CategoryList](https://user-images.githubusercontent.com/65304599/141692225-36f5fd31-ee8c-4452-90ad-d5a3ffea9ea9.jpeg) ![NewsSample](https://user-images.githubusercontent.com/65304599/141692294-1c5ddc03-0cb9-4553-ba24-708383c4563a.jpeg) 


## Features
- Pull down to refresh pages.
- Single touch sends you to that news source.
- Long press allows you to share it anywhere through social medias.






