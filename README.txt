# SmartHealthCare
A mobile based Smart health Care system with Smart Analytics

SmartHealthCare focusses on complete heath analysis
the app is divided into three section ECG_tool , Caloriemeter and Food_Tracker .
the MAinActivity launches with three buttons one for ECG_tool , Caloriemeter and Food_Tracker each, then if he checks
Exercise tracker that will launch Activity tracker class of Caloriemter section . if he checks get checked up
then launches a survey page which first collects basic data about him weather he smokes, drinks , is in hypertension or
some one in his famly has cprevious cardiatic disease each field is subdivided into 4 categories and are rated among 0. 0.33. 0.66,
1.0 was proposed for maintaing a fuzzy matrix and do MAx min and predict the risk of Cardiatic disease but has not been used
after survey a login page opens up( WHICH HAS NOT YET BEEN IMPLEMENTED) just press sign in and it launches Bluetooh activity
which turn Bluetooth on and shows nearby BLE device and after one is chosen finally in Graphview class live plot of ECg is shown

 after datas are collected they are sent to server (it's  code is not in this repository) which using bios.py find the R-R interval
 and if differnce between subsequent R-R intereval is more than a threshold it then gives a message to user's doctor and registered
 relatives that person has cances of having arythmia.

 ECG_tool cointains

 ActivityHelper.class
 Models
 CustomAdapter
 Calculate
 GraphView
 HttpCall
 Bluetooth Activity

 BluetoothActivity Class is responsible for pairing phone with sensor or(pc during simulation) it lists all the nearby
 Bluetooth devices and Once you clicked it . It's device id or mac id is passed to next activity Now at runtime there
 run's an Async Task which synchronise the bluetoothGattserver and update the graph view at run time. i.e live ECG plotting
 (during the testing process the vibrator is enabled at each recived input)
 All the recived data are saved in an LinkedBlockingQueue and later converted to json and uploaded to the diserd server via
 anoher AsyncProcess called UploadJson .

 GraphView
 this class consists of three buttons and a raph canvas. the Bluetooth server is enablled once the button ppressed the disable button
 disconnects the BLE server and the upload button Uploads the json data to the server

 Models
 it defines the basic structure for the survey that's initially taken and defines some basics getter and setter methods

 CustomAdapter
 this class integrates Model to an Arrayadapter resulting in the survey that's taken

 HttpCall defines the post method for uploading the data(in this case json but can be changed)  to the given server
 the method Get takes the response from the server.

 ActivityHelper
 this class is resposible for avoiding the screen rotation so as to avoid data loss as (savedinstance methods are
 not implemented).

 Calculate
 this class was earlier introduced for calculating the MAX Min Matrix with the survey data to get aa rule based system
  a Fuzzy matrix was planned to be implemented BUT THE RESULT IS NOT IMPLEMENTED IN THE APP was to launch the
  Bluetooth Activity class only when MAx-Min value of above matrix comes >0.5 where val defines the risk .

  Caloriemeter contains

  Acceralation Data
  contains some method for getters and setters.
  ActivityTracker.java
  this class uses triaxial accerolometer data value and Normalises it b A = sqrt(x*x + y*y +z*z) as total accceraltion
  it first recives the weight of person in kgs and then calculate instantaneous KE via adding all 1/2*M*(A1-A2)*(0.002)
   0.002 s are multiplied with Acceralation data to get instantaneous velocity as Acceralometer updates data after 2 ms in
   Normal Mode. and a live plot is shown via staticticsview.class of his instantaneoueous Acceraltion data
   if the orientation of phone becomes horizontal while in ActivityTracker.class it's assumed that perso is jogging/running
   and has fallen and if he didn't waked up in 8-10s a toast message is shown (it can be furhter increased to launch
   the Ecg Activity as well as giving some push message to person's relative's that he has fallen)


  StatisticsView.class
  applies the Canvas class methods to plot the realtime graph of person instantaneous acceralation while running.

  DataBase Handler
  this class was earlier introduced to store the data in sqlite format but is not used yet an async process has to be applied
  for storing acceralatometer data as they are too fast UI may get blocked other wise.


  Food_tracked
  This class has not yet been completed as I was still in a dillema about it's effect on UX
  it was ntroduced with a vision to give user a pop message daily at night two check some check boxes regarding
   a very rough approximatte of food he has taken through out the day so tha t we can get an approximate idea about the
    Calorie intake and tah can be compared with the Calories burned via Caloriemeter section and can be compared
    to get approximate idea about user's lifestyle and get more idea about his chances of some cardiatic diseases.
    And also notifying the user about his habbit and showing a vizualisation of his calories intake vs calories burned.
    also predic chances of being fat etc.
