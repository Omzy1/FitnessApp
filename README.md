# FItness Android App Built With Java

## A functional Fitness app written in Java Android studio that demonstrates how to make a fitness app that use both a smartphone and a smartwatch device to track your activities and store your progress.

This project is a responsive Android app that allows you to track your activities ,store your activites and visualise your past activities.
This project use the Room database and use a one to many relationship to store the activity and user data.

The first time a user downloads the fitness app, they must enter their user information in the profile section of the smartphone app as well as their daily step target.
A message will appear in the wristwatch app telling the user to enter their details in the profile page and will block them from beginning an activity if they don't do so at least once before starting a workout.
The user can launch an activity on the smartwatch app after successfully entering their user info.
The user will be given the option to begin either walking or running.
Once a user begins an activity, they can view the timer, average heart rate, number of steps, calories burned, and pace in real time.
By pressing the button, the user can halt the activity and be taken to a new page where a summary of the action is displayed.
When a user completes an activity, the details of that activity are recorded in a database and then displayed on the progress page.
The daily amount of steps done is displayed on the home page of the smartphone app and is compared to the target daily step goal.
The homepage was created to provide a high-level summary of the app's information.

The project shows how to do the following:
* Creating a simple and effective navigation system.
* Sending multiple data between the smartphone and smartwatch app back and forth.
* Using Smartwatches Sensors such as step detecor, Heart rate and step counter.
* Calculating calories burnt , pace and distance.
* Creating a one to many relationship database(Room).
* Observing data from database table.
* Displaying activities in the progress page with RecylceView.
* Display a Pie chart.
* Display a circular and linear bar.

# Smartphone app

Home

![homePage1Phone](https://user-images.githubusercontent.com/93276114/235917714-b1349be2-89d4-49a7-8d17-8dcc398ae108.jpg)

![homePage2Phone](https://user-images.githubusercontent.com/93276114/235917762-a407578e-72e3-4ece-8b46-9125ad1a43a7.jpg)

Progress 

![progress1](https://user-images.githubusercontent.com/93276114/235917888-60c21986-e04d-4277-bc71-f9a6eba770dc.jpg)

![progress2](https://user-images.githubusercontent.com/93276114/235917913-39266998-5ea2-47c5-bebc-f8e3ab1ccc67.jpg)

Profile

![ProfileEnter](https://user-images.githubusercontent.com/93276114/235917269-822da3bb-e710-4ac3-a3f7-578b839a26e1.jpg)

![Profile](https://user-images.githubusercontent.com/93276114/235917075-e4ddf862-cab1-4840-b158-37f77dcd6b88.jpg)

![profilePhone2](https://user-images.githubusercontent.com/93276114/235917403-7f2299fa-835f-4706-9a80-89fe5afefa80.jpg)

# Smartwatch app

Home

![homeWatch](https://user-images.githubusercontent.com/93276114/235918108-0db75fce-5360-4d04-b7d6-be22a1ce31a2.jpg)

Start Activity

![test2Watch1](https://user-images.githubusercontent.com/93276114/235918390-f27e1f46-2295-4b59-b5b9-bffad39eb2e6.jpg)

Activity

![test2Watch2](https://user-images.githubusercontent.com/93276114/235918791-8faf7260-89c0-4c77-aa66-5600b1133554.jpg)

summary

![test2Watch3](https://user-images.githubusercontent.com/93276114/235919129-bcc87e8f-6f16-4eda-918c-e71ed88694ad.jpg)

![test2Watch4](https://user-images.githubusercontent.com/93276114/235919171-8d3c7477-15f4-4bed-822b-693ab9db25df.jpg)






