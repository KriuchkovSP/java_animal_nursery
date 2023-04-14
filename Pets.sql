/* 7. В подключенном MySQL репозитории создать базу данных “Друзья
человека” */
DROP DATABASE IF EXISTS friends;
CREATE DATABASE friends;

/* 8. Создать таблицы с иерархией из диаграммы в БД
	Указанная в диаграмме иерархия не укладывается в каноны проектирования баз данных.
	Не смог придумать такую базу, чтобы не было кучи лишних таблиц и foreign key.
	Поэтому сделал как это было бы логичнее. */
USE friends;

DROP TABLE IF EXISTS `types`;
CREATE TABLE types
(
	id BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
	type varchar(20) NOT NULL
);

DROP TABLE IF EXISTS `subspecies`;
CREATE TABLE subspecies
(
	id BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
	species varchar(20) NOT NULL,
	types_id BIGINT UNSIGNED NULL,
	CONSTRAINT FK_types_types_id FOREIGN KEY (types_id) REFERENCES types(id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS `animals`;
CREATE TABLE animals
(
	id BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
	name varchar(50) NOT NULL,
	dateofbirth DATE,
	subspecies_animals_id BIGINT UNSIGNED NULL,
	CONSTRAINT FK_subspecies_subspecies_animals_id FOREIGN KEY (subspecies_animals_id) REFERENCES subspecies(id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS `commands`;
CREATE TABLE commands
(
	id BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
	command varchar(20) NOT NULL
);

DROP TABLE IF EXISTS `anim_comm`;
CREATE TABLE anim_comm
(
	anim_id BIGINT UNSIGNED NOT NULL,
	comm_id BIGINT UNSIGNED,
	PRIMARY KEY (`anim_id`, `comm_id`),
	CONSTRAINT FK_animals_anim_id FOREIGN KEY (anim_id) REFERENCES animals(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT FK_animals_comm_id FOREIGN KEY (comm_id) REFERENCES commands(id) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 9. Заполнить низкоуровневые таблицы именами(животных), командами
которые они выполняют и датами рождения */

INSERT INTO commands (command) VALUES
	 ('GO'),
	 ('STOP'),
	 ('RUN_AWAY'),
	 ('COME_UP'),
	 ('LIE'),
	 ('JUMP'),
	 ('TRUP'),
	 ('STAND_UP');

INSERT INTO types (`type`) VALUES
	 ('Домашние животные'),
	 ('Вьючные животные');

INSERT INTO subspecies (species,types_id) VALUES
	 ('Кошка',1),
	 ('Собака',1),
	 ('Хомяк',1),
	 ('Лошадь',2),
	 ('Верблюд',2),
	 ('Осел',2);

INSERT INTO animals (name,dateofbirth,subspecies_animals_id) VALUES
	 ('Хельга','2023-03-04',1),
	 ('Дружок','2021-05-20',2),
	 ('Глюк','2022-07-29',3),
	 ('Рысак','2019-09-20',4),
	 ('Ланцелот','2018-11-24',5),
	 ('Иа','2021-10-15',6);

INSERT INTO anim_comm (anim_id,comm_id) VALUES
	 (4,1),
	 (5,1),
	 (6,1),
	 (4,2),
	 (5,2),
	 (6,2),
	 (4,3),
	 (4,4),
	 (5,4),
	 (6,4),
	 (2,5),
	 (2,6),
	 (2,7),
	 (2,8);

	
SELECT name,
	dateofbirth,
	t.type,
	s.species,
	c.command
FROM animals as a
JOIN subspecies as s on s.id = a.subspecies_animals_id 
JOIN types as t on t.id = s.types_id 
LEFT JOIN anim_comm as ac on ac.anim_id = a.id 
LEFT JOIN commands as c on c.id = ac.comm_id;

/* 10. Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой
питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу. */
		
/* Удаление. Не желательный вариант... А если верблюдов вернут, то их в базу надо будет заново заводить:
У меня изначально все животные в одной таблице, потому достаточно только удалить запись с верблюдом*/
DELETE FROM subspecies WHERE species = "Верблюд";
-- Предпочтительный вариант промаркировать их и скрыть из вывода. Можно результирующую временную таблицу создать...
ALTER TABLE animals 
ADD COLUMN is_deleted BIT DEFAULT 0;

UPDATE animals
SET is_deleted = 1
WHERE subspecies_animals_id = 5;

SELECT name,
	dateofbirth,
	t.type,
	s.species,
	c.command
FROM animals as a
JOIN subspecies as s on s.id = a.subspecies_animals_id 
JOIN types as t on t.id = s.types_id 
JOIN anim_comm as ac on ac.anim_id = a.id 
JOIN commands as c on c.id = ac.comm_id
WHERE NOT a.is_deleted;

/* 11. Создать новую таблицу “молодые животные” в которую попадут все
животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью
до месяца подсчитать возраст животных в новой таблице */

/* Столбец с возрастом выводится при запросе, чтобы актуальная информация о текущем возрасте
 формировалась в момент вывода, а не на момент создания таблицы. Таблицу создал временную. */ 
DROP TABLE IF EXISTS young_animals;
CREATE TEMPORARY TABLE young_animals
	SELECT 
		a.name,
		a.dateofbirth,
		t.type,
		s.species
	FROM animals as a 
	JOIN subspecies as s on s.id = a.subspecies_animals_id 
	JOIN types as t on t.id = s.types_id
	WHERE NOT a.is_deleted AND TIMESTAMPDIFF(YEAR, a.dateofbirth, CURDATE()) >= 1 AND TIMESTAMPDIFF(YEAR, a.dateofbirth, CURDATE()) < 3;
SELECT * FROM young animals;

SELECT name,
	dateofbirth,
	type,
	species,
	DATE_FORMAT(
    	FROM_DAYS(
        	DATEDIFF(CURRENT_DATE,  dateofbirth)
        ),
	    '%y year %m months'
    ) AS age
 FROM young animals;

/* 12. Объединить все таблицы в одну, при этом сохраняя поля, указывающие на
прошлую принадлежность к старым таблицам. */

/* Создал также временную таблицу. В выборку не включил уехавших верблюдов.
 * Поскольку полей id несколько, пришлось их переименовывать при объединении,
 * т.к. требовалось сохранять поля, указывающие на прошлую принадлежность к старым таблицам.
 */
DROP TABLE IF EXISTS all_in_one;
CREATE TEMPORARY TABLE all_in_one
	SELECT
		a.id as aid, a.name, a.dateofbirth, a.subspecies_animals_id,
		s.id as sid, s.species, s.types_id,
		t.id as tid, t.`type`,
		ac.anim_id, ac.comm_id,
		c.id as cid, c.command
	FROM animals as a
	JOIN subspecies as s on s.id = a.subspecies_animals_id 
	JOIN types as t on t.id = s.types_id 
	LEFT JOIN anim_comm as ac on ac.anim_id = a.id 
	LEFT JOIN commands as c on c.id = ac.comm_id
	WHERE NOT a.is_deleted;

SELECT * FROM all_in_one;

	