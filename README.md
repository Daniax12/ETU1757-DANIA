# ETU1757-DANIA
 SGBD
ETU1757 - Dania Midera ANDRIANANDRASANA

Objectif: Simulation d'un systeme de gestion de base de donnees avec socket (SGBD)
Langage: JAVA avec SWING

**************************************************************
File content: Client - Server - Video 
**************************************************************

SERVER : Contenant
	interface graphique pour demarrer et stopper le serveur (HostPage.java)
	classes utilent pour analyser les requetes des clients (Table, Query, Skl)
	multithread afin que plusieurs clients peuvent se connecter en meme temps(MainThread.java)
	classes implementant Seriazable afin que ces dernieres puissent etre envoyer vie Socket
		(PacketRequest, PacketResponse)

CLIENT: Contenant
	interface graphique afin de s'identifier, de creer un user, de se deconnecter
	interface graphique pour afficher les resultats des requetes 
	classes utiles pour les reponses des requetes(Table)
	classes implementant Seriazable afin que ces dernieres puissent etre envoyer vie Socket
		(PacketRequest, PacketResponse)

*****************************************************************
Compilation *.java (Server and Client)

Execution
Server => java view.Main
Client => java client.Client



*****************************************************************
Resume 
*****************************************************************
Mode de stockage:
Directory Server > DATABASE > (Liste des databases de chaque utilisateur) > (Liste des tables de chaque database)

Interface graphique Server:
Pour demarrer ou stopper le server

Interface graphique Client:
	- Insertion IP (A condition que le Client et le Server sont sur le meme reseau
	- Creation nouveau utilisateur (creation nouveau directory pour le nouveau utilisateur)
	- Login
	- Deconnection
	
Backend:
Modele : MVC
Les classes les plus importantes:
	- Server > 
		- Server.class => extends Thread afin de pouvoir ecouter en meme temps plusieurs clients et de gerer chaque client en fonction de leur requete
		- Table.class => Table a retourner vers le Client (titre de colonnes et data)
		- Skl.class => analyse des vocabulaires du requete de client => retour Table
	- Client >
		- tous les listeners en tant que controller qui envoient les requetes et ecoutent les reponses venant du Server
	- Server et Client >
		- PacketRequest.class => implements Serializable 
		- PacketResponse.class => implements Serializable 
		Tous deux sont les objects qui sont envoyes dans les ObjectOutputStream et ObjectInputStream afin de savoir quels sont les types d'action et de reponse venant du Client/Server

****************************************************************
Toutes les requetes :
****************************************************************

	---------------------------------------------------------------
	DATABASE CONTENT
	---------------------------------------------------------------

USE databasename
SHOW database

	-------------------------------------------------------------
	CREATE
	-------------------------------------------------------------

CREATE DATABASE databasename
CREATE TABLE tableName (columnName, columnName1, columnName2)

	--------------------------------------------------------------
	SELECT 
	--------------------------------------------------------------

SELECT * FROM tab1
SELECT * FROM tab1 WHERE columnName = valueColumn
SELECT * FROM tab1 WHERE columnName = valueColumn AND columnName1 = valueColumn1
SELECT * FROM tab1 WHERE columnName = valueColumn OR columnName1 = valueColumn1 
SELECT * FROM tab1 WHERE columnName = valueColumn AND columnName1 = valueColumn1 AND columnName2 = valueColumn2
SELECT * FROM tab1 WHERE columnName = valueColumn AND columnName1 = valueColumn1 OR columnName2 = valueColumn2
SELECT * FROM tab1 NOT IN SELECT * FROM tab2

SELECT * FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName
SELECT * FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn
SELECT * FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn AND columnName1 = valueColumn1
SELECT * FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn OR columnName1 = valueColumn1 
SELECT * FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn AND columnName1 = valueColumn1 AND columnName2 = valueColumn2
SELECT * FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn AND columnName1 = valueColumn1 OR columnName2 = valueColumn2


SELECT columnName, columnName1 FROM tab1
SELECT columnName, columnName1 FROM tab1 WHERE columnName = valueColumn
SELECT columnName, columnName1 FROM tab1 WHERE columnName = valueColumn AND columnName1 = valueColumn1
SELECT columnName, columnName1 FROM tab1 WHERE columnName = valueColumn OR columnName1 = valueColumn1 
SELECT columnName, columnName1 FROM tab1 WHERE columnName = valueColumn AND columnName1 = valueColumn1 AND columnName2 = valueColumn2
SELECT columnName, columnName1 FROM tab1 WHERE columnName = valueColumn AND columnName1 = valueColumn1 OR columnName2 = valueColumn2

SELECT columnName, columnName1 FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName
SELECT columnName, columnName1 FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn
SELECT columnName, columnName1 FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn AND columnName1 = valueColumn1
SELECT columnName, columnName1 FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn OR columnName1 = valueColumn1 
SELECT columnName1, columnName2 FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn AND columnName1 = valueColumn1 AND columnName2 = valueColumn2
SELECT columnName1, columnName2 FROM tab1 JOIN tab2 ON tab1.columnName = tab2.columnName WHERE columnName = valueColumn AND columnName1 = valueColumn1 OR columnName2 = valueColumn2

	---------------------------------------------------------------
	INSERT	
	---------------------------------------------------------------
INSERT INTO tab1 VALUES (valueColumn1, valueColumn2, valueColumn3)

	--------------------------------------------------------------
	UPDATE
	--------------------------------------------------------------
UPDATE tab1 SET columnName = valueColumn WHERE columnCondition = valueCondition1

	--------------------------------------------------------------
	DELETE
	-------------------------------------------------------------
DELETE FROM tab1 WHERE columnCondition = valueColumn
DELETE FROM tab1 (deleting all rows)
