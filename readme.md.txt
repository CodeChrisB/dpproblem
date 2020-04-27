# The Dining Philosophers Problem

##### The Excercise was, come up with your own verison of the DPP, implement your idea using Java. (Animations would be appreciated)
###### The team :
 - Robert Freiseisen
 - Christhoper Buchberger (Project Leader)



So what did we come up with? 
- First of all we really had to be extremly close for the original problem, it was not allowed to have like 4 or 6 Persons nor did we were able to change the way the deadlock can be produced. So here is how we did it.

  - We have **5 test subject standing** arround a circuluar table.
  - All of the **test subject have been blindfoolded** so that they can not see  what the other ones do.
  - Between 2 person is 1 button which sums up to **5 buttons**
  - If a person can press both buttons he or she is allowed to sit down.
  - But if **everyone presses at the same time** they all will wait for their neighbour to release the button , but no one will ever do it ---> **Deadlock**
  - Magic

# How can we prevent it?

  - Let's say our test persons signed a contract that they can not sue us for any physical damage which might occur.
  
  - If 2 neighbours press the button at the same time they will both get an electric shock, and will be forced to let of the button.
  
  - If this sounds a bit to brutal let's suppose the buttons have a finger scanner inside and if test subject a presses the left button the right button will only be able to be pressed by subject a.
  -



### Just download the repository and try it foryourself
For any questions about this project contact me --> c.buchberger01@gmail.com
  