  

# Projet intégré Startup POC : Time Manager 
![Logo](https://i.ibb.co/Kmb1cL7/Time-Manager-free-file.png)

  

## Sujet : Application de saisie de temps

  

Réalisation d’une application web permettant la saisie, la consultation, l'édition des temps dans une entreprise de développement

  

## User Stories :

- En tant qu’utilisateur, on peut se connecter via un Login / Password (les profils supportés sont Admin, Manager, User)

 - En tant que **User** :
	 - on peut saisir ses temps, à la granularité de l’heure (choix projet + temps) puis les soummettre
	 - on peut éditer un compte-rendu mensuel (export PDF)

- En tant que **Manager** - On peut faire la même chose qu'un User ainsi que :
	- on peut consulter les temps saisis par les Users qui lui sont rattachés
	- on peut éditer les compte-rendus mensuels des Users qui lui sont rattachés (export PDF)
	- on peut débloquer la soumission des temps du mois
	- on peut saisir des projets (ajout et suppression)
	- on peut saisir de nouveaux Users qui lui seront rattachés

- En tant qu'**Admin** - On peut faire la même chose qu'un User + Manager ainsi que :
	- on peut changer le statut d’un User (Manager / Admin)
	- on peut changer l’affectation d’un User (changement de Manager)
	- on peut modifier les informations d'un utilisateur

## Installation
  ### BackEnd :

First, you need to install a MySQL server (we suggest  [MySQL Workbench](https://dev.mysql.com/downloads/workbench/))

The first step is to create a database named : poc

Then import the SQL Script : [Db_structure.sql](https://github.com/AmineBouzid/poc/blob/main/Db_structure.sql "Db_structure.sql") to your created Database and execute it.

You have to run your database server before running the application so you can connect it to the database.

Then, all you have to do is to open the maven project named as server on your IDE and run ServerApplication.java as a springboot application.
> **Note:** You need to modify MySQL Login information and ports in  _application.properties_  file in the server project.



### FrontEnd :

First, you need to install  [NodeJs](https://nodejs.org/en/download/)

In the front folder, open a Terminal and run the following commands to install dependencies :

```

npm install


```

and then you start the Angular devserver :

```

npm start


```

Finally on your browser go to :  [http://localhost:4200/](http://localhost:4200/)
  
  ## Demo 
[![Demo81989d52d0eb02a2.gif](https://s10.gifyu.com/images/Demo81989d52d0eb02a2.gif)](https://gifyu.com/image/SzHav)

## Authors

  

- [@Amine BOUZID](https://github.com/AmineBouzid) - amine.bouzid@telecom-st-etienne.fr

- @Eva LUVISUTTO - eva.luvisutto@telecom-st-etienne.fr

- @Mathieu Semin- mathieu.semin@telecom-st-etienne.fr


