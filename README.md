# SNews
### Simple newsletter site as an exam project for Spring course at Softuni

## Main features
* Entirely responsive Angular frontend.
* Spring server featuring MySQL database.
* Responsive design and eye-candy.

### Client side - users
* 4 main news categories, including today's news as a 5th one.
* Basic search engine to easily find article by keywords or date.
* 12 most recent articles shown on front page. 8 for mobile.
* Only registered users can read the whole articles.
* Articles feature 4 (or less) most recent related articles.
* Warning label if user is looking at an old article.
* Users can upload an avatar and remove it if needed.
* Users can change their email and password.
* Users can request password recovery email to reset their passwords.
* Passwords can be revealed by user, if needed.
* "404 not found" and "under construction" pages. 
* All user forms feature appropriate amount of feedback and error validation.

### Client side - administration
* Admin accounts can add new articles.
* Admins have access to search engine in order to find a user. Full and partial username match supported.
* Admins can assign and remove rights to any user.
* Admins can disable registration of new users.

### Server side
* Custom password validation annotation.
* All POST request are checked for validity and appropriate error and status code is sent, if needed.
* Mail sender service sending html enhanced password recovery emails.
* Toggleable interceptor that handles if new user registrations are allowed.
* CSRF and CORS support.
* Password recovery tokens are for single use and have 15 minutes expiration time.
* 3 user roles - users, moderators, and administrators.

### Server technicalities 
* Controller advice class handling custom exceptions.
* ModelMapper as mapper library.
* All image uploaded to server are resized to appropriate dimensions ot save server space and bandwidth. Stored in db.
* Separate controllers handling articles, configuration, images, search queries, and user related requests.
* MySQL database with separate tables keeping track of articles, images, users and server configuration. Password recovery tokens.
* Scheduled task that cleans invalid password recovery tokens (used and expired) once a day.
* Unit tests.
