INSERT INTO department (name, location) VALUES ('IT','Steindamm 80');
INSERT INTO department (name, location) VALUES ('Marketing','Steindamm 71');
INSERT INTO department (name, location) VALUES ('Finanzen','Steindamm 71');
INSERT INTO switch (name) VALUES ('IT-001');
INSERT INTO switch (name) VALUES ('Finanzen-001');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:00:5e:00:53:af', '192.168.10.1', 'PC-IT-001', 1, 'PC');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:10:4a:10:53:bd', '192.168.10.2', 'PC-IT-002', 1, 'PC');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:12:2f:f2:20:ab', '192.168.10.3', 'Laptop-IT-001', 1, 'Laptop');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:02:16:g6:j5:k9', '192.168.20.1', 'Laptop-MA-001', 2, 'Laptop');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:02:16:g6:j5:k5', '192.168.20.2', 'Laptop-MA-002', 2, 'Laptop');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:02:16:g6:j5:55', '192.168.20.2', 'Laptop-MA-002', 2, 'Laptop');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:01:30:f4:j2:5h', '192.168.10.1', 'PC-FI-001', 3, 'PC');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:01:40:f4:j2:6g', '192.168.10.2', 'PC-FI-002', 3, 'PC');
INSERT INTO host (mac_id, ip, name, department_id, system) VALUES ('00:02:30:f4:j2:bh', '192.168.10.3', 'PC-FI-003', 3, 'PC');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (1, 'FastEthernet0/1', 'Access', '00:00:5e:00:53:af', 'VLAN30');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (1, 'FastEthernet0/2', 'Access', '00:10:4a:10:53:bd', 'VLAN30');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (1, 'FastEthernet0/3', 'Access', '00:10:4a:10:53:ab', 'VLAN20');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (1, 'FastEthernet0/4', 'Access', '00:10:4a:10:53:k5', 'VLAN20');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (1, 'FastEthernet0/5', 'Access', null, 'VLAN30');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (1, 'FastEthernet0/7', 'Access', '00:02:16:g6:j5:k9', 'VLAN20');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (2, 'FastEthernet0/1', 'Access', '00:01:30:f4:j2:5h', 'VLAN10');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (2, 'FastEthernet0/2', 'Access', '00:01:40:f4:j2:6g', 'VLAN10');
INSERT INTO port (switch_id, name, port_mode, host_mac_id, vlan) VALUES (2, 'FastEthernet0/3', 'Access', '00:02:30:f4:j2:bh', 'VLAN10');


/*
 \c postgres
DROP DATABASE krautundthueringerkloesse;
CREATE DATABASE krautundthueringerkloesse;
\c krautundthueringerkloesse


CREATE TABLE KUNDE (
                       KUNDENNR        INTEGER PRIMARY KEY NOT NULL,
                       NACHNAME        VARCHAR(50),
                       VORNAME         VARCHAR(50),
                       GEBURTSDATUM    DATE,
                       STRASSE         VARCHAR(50),
                       HAUSNR	    VARCHAR(6),
                       PLZ             VARCHAR(5),
                       ORT             VARCHAR(50),
                       TELEFON         VARCHAR(25),
                       EMAIL           VARCHAR(50)
);

CREATE TABLE ZUTAT(
                      ZUTATENNR           INTEGER NOT NULL,
                      BEZEICHNUNG         VARCHAR(50),
                      EINHEIT        	VARCHAR (25),
                      NETTOPREIS	        DECIMAL(10,2),
                      BESTAND             INTEGER,
                      LIEFERANT           INTEGER,
                      KALORIEN            INTEGER,
                      KOHLENHYDRATE       DECIMAL (10,2),
                      PROTEIN             DECIMAL(10,2),
                      PRIMARY KEY         (ZUTATENNR)
);

CREATE TABLE REZEPT(
                       REZEPTNR       SERIAL PRIMARY KEY NOT NULL,
                       REZEPTNAME     VARCHAR(20)
);

CREATE TABLE KATEGORIE(
    KATEGORIENR      SERIAL NOT NULL,
    KATEGORIENAME    VARCHAR(20),
    PRIMARY KEY     (KATEGORIENR)
);

CREATE TABLE REZEPT_KATEGORIE(
    REZEPTNR         INTEGER NOT NULL,
    KATEGORIENR      INTEGER NOT NULL,
    PRIMARY KEY     (REZEPTNR, KATEGORIENR)
);

CREATE TABLE BESCHRAENKUNG(
                              BESCHRAENKUNGSNR      SERIAL NOT NULL,
                              BESCHRAENKUNGSNAME    VARCHAR(20),
                              PRIMARY KEY     (BESCHRAENKUNGSNR)
);

CREATE TABLE REZEPT_BESCHRAENKUNG(
                                 REZEPTNR         INTEGER NOT NULL,
                                 BESCHRAENKUNGSNR      INTEGER NOT NULL,
                                 PRIMARY KEY     (REZEPTNR, BESCHRAENKUNGSNR)
);

CREATE TABLE BESTELLUNG (
                            BESTELLNR        SERIAL NOT NULL,
                            KUNDENNR         INTEGER,
                            BESTELLDATUM     DATE,
                            RECHNUNGSBETRAG  DECIMAL(10,2),
                            PRIMARY KEY     (BESTELLNR)
);


CREATE TABLE REZEPT_ZUTAT(
                            REZEPTNR        INTEGER NOT NULL,
                            ZUTATENNR       INTEGER NOT NULL,
                            MENGE           INTEGER,
                            PRIMARY KEY     (REZEPTNR, ZUTATENNR)
);

CREATE TABLE BESTELLUNG_REZEPT (
                                 BESTELLNR       INTEGER NOT NULL,
                                 REZEPTNR        INTEGER,
                                 MENGE           INTEGER,
                                 PRIMARY KEY (BESTELLNR, REZEPTNR)
);

CREATE TABLE LIEFERANT (
                           LIEFERANTENNR   INTEGER PRIMARY KEY NOT NULL,
                           LIEFERANTENNAME VARCHAR(50),
                           STRASSE         VARCHAR(50),
                           HAUSNR          VARCHAR(6),
                           PLZ             VARCHAR(5),
                           ORT             VARCHAR(50),
                           TELEFON         VARCHAR(25),
                           EMAIL           VARCHAR(50)
);

/******************************************************************************/
/***                              Primary Keys                              ***/
/******************************************************************************/



/******************************************************************************/
/***                              Foreign Keys                              ***/
/******************************************************************************/

ALTER TABLE ZUTAT ADD FOREIGN KEY (LIEFERANT) REFERENCES LIEFERANT (LIEFERANTENNR);
ALTER TABLE BESTELLUNG_REZEPT ADD FOREIGN KEY (BESTELLNR) REFERENCES BESTELLUNG (BESTELLNR);
ALTER TABLE BESTELLUNG_REZEPT ADD FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR);
ALTER TABLE BESTELLUNG ADD FOREIGN KEY (KUNDENNR) REFERENCES KUNDE (KUNDENNR);
ALTER TABLE REZEPT_ZUTAT ADD FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR);
ALTER TABLE REZEPT_ZUTAT ADD FOREIGN KEY (ZUTATENNR) REFERENCES ZUTAT (ZUTATENNR);
ALTER TABLE REZEPT_KATEGORIE ADD FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR);
ALTER TABLE REZEPT_KATEGORIE ADD FOREIGN KEY (KATEGORIENR) REFERENCES KATEGORIE (KATEGORIENR);
ALTER TABLE REZEPT_BESCHRAENKUNG ADD FOREIGN KEY (BESCHRAENKUNGSNR) REFERENCES BESCHRAENKUNG (BESCHRAENKUNGSNR);
ALTER TABLE REZEPT_BESCHRAENKUNG ADD FOREIGN KEY (REZEPTNR) REFERENCES REZEPT (REZEPTNR);


 \c krautundthueringerkloesse;

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2001, 'Wellensteyn','Kira','1990-05-05','Eppendorfer Landstrasse', '104', '20249','Hamburg','040/443322','k.wellensteyn@yahoo.de');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2002, 'Foede','Dorothea','2000-03-24','Ohmstraße', '23', '22765','Hamburg','040/543822','d.foede@web.de');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2003, 'Leberer','Sigrid','1989-09-21','Bilser Berg', '6', '20459','Hamburg','0175/1234588','sigrid@leberer.de');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2004, 'Soerensen','Hanna','1974-04-03','Alter Teichweg', '95', '22049','Hamburg','040/634578','h.soerensen@yahoo.de');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2005, 'Schnitter','Marten','1964-04-17','Stuebels', '10', '22835','Barsbuettel','0176/447587','schni_mart@gmail.com');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2006, 'Maurer','Belinda','1978-09-09','Grotelertwiete', '4a', '21075','Hamburg','040/332189','belinda1978@yahoo.de');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2007, 'Gessert','Armin','1978-01-29','Kuestersweg', '3', '21079','Hamburg','040/67890','armin@gessert.de');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2008, 'Haessig','Jean-Marc','1982-08-30','Neugrabener Bahnhofstraße', '30', '21149','Hamburg','0178-67013390','jm@haessig.de');

INSERT INTO KUNDE (KUNDENNR, NACHNAME, VORNAME, GEBURTSDATUM, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (2009, 'Urocki','Eric','1999-12-04','Elbchaussee', '228', '22605','Hamburg','0152-96701390','urocki@outlook.de');

INSERT INTO LIEFERANT (LIEFERANTENNR, LIEFERANTENNAME, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (101, 'Bio-Hof Mueller', 'Dorfstraße', '74', '24354', 'Weseby', '04354-9080', 'mueller@biohof.de');

INSERT INTO LIEFERANT (LIEFERANTENNR, LIEFERANTENNAME, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (102, 'Obst-Hof Altes Land', 'Westerjork 74', '76', '21635', 'Jork', '04162-4523', 'info@biohof-altesland.de');

INSERT INTO LIEFERANT (LIEFERANTENNR, LIEFERANTENNAME, STRASSE, HAUSNR, PLZ, ORT, TELEFON, EMAIL) VALUES (103, 'Molkerei Henning', 'Molkereiwegkundekunde', '13','19217', 'Dechow', '038873-8976', 'info@molkerei-henning.de');

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1001,'Zucchini','Stueck', 0.89, 100, 101,19,2,1.6);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1002,'Zwiebel','Stueck', 0.15, 50, 101, 28, 4.9, 1.20);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1003, 'Tomate', 'Stueck', 0.45, 50, 101, 18, 2.6, 1);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1004, 'Schalotte', 'Stueck', 0.20, 500, 101, 25, 3.3, 1.5);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1005, 'Karotte', 'Stueck', 0.30, 500, 101, 41, 10, 0.9);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1006, 'Kartoffel', 'Stueck', 0.15, 1500, 101, 71, 14.6, 2);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1007, 'Rucola', 'Bund', 0.90, 10, 101, 27, 2.1, 2.6);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1008, 'Lauch', 'Stueck', 1.2, 35, 101, 29, 3.3, 2.1);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1009, 'Knoblauch', 'Stueck', 0.25, 250, 101, 141, 28.4, 6.1);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1010, 'Basilikum', 'Bund', 1.3, 10, 101, 41, 5.1, 3.1);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1011, 'Sueßkartoffel', 'Stueck', 2.0, 200, 101, 86, 20, 1.6);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (1012, 'Schnittlauch', 'Bund', 0.9, 10, 101, 28, 1, 3);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (2001, 'Apfel', 'Stueck', 1.2, 750, 102, 54, 14.4, 0.3);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (3001, 'Vollmilch. 3.5%', 'Liter', 1.5, 50, 103, 65, 4.7, 3.4);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (3002, 'Mozzarella', 'Packung', 3.5, 20, 103, 241, 1, 18.1);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (3003, 'Butter', 'Stueck', 3.0, 50, 103, 741, 0.6, 0.7);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (4001, 'Ei', 'Stueck', 0.4, 300, 102, 137, 1.5, 11.9);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (5001, 'Wiener Wuerstchen', 'Paar', 1.8, 40, 101, 331, 1.2, 9.9);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (9001, 'Tofu-Wuerstchen', 'Stueck', 1.8, 20, 103, 252, 7, 17);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (6408, 'Couscous', 'Packung', 1.9, 15, 102, 351, 67, 12);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (7043, 'Gemuesebruehe', 'Wuerfel', 0.2, 4000, 101, 1, 0.5, 0.5);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (6300, 'Kichererbsen', 'Dose', 1.0, 400, 103, 150, 21.2, 9);

INSERT INTO ZUTAT (ZUTATENNR, BEZEICHNUNG, EINHEIT, NETTOPREIS, BESTAND, lieferant, KALORIEN, KOHLENHYDRATE, PROTEIN) VALUES (6666, 'Thueringer Mett', 'Dose', 1.0, 1000, 103, 200, 25.5, 50);

INSERT INTO REZEPT(REZEPTNR, REZEPTNAME) VALUES (6300, 'Kartoffelsuppe');

INSERT INTO REZEPT(REZEPTNR, REZEPTNAME) VALUES (6400, 'Nusskuchen');

INSERT INTO REZEPT(REZEPTNR, REZEPTNAME) VALUES (6500, 'Thueringer Kloesse');

INSERT INTO KATEGORIE(KATEGORIENR, KATEGORIENAME) VALUES (1, 'VEGETARISCH');

INSERT INTO KATEGORIE(KATEGORIENR, KATEGORIENAME) VALUES (2, 'VEGAN');

INSERT INTO KATEGORIE(KATEGORIENR, KATEGORIENAME) VALUES (3, 'FRUTARISCH');

INSERT INTO KATEGORIE(KATEGORIENR, KATEGORIENAME) VALUES (4, 'HIGHCARB');

INSERT INTO KATEGORIE(KATEGORIENR, KATEGORIENAME) VALUES (5, 'LOWCARB');

INSERT INTO REZEPT_KATEGORIE(REZEPTNR, KATEGORIENR) VALUES (6300, 1);

INSERT INTO REZEPT_KATEGORIE(REZEPTNR, KATEGORIENR) VALUES (6300, 2);

INSERT INTO REZEPT_KATEGORIE(REZEPTNR, KATEGORIENR) VALUES (6300, 5);

INSERT INTO REZEPT_KATEGORIE(REZEPTNR, KATEGORIENR) VALUES (6400, 1);

INSERT INTO REZEPT_KATEGORIE(REZEPTNR, KATEGORIENR) VALUES (6400, 4);

INSERT INTO REZEPT_KATEGORIE(REZEPTNR, KATEGORIENR) VALUES (6500, 5);

INSERT INTO BESCHRAENKUNG(BESCHRAENKUNGSNR, BESCHRAENKUNGSNAME) VALUES (1, 'LAKTOSE');

INSERT INTO BESCHRAENKUNG(BESCHRAENKUNGSNR, BESCHRAENKUNGSNAME) VALUES (2, 'GLUTEN');

INSERT INTO BESCHRAENKUNG(BESCHRAENKUNGSNR, BESCHRAENKUNGSNAME) VALUES (3, 'NUESSE');

INSERT INTO BESCHRAENKUNG(BESCHRAENKUNGSNR, BESCHRAENKUNGSNAME) VALUES (4, 'EI');

INSERT INTO BESCHRAENKUNG(BESCHRAENKUNGSNR, BESCHRAENKUNGSNAME) VALUES (5, 'TOMATE');

INSERT INTO REZEPT_BESCHRAENKUNG(REZEPTNR, BESCHRAENKUNGSNR) VALUES (6400, 1);

INSERT INTO REZEPT_BESCHRAENKUNG(REZEPTNR, BESCHRAENKUNGSNR) VALUES (6400, 3);

INSERT INTO REZEPT_BESCHRAENKUNG(REZEPTNR, BESCHRAENKUNGSNR) VALUES (6400, 4);

INSERT INTO REZEPT_BESCHRAENKUNG(REZEPTNR, BESCHRAENKUNGSNR) VALUES (6500, 1);

INSERT INTO REZEPT_BESCHRAENKUNG(REZEPTNR, BESCHRAENKUNGSNR) VALUES (6500, 4);

INSERT INTO BESTELLUNG (KUNDENNR, BESTELLDATUM, RECHNUNGSBETRAG) VALUES (2001, '2020-07-01', 6.21);

INSERT INTO BESTELLUNG (KUNDENNR, BESTELLDATUM, RECHNUNGSBETRAG) VALUES (2002, '2020-07-08', 32.96);

INSERT INTO BESTELLUNG (KUNDENNR, BESTELLDATUM, RECHNUNGSBETRAG) VALUES (2003, '2020-08-01',24.08);

INSERT INTO BESTELLUNG (KUNDENNR, BESTELLDATUM, RECHNUNGSBETRAG) VALUES (2001, '2020-08-01', 5.50);

INSERT INTO BESTELLUNG (KUNDENNR, BESTELLDATUM, RECHNUNGSBETRAG) VALUES (2001, '2020-08-02', 11.00);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (1, 6400, 3);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (1, 6300, 2);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (1, 6500, 3);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (2, 6300, 1);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (2, 6500, 1);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (3, 6500, 1);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (4, 6500, 1);

INSERT INTO BESTELLUNG_REZEPT(BESTELLNR, REZEPTNR, MENGE) VALUES (5, 6500, 2);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6300, 1001, 5);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6300, 1002, 3);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6300, 1006, 2);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6300, 1004, 3);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6400, 6300, 1);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6400, 3003, 3);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6400, 4001, 2);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6500, 4001, 3);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6500, 3003, 3);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6500, 7043, 2);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6500, 3001, 3);

INSERT INTO REZEPT_ZUTAT(REZEPTNR, ZUTATENNR, MENGE) VALUES (6500, 6666, 5);
 */