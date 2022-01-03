insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'VikendicaPrvenstvoSrbije', 'Serbia', 'Petrovaradin', 'Strazilovska 18', 'Mnogo jaka!', 8, 'Pravila nema', 'Puno kosta', true, 2, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', true, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', true, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', true, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', true, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', true, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', true, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', true, 3, 2);

insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fishfinder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', false, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fishfinder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', false, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fishfinder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', false, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fishfinder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', false, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fishfinder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', false, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');


insert into role(id, role_name) values (nextval('role_id_seq'), 'COTTAGE_OWNER');                                                          --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Piwneuh', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Filip', 'Pinjuh', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 1, true);
insert into cottage_owner(username, letter_of_intent, is_activated) values ('Piwneuh','Nesto', true);

insert into role(id, role_name) values (nextval('role_id_seq'), 'INSTRUCTOR');                                                              --fishes123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Fishdude', '$2a$10$AD6RIT6vlESwmIacDXE0O.PeuUTA5J5hyKa/VAC1jYHpw.ubuRusu', 'Fish', 'Dude', 'fishdude@gmail.com', 'Bulevar oslobodjenja 22', 'Novi Sad', 'Serbia', '453678', 2, true);
insert into instructor(username, letter_of_intent, is_activated) values ('Fishdude','Best instructor', true);
insert into adventure(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, instructor_bio, fishing_equipment, instructor_username)
values (nextval('rentable_id_seq'), 'Silver Mirror Tour', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 4, 'Be polite.', '50 euros per person', true, 'Ambitious and carefree adventurer', 'Fishing lines, hooks, rigs, bait', 'Fishdude');

