--Eliminate tables if, for some reason, they exist
DROP TABLE IF EXISTS 'CODES';
DROP TABLE IF EXISTS 'PRECIPITATION';
DROP TABLE IF EXISTS 'SNOW';
DROP TABLE IF EXISTS 'SKY';
DROP TABLE IF EXISTS 'WIND';
DROP TABLE IF EXISTS 'TEMPERATURE';
DROP TABLE IF EXISTS 'HUMIDITY';

--Create tables with their restrictions
CREATE TABLE IF NOT EXISTS 'CODES' (
  'Cod_mun' varchar(5) NOT NULL,
  'Provincia' varchar(30),
  'Nombre' varchar(30) NOT NULL,
  PRIMARY KEY ('Cod_mun')
) ;

CREATE TABLE IF NOT EXISTS 'PRECIPITATION' (
  'Cod_mun' varchar(5) NOT NULL,
  'Fecha' varchar(30) NOT NULL,
  'Valor' varchar(30),
  PRIMARY KEY ('Cod_mun','Fecha'),
  FOREIGN KEY ('Cod_mun') REFERENCES CODES('Cod_mun')
) ;

CREATE TABLE IF NOT EXISTS 'SNOW' (
  'Cod_mun' varchar(5) NOT NULL,
  'Fecha' varchar(30) NOT NULL,
  'Valor' varchar(30),
  PRIMARY KEY ('Cod_mun','Fecha'),
  FOREIGN KEY ('Cod_mun') REFERENCES CODES('Cod_mun')
) ;

CREATE TABLE IF NOT EXISTS 'SKY' (
  'Cod_mun' varchar(5) NOT NULL,
  'Fecha' varchar(30) NOT NULL,
  'Valor' varchar(30),
  'Descripcion' varchar(50),
  'UVMax' varchar(2),
  PRIMARY KEY ('Cod_mun','Fecha'),
  FOREIGN KEY ('Cod_mun') REFERENCES CODES('Cod_mun')
) ;

CREATE TABLE IF NOT EXISTS 'WIND' (
  'Cod_mun' varchar(5) NOT NULL,
  'Fecha' varchar(30) NOT NULL,
  'Direccion' varchar(30),
  'Velocidad' varchar(50),
  'RachaMax' varchar(2),
  PRIMARY KEY ('Cod_mun','Fecha'),
  FOREIGN KEY ('Cod_mun') REFERENCES CODES('Cod_mun')
) ;

CREATE TABLE IF NOT EXISTS 'TEMPERATURE' (
  'Cod_mun' varchar(5) NOT NULL,
  'Fecha' varchar(30) NOT NULL,
  'T_max' varchar(30),
  'T_min' varchar(50),
  'T_max_rel' varchar(2),
  'T_min_rel' varchar(2),
  PRIMARY KEY ('Cod_mun','Fecha'),
  FOREIGN KEY ('Cod_mun') REFERENCES CODES('Cod_mun')
) ;

CREATE TABLE IF NOT EXISTS 'HUMIDITY' (
  'Cod_mun' varchar(5) NOT NULL,
  'Fecha' varchar(30) NOT NULL,
  'Max' varchar(30),
  'Min' varchar(50),
  PRIMARY KEY ('Cod_mun','Fecha'),
  FOREIGN KEY ('Cod_mun') REFERENCES CODES('Cod_mun')
) ;

--Create views

CREATE VIEW 'HUMEDAD' AS 
SELECT C.Provincia as 'PROVINCIA', C.Nombre as 'MUNICIPIO', H.Fecha as 'FECHA', H.Max as 'HUMEDAD_MAXIMA', H.Min as 'HUMEDAD_MINIMA' 
FROM HUMIDITY H 
JOIN CODES C ON C.Cod_mun=H.Cod_mun;

CREATE VIEW 'TEMPERATURA' AS 
SELECT C.Provincia as 'PROVINCIA', C.Nombre as 'MUNICIPIO', T.Fecha as 'FECHA', T.T_max as 'TA_MAXIMA', T.T_min as 'TA_MINIMA', T.T_max_rel as 'SENSACION_TERMICA_MAXIMA', T.T_min_rel as 'SENSACION_TERMICA_MINIMA'
FROM TEMPERATURE T 
JOIN CODES C on C.Cod_mun=T.Cod_mun;

CREATE VIEW 'VIENTO' AS 
SELECT C.Provincia as 'PROVINCIA', C.Nombre as 'MUNICIPIO', W.Fecha as 'FECHA', W.Direccion as 'DIRECCION', W.Velocidad as 'VELOCIDAD', W.RachaMax as 'RACHA_MAXIMA' 
FROM WIND W 
JOIN CODES C on C.Cod_mun=W.Cod_mun;