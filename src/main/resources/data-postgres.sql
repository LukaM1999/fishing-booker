insert into role(id, role_name) values (nextval('role_id_seq'), 'COTTAGE_OWNER');                                                          --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Piwneuh', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Filip', 'Pinjuh', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 1, true);
insert into cottage_owner(username, letter_of_intent) values ('Piwneuh','Nesto');

insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'VikendicaPrvenstvoSrbije', 'Serbia', 'Petrovaradin', 'Strazilovska 18', 'Mnogo jaka!', 8, 'Pravila nema', 'Puno kosta', 0, 2, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room, owner_username)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', 0, 3, 2, 'Piwneuh');
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', '20 euros/day', 0, 3, 2);

insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');

insert into role(id, role_name) values (nextval('role_id_seq'), 'CUSTOMER');                                                               --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('imbiamba', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Luka', 'Miletic', 'imbiamba@gmail.com', 'Kraljevica Marka 21', 'Novi Sad', 'Serbia', '4443332', 2, true);
insert into customer(username, verification_token) values ('imbiamba',null);

insert into role(id, role_name) values (nextval('role_id_seq'), 'ADMIN');                                                          --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('mkisic', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Mihajlo', 'Kisic', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 3, true);
insert into admin(username) values ('mkisic');

insert into role(id, role_name) values (nextval('role_id_seq'), 'BOAT_OWNER');                                                             --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Popaye', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Filip', 'Pinjuh', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 4, true);
insert into boat_owner(username, letter_of_intent) values ('Popaye','Nesto');

insert into boat(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment, boat_owner_username)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', '50 euros/h', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment', 'Popaye');


insert into role(id, role_name) values (nextval('role_id_seq'), 'INSTRUCTOR');                                                              --fishes123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Fishdude', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Fish', 'Dude', 'fishdude@gmail.com', 'Bulevar oslobodjenja 22', 'Novi Sad', 'Serbia', '453678', 5, true);
insert into instructor(username, letter_of_intent) values ('Fishdude','Best instructor');
insert into adventure(id, name, country, city, address, promo_description, capacity, rules, pricing, cancellation_fee, instructor_bio, fishing_equipment, instructor_username)
values (nextval('rentable_id_seq'), 'Silver Mirror Tour', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 4, 'Be polite.', '50 euros per person', 0, 'Ambitious and carefree adventurer', 'Fishing lines, hooks, rigs, bait', 'Fishdude');

insert into free_term(id, type, entity_name, owner_username, start_time, end_time) values (nextVal('free_term_id_seq'), 'COTTAGE', 'Silver Mirror', 'Piwneuh', '2022-01-07', '2022-01-09');
insert into free_term(id, type, entity_name, owner_username, start_time, end_time) values (nextVal('free_term_id_seq'), 'BOAT', '380 Realm Boston Whaler', 'Popaye', '2022-01-07', '2022-01-09');

insert into reservation(id, type, name, owner_username, customer_username, start_time, end_time, additional_services, is_cancelled, is_deal, price, rating, complaint_exists)
values (1, 'COTTAGE', 'Silver Mirror', 'Piwneuh', 'imbiamba', '2022-01-08', '2022-01-21', 'Some services', false, false, 10000, 0, false);
