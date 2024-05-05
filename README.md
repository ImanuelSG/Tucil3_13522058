# Tucil 3 IF-2211 Strategi Algorima : Word Ladder Solver Using UCS, Greedy Best First Search and A\* Algorithm

This project was created to fulfill the third mini task for the Algorithm Strategy Course at the Institute of Technology, Bandung. The project aims to solve the popular Word Ladder game using three different algorithms, then compare them to determine which is best suited for this problem.

## Table of Contents

- [General Information](#general-information)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Screenshots](#screenshots)
- [Setup](#setup)
- [Usage](#usage)
- [Project Status](#project-status)
- [Room for Improvement](#room-for-improvement)
- [Acknowledgements](#acknowledgements)
- [Contact](#contact)
<!-- * [License](#license) -->

## General Information

A word ladder game is a puzzle where players transform one word into another by changing one letter at a time, with each intermediate word also being valid. The goal is to find the shortest path from the starting word to the target word using a series of valid transformations. The dictionary used in this project can be found here https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/dictionary.txt

## Technologies Used

- Java - version 1.8.0_381

## Features

- CLI Solver
- GUI Solver
- Detailed Output Steps Visualization

## Screenshots

![Example screenshot](./img/screenshot.png)

<!-- If you have screenshots you'd like to share, include them here. -->

## Setup

In order to use this project, make sure to have Java installed on your computer. You can then follow these steps

#### 1. Clone This Repository

```bash
git clone https://github.com/ImanuelSG/Tucil3_13522058.git
```

#### 2. Navigate towards the main directory

```bash
cd Tucil3_13522058
```

#### 3. Generate the map of your dictionary

If you want to use your own dictionary, please run this command below before moving on. Else just ignore this step

```bash
./generateMap.bat for Windows or ./generateMap.sh for Linux
```

#### 4. Run the GUI/CLI starter using.bat / .sh

If you want to use the CLI, use this command

```bash
./runCLI.bat for Windows or ./runCLI.sh for Linux
```

If you want to use the GUI, use this command

```bash
./runGUI.bat for Windows or ./runGUI.sh for Linux
```

## Project Status

| Poin                                                                                                                                    | Ya      | Tidak |
| --------------------------------------------------------------------------------------------------------------------------------------- | ------- | ----- |
| 1. Program berhasil dijalankan.                                                                                                         | &#9745; |       |
| 2. Program dapat menemukan rangkaian kata dari start word ke end word sesuai aturan permainan dengan algoritma UCS                      | &#9745; |       |
| 3. Solusi yang diberikan pada algoritma UCS optimal                                                                                     | &#9745; |       |
| 4. Program dapat menemukan rangkaian kata dari start word ke end word sesuai aturan permainan dengan algoritma Greedy Best First Search | &#9745; |       |
| 5. Program dapat menemukan rangkaian kata dari start word ke end word sesuai aturan permainan dengan algoritma A\*                      | &#9745; |       |
| 6. Solusi yang diberikan pada algoritma A\* optimal                                                                                     | &#9745; |       |
| 7. [Bonus]: Program memiliki tampilan GUI                                                                                               | &#9745; |       |

## Room for Improvement

Because this project is speedrun in one week, there are a lot of things to be improved on.
Room for improvement:

- Use better OOP Design Principles in order ot make more maintainable and sustainable code
- Search for better data structures, pruning strategies, and other performance related things to improve the execution time of this program.
- Explore better GUI features and add more functionality to the GUI

## Contact

You cant contact me via
| Name | NIM | Contact |Github
| ------------------------- | -------- | --------------------------- |------
| Imanuel Sebastian Girsang | 13522058 | 13522058@std.stei.itb.ac.id |[ImanuelSG](https:/github.com/ImanuelSG)
