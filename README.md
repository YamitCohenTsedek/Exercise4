# flight-simulator-mobile-app

A Mobile app written in Android. <br/>
The app allows the user to connect to the [FlightGear](https://user-images.githubusercontent.com/45918740/103747212-faa5d880-500a-11eb-9123-dbe4c0052cff.jpg) flight simulator and remote control an aircraft with a joystick.<br/>
The app contains the following Activities:

### Login Activity:

The login window contains 2 fields for entering IP & Port, as well as a connection button to the flight simulator.<br/>

![1](https://user-images.githubusercontent.com/45918740/103747212-faa5d880-500a-11eb-9123-dbe4c0052cff.jpg)


Clicking the Connect button leads to the Joystick Activity which will be described below.<br/><br/>


### Joystick Activity

This window displays the Joystick which allows the user to change the aileron & elevator values of the aircraft.<br/>
The joystick sends SET commands to the flight simulator via TCP/IP communication protocol when the app is the client while the simulator is the server.<br/>
The connection to the server is created when the Activity is created and is closed when the Activity is destroyed (by OnDestroy method).<br/>


![2](https://user-images.githubusercontent.com/45918740/103747617-9afbfd00-500b-11eb-885e-6f528163124a.jpg)

The user can drag the inner circle within the limits of the outer circle: <br/>
* Right-left movement will control the aileron movement in values [-1, 1] respectively.
* Up-down movement will control the elevator movement in values [-1, 1] respectively.
* After release, the inner circle will return to the middle (0,0).<br/>

![3](https://user-images.githubusercontent.com/45918740/103748640-1611e300-500d-11eb-9e9a-6a443e5209de.jpg) 

![4](https://user-images.githubusercontent.com/45918740/103748679-288c1c80-500d-11eb-8e1e-13d112f19ca6.jpg)

![5](https://user-images.githubusercontent.com/45918740/103748753-4194cd80-500d-11eb-9546-85f61ec5c0c4.jpg)

![6](https://user-images.githubusercontent.com/45918740/103748806-53767080-500d-11eb-9468-46d57a34b384.jpg)


