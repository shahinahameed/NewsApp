# NewsApp
Just a sample app which shows how to connect API and fetch data
-------------------------------------------------------------------
The very first screen contains a list of news headlines:
In MainActivity.java, we have a content view  (activity_main.xml)  which contains a custom listview with custom adapter  (CustomListAdapter) and layout  (activity_listview.xml)
It shows an image, headline, description and time of news.
This is how its done: fetching data through API in an asynctask(because loading in UI thread is not a good practice) and processing the result json and storing that data in an arraylist. And then setting the custom adapter to list with that data.
In custom adapter (CustomListAdapter) we set the data to the element and loading image in an async task and resizing it to avoid the memory overflow.
while clicking on the list, it will start an intent to details activity - DetailsActivity.java with layout activity_details.xml. Here along with intent, passing a hashmap of details of that clicked news.
In details page,we will get the hashmap of details from intent and from that, showing a heading, then the source and author, then time , then loading image in an async task(because it is from URL) and resizing it to avoid memory overflow, Then the content and finally the link to the website for more details.

Here i have some suggestions for this app to make better
--------------------------------------------------------
Show a splash screen or something first and mean time load data from API - It will avoid to wait user to load the news.
Speedup image loading(cache image once loaded)
And ofcourse better design
