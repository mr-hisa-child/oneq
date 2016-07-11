
/* Drop Tables */

DROP TABLE IF EXISTS answers_choices;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS choices;
DROP TABLE IF EXISTS questions;




/* Create Tables */

CREATE TABLE answers
(
	id serial NOT NULL,
	name varchar(20) NOT NULL,
	answer_date timestamp NOT NULL,
	questions_id int NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE answers_choices
(
	choices_id int NOT NULL,
	answers_id int NOT NULL
) WITHOUT OIDS;


CREATE TABLE choices
(
	id serial NOT NULL,
	content varchar(1000) NOT NULL,
	questions_id int NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE questions
(
	id serial NOT NULL,
	pass varchar(8),
	path varchar(20) NOT NULL,
	deadline date,
	content text NOT NULL,
	create_date timestamp NOT NULL,
	create_user varchar(20),
	admin_pass varchar(20) NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE answers_choices
	ADD FOREIGN KEY (answers_id)
	REFERENCES answers (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE answers_choices
	ADD FOREIGN KEY (choices_id)
	REFERENCES choices (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE answers
	ADD FOREIGN KEY (questions_id)
	REFERENCES questions (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE choices
	ADD FOREIGN KEY (questions_id)
	REFERENCES questions (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



