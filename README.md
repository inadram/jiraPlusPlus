jiraPlusPlus
============

Sync your physical and electronic agile board with one click !

Problem 
In modern businesses teams often operate following agile methodologies . Agile focuses on getting the business value from the team as soon as possible. Agile also encourages the use of a board to show work being done by the team and how it is progressing. Most agile teams have both a physical and electronic boards which are hard to keep in sync.

Solution
JIRA Plus Plus is our attempt to simplify this syncing with a device everyone carries with them -a camera phone. Once everyone has updated the board all you need to do is to take a photo and email it to JIRA Plus Plus. JIRA Plus Plus reads the pictures and then updates the electronic borad automatically. JIRA is a commercially available electronic agile board. This solution builds on top of their offering.

Implementation
JIRA Plus Plus is written in Java and following a simple path. Firstly it reads the emails and download the attachments. it then reads the QR codes using ZXing library. Unfortunately we don't always get information about all the tickets so we have to make some informed inferences to ensure the tickets are updated correctly. Once we have worked this out we then make use of JIRA's HTTP API and update the status of the tickets.

http://jiraplusplus.com/

