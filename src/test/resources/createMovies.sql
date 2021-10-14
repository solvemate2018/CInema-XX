DELETE FROM movie where id > 0;
INSERT INTO movie (id,duration,name,category_id,genre_id) values (100,100,'bbb',null,null);
INSERT INTO movie (id,duration,name,category_id,genre_id) values (101,100,'bbb2',null,null);
INSERT INTO movie (id,duration,name,category_id,genre_id) values (102,200,'bbb3',null,null);

INSERT INTO genre (name) values ('horror');

INSERT INTO projection (id,start_time,ticket_price,hall_id,movie_id) values (100,CURRENT_TIMESTAMP,20,null,null);
INSERT INTO projection (id,start_time,ticket_price,hall_id,movie_id) values (101,CURRENT_TIMESTAMP,30,null,null);
INSERT INTO projection (id,start_time,ticket_price,hall_id,movie_id) values (102,CURRENT_TIMESTAMP,40,null,null);


