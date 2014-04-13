TaskObserverSample
==================

Observer Design Pattern Fully functional Implementation

Class TaskObserverExample hold implementation of the Task Observer Design pattern.

It provides following functionality:

1. Checks how long was the task running and takes an action that can be defined
2. Supports father-son relations:
   Ex. Father thread has finished running but its children still hasn't.
   In this case Father task will not be removed fropm observation, 
   but will be qulified as waiting for the children tasks to finish
3. Tasks timeouts and behaviors when timeout occur can be defined by different task types
4. Each task type has its default that can be easily overriden when task is being submitted for an observation
