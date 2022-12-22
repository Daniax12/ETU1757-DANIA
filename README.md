# ETU1757-DANIA
 SGBD
ETU1757 - Dania Midera ANDRIANANDRASANA

Objectif: Simulation d'un systeme de gestion de base de donnees avec socket (SGBD)
Langage: JAVA avec SWING

**************************************************************
File content: Client - Server - Readme.txt
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
