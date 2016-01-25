# knowledgebase

## Beschreibung

knowledgebase ist ein Prototyp eines client-server-basierten JAVA / JavaScript Frameworks unter Nutzung von OpenRDF Sesame. Daten können in einem UI erfasst, auf dem Server in Triples umgewandelt und mit Hilfe von filter.js angezeigt werden.

## Vorraussetzungen

* Apache Tomcat installieren
* deploy war-files OpenRDF Sesame Server und Workbench in Version 2.8.7 [[Download](http://sourceforge.net/projects/sesame/files/Sesame%202/2.8.7/)]
* Config-Variablen anpassen (siehe unten)
* built knowledgebase
* deploy knowledgebase.war
* fertig!

## Dokumentation
* [siehe knowledgebase Wiki](../../wiki)

## Quellcode
* knowledgebase ist ein JAVA Netbeans Projekt.
* Anpassen der Variablen in JAVA [Config.java](/netbeans/knowledgebase/src/main/webapp/config.js)
  * TRIPLESTORE_SERVER
  * INSTANCE_HOST
  * TRIPLESTORE_REPOSITORY
* Anpassen der Variablen in JAVASCRIPT [config.js](/netbeans/knowledgebase/src/main/webapp/config.js)
  * Config.HOST

## Beispiele
* [mainzed knowledgebase](http://labeling.i3mainz.hs-mainz.de/knowledgebase/)
