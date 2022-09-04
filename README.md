# bowling-game
### Bowling simulator in java

This is a barebone of a bowling game simulator. Feel free to implement your own based on this barebone.

### Glossary
* Roll
  * One throw on the pitch to take down standing pins.
* Frame
  * A full game consists of 10 frames. Each frame holds maximum two rolls, or three in the last frame.
  * The frame ends if all pins are taken down (unless it's the last frame).
  * The last frame can consist of three rolls, IF the two first are either spare or strike.
* Strike
  * If the first throw in a frame gets all the pins (10 pts) then a bonus is awarded. The next two rolls results are added to this frame's score.
  * If the last frame has a strike as the first roll, then an extra roll is awarded (3rd) to calculate the bonus for the frame.
  * A strike completes the frame, unless its the last thou.
* Spare
  * If all pins are taken down on the 2nd throw in the frame, the user is awarded a bonus. Then the next frames first roll is added to this frame's result.
  * On the last frame if it is a spare, then a 3rd roll is awarded to calculate the bonus of the frame.

### References
* [rules](https://www.playerssports.net/page/bowling-rules)
* [A good calculator](https://www.bowlinggenius.com/)
