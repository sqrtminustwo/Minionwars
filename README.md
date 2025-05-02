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
```
## Launching the game

Use the following commands to launch the program with JavaFX and required dependencies:

**Windows:**
```bash
java --module-path "path\to\javafx\lib" --add-modules javafx.controls,javafx.fxml ^
     -cp "minionwars-1.0-SNAPSHOT.jar;path\to\jdom2.jar" ^
     be.ugent.objprog.minionwars.MinionWars path\to\your\config.xml
```
**Linux/macOS:**
```bash
java --module-path path/to/javafx/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp minionwars-1.0-SNAPSHOT.jar:path/to/jdom2.jar \
     be.ugent.objprog.minionwars.MinionWars path/to/your/config.xml
```

## Configuration File

To allow aspects of the game to be adjusted without modifying the source code, we use a configuration file (`configs/game.xml`). This file is passed as an argument when launching the program.
> [!NOTE]
> You use a configuration file (e.g., `configs/game.xml`) to adjust minion, effect, power, and board properties. When launching the program, provide the file path as an argument. This file does not need to be named `game.xml`, nor does it need to be in the `configs` folder — any valid relative or absolute path is accepted.
> 
> Example:
> ```
> ./minion-wars folder1/folder2/.../configuratie.xml
> ```

The configuration file is written in XML and contains the following sections:

* Minion properties
* Effect properties
* Power properties
* Game board layout

The root structure of the XML configuration file:

```xml
<configuration>
    <minions>
        ...
    </minions>

    <effects>
        ...
    </effects>

    <powers>
        ...
    </powers>

    <field>
        ...
    </field>
</configuration>
```

### `<minions>`

Defines the available minions. Each minion has its own tag and supports the following attributes:

* `name`: name of the minion
* `cost`: deployment cost
* `movement`: movement range
* `attack`: attack strength
* `range`: attack range in the form `x y`, where `x` is the minimum and `y` is the maximum attack distance
* `defense`: defense strength
* `effect` (optional): special effect applied by the minion
* `effect-value` (optional): overrides the `value` attribute of the effect

Example:

```xml
<militia name="Militia" cost="5" movement="2" attack="1" range="1 3" defense="1" effect="paralysis" effect-value="1" />
```

<details>
<summary>Supported minion types</summary>

* `<militia>`
* `<spear>`
* `<sword>`
* `<axe>`
* `<archer>`
* `<scout>`
* `<cavalry>`
* `<heavy-cavalry>`
* `<mounted-archer>`
* `<catapult>`
* `<trebuchet>`

</details>

---

### `<effects>`

Defines the effects that can be applied to minions. Each `<effect>` tag can have the following attributes:

* `name`: name of the effect
* `duration`: number of turns the effect lasts
* `value` (optional): effect-specific value

Example:

```xml
<burn name="Burn" duration="2" value="1" />
```

<details>
<summary>Supported effects</summary>

* `<burn>`
* `<paralysis>`
* `<heal>`
* `<poison>`
* `<slow>`
* `<blindness>`
* `<rage>`

</details>

---

### `<powers>`

Defines special powers that affect an area of the board. Each `<power>` tag can have the following attributes:

* `name`: name of the power
* `radius`: diameter of the area of effect
* `value` (optional): damage or healing value
* `effect` (optional): effect applied by the power
* `effect-value` (optional): overrides the `value` attribute of the effect

Example:

```xml
<lightning name="Lightning" radius="3" effect="paralysis" value="2" />
```

<details>
<summary>Supported powers</summary>

* `<fireball>`
* `<lightning>`
* `<heal>`

</details>

---

### `<field>`

Defines the layout of the game board. Each tile is represented by a tag with:

* `x`: x-coordinate
* `y`: y-coordinate
* `homebase` (optional): `1` or `2` if the tile is a starting base for player 1 or 2

Example:

```xml
<dirt x="0" y="0" homebase="1" />
```

<details>
<summary>Supported tile types</summary>

* `<dirt>`: a basic walkable tile with no special effects
* `<forest>`: walkable, but it slows your minions down by 1
* `<mountains>`: walkable, but you can't attack from here
* `<water>`: not walkable, minions can’t move through it

</details>
