

# JoinTrip

JointTrip.Web is a project developed using the Spring framework during my studies. It is a blog about mountain trips where the admin can add posts and manage them. 
The project's database is in PostgreSQL. Project features functionalities such as a table of the most active users, the ability to add and like comments, as well as adding posts and sub-posts with descriptions and images.
Additionally, Docker is utilized to containerize the application, ensuring consistent deployment across different environments.


### Completed features:


1. Admins can add, delete and edit posts and sub-posts. (login and password to admin account is 'admin')

2. Simple registration and logging system without Spring Security.

4. Users can view specific post details.

5. Users can comment posts.

6. Users can like other user posts.

7. Users can find posts trougch search bar

8. Most active users table.


# How to run 

1. Navigate to project folder in CMD
   
3. To run app type
```
docker-compose up --build
```
3. Open in your browser
```
http://localhost:8081/trips
```
