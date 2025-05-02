# Minion Wars

**Minion Wars** is a simple yet strategic two-player game developed in **Java 21** using **JavaFX 21**.

In this game, players take turns deploying their minions on their respective sides of the battlefield. Once both players confirm they are ready, the battle begins.

During each turn, a player must make tactical decisions: whether to move minions, launch attacks, or allow minions to rest. Additionally, players must consider special bonus actions that can significantly impact the outcome of the game.

The game continues until one player has no minions remaining on the board.

## Requirements

This project uses the following Java modules:

```java
open module be.ugent.objprog.minionwars {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jdom2;

    exports be.ugent.objprog.minionwars;
}
