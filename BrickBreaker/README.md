# **Team Project - BrickBreaker (Null Pointer Exception)**

### Game Introduction and Plan
We are implementing a  simple brick breaker game which will be having layers of bricks and ball with which to break the layers. The player moves the paddle from left to right to keep the ball from falling. A life is used when the player fails to hit the ball with paddle. The paddle doesn’t bounce the ball like a mirror, although it does so when the ball hits right in the middle. The closer the bounce take place to the left end of the paddle, a more significant left turn is added to an expected mirror bouncing.

A regular brick disappears when it’s hit by the ball, or breaks a little if it’s a bulkier brick. At the same time, for every brick hit, there will be increase in the score. And by default the player will be given 3 lives. If the user finishes off these 3 lives, then the game will be over. In addition to that, there will be some special bricks in the game. If the player hits those bricks, a new ball will be added into game. When the user hits this new ball with paddle, the paddle size increases. We are also planning to implement background music for our brick breaker game.

### **Link to your team's project Videos**

https://drive.google.com/drive/folders/1HMNFoW9TjGAdcdK2f4IM94VUPI1yE3SH?usp=sharing

### **Our Team**
* Anshul Kumar Shandilya
* Boteng Yuan
* Rafic Hasbini
* Varun Teja Maguluri

### **Individual Journals**

* Anshul Kumar Shandilya's Journal - [Week 1](Journals/Anshul/Week1.md), [Week 2](Journals/Anshul/Week2.md), [Week 3](Journals/Anshul/Week3.md), [Week 4](Journals/Anshul/Week4.md)
* Boteng Yuan's Journal - [Week 1](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Boteng/Week1.md), [Week 2](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Boteng/Week2.md), [Week 3](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Boteng/Week3.md), [Week 4](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Boteng/Week4.md)
* Rafic Hasbini's Journal - [Week1](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Rafic/Week1.md), [Week 2](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Rafic/Week2.md), [Week 3](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Rafic/Week3.md), [Week 4](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Rafic/Week4.md).
* Varun Teja Maguluri's Journal - [Week 1](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Varun/Week1.md), [Week 2](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Varun/Week2.md), [Week 3](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Varun/Week3.md), [Week 4](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Journals/Varun/Week4.md).

### **Summarizing our game**
The game that we as a team developing is Brick Breaker. So, the main concept of this game is, the player will be given a ball and all the time the ball will be in motion. And the player will just be controlling the paddle at the bottom which he can just move that paddle left and right. The main task of the player is to hit the ball with paddle and also make sure the ball hits the bricks at top. If he fails to hit any brick or fails to hit the ball with his paddle, then one life of the player will be lost. By default, each player will be given 3 lives. So, in this game, there were extended features like special bricks whose functionality is whenever the user hits this special brick, a new ball will be added to the game and if he managed to hit that ball, his paddle length will be increased. And also another extra feature that is planned to implement in this game is background music for this game.

### **A High Level Architecture Diagram**

![Class_UML](Diagrams/uml_final.jpg)

- Strategy pattern for switching music
![Strategy Pattern](https://user-images.githubusercontent.com/98674002/168416783-9e2013d6-861b-4587-bb89-c36364cfbe9b.png)


- Strategy pattern for different Bricks
![BrickStrategy](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/BrickStrategy.png)

- Strategy pattern for different game mode
![GameStrategy](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/GameStrategy.png)

- State machine pattern for change racket move direction
![DirectionStateDiagram](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/DirectionStateDiagram.png)

- Decorator pattern for game ending images
![ImageDecorator](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/ImageDecorator.png)

- Decorator pattern for different racket types
![RacketDecoratorDiagram](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/RacketDecoratorDiagram.png)

- Observer pattern for lives
![LivesObserver](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/LivesObserver.png)

- Observer pattenr for score
![ScoreObserver](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/ScoreObserver.png)

- Observer pattern for speed
![SpeedObserver](https://github.com/nguyensjsu/sp22-202-null-pointer-exception/blob/main/Diagrams/SpeedObserver.png)


### **Burndown Chart**

![500_F_222159309_r17sMh7YurdVFbpkqPI9QZ6PwaJFySlg](https://user-images.githubusercontent.com/98674002/168414320-00263328-98eb-434f-b683-7d478509ae10.jpeg)

### **UI Wireframes**

<img width="678" alt="Screen Shot 2022-05-13 at 11 48 07 PM" src="https://user-images.githubusercontent.com/98674002/168414399-1b2dda4e-57cc-461c-999d-7e9f43599042.png">

### **A summary of the key features (include any special design ideas implemented)**

Change racket size bricks: When the blue bricks are broken, they will drop an item. If the item is picked up, then the racket will change size:
<br>
  - Long Racket
  - Short Racket
<br>
Rainbow brick : Switch arrow direction brick.
<br>
  - changes the direction of arrows once this brick is destroyed
<br>
Green brick: Additional life brick.
<br>
  - By destorying this brick: the player is awarded an additional life/chance.
<br>
Red brick: removes a life.
<br>
  - By destroying this brick the player loses a life
  - If player is on their last life, they lose the game.
<br>
Two player mode:
<br>
  - Allow for two players to play together to beat the game on the same team.
  - Two rackets are present in two player mode.
<br>
Blue Brick : Special Brick
<br>
  - Drops a special ball, which may increase or decrease the racket size.
 <br>
 Background Sounds :
 <br>
  - Audio implementation for both winning and losing game.
