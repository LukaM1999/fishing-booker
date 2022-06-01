insert into role(id, role_name) values (nextval('role_id_seq'), 'COTTAGE_OWNER');                                                          --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Piwneuh', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Filip', 'Pinjuh', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 1, true);
insert into cottage_owner(username, letter_of_intent) values ('Piwneuh','Nesto');

insert into role(id, role_name) values (nextval('role_id_seq'), 'CUSTOMER');                                                               --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('imbiamba', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Luka', 'Miletic', 'imbiamba@gmail.com', 'Kraljevica Marka 21', 'Novi Sad', 'Serbia', '4443332', 2, true);
insert into customer(username, verification_token) values ('imbiamba',null);

insert into role(id, role_name) values (nextval('role_id_seq'), 'ADMIN');                                                                 --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('mkisic', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Mihajlo', 'Kisic', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 3, true);
insert into admin(username) values ('mkisic');

insert into role(id, role_name) values (nextval('role_id_seq'), 'BOAT_OWNER');                                                             --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Popaye', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Filip', 'Pinjuh', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 4, true);
insert into boat_owner(username, letter_of_intent) values ('Popaye','Nesto');

insert into boat(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment, owner_username)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', 50, 'wifi=15;', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment', 'Popaye');

insert into role(id, role_name) values (nextval('role_id_seq'), 'INSTRUCTOR');                                                              --jabuka123
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Fishdude', '$2a$10$UVn74F/yEiUzKWBSGVyzHe2UfpVJ95zY50Q8bz1RFyrAYVfwFAj4i', 'Fish', 'Dude', 'fishdude@gmail.com', 'Bulevar oslobodjenja 22', 'Novi Sad', 'Serbia', '453678', 5, true);
insert into instructor(username, letter_of_intent) values ('Fishdude','Best instructor');

insert into adventure(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, instructor_bio, fishing_equipment, owner_username, images)
values (nextval('rentable_id_seq'), 'Silver Mirror Tour', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 4, 'Be polite.', 50, 'snacks=15;', 0, 'Ambitious and carefree adventurer', 'Fishing lines, hooks, rigs, bait', 'Fishdude', 'a2.1.jpg;a2.2.jpg');

insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room, owner_username, images)
values (nextval('rentable_id_seq'), 'Prvenstvo Srbije', 'Serbia', 'Petrovaradin', 'Strazilovska 18', 'Mnogo jaka!', 8, 'Pravila nema', 20, 'wifi=5;chocolate=3', 0, 2, 2, 'Piwneuh', 'c3.1.jpg;c3.2.jpg;c3.3.jpg;');
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room, owner_username, images)
values (nextval('rentable_id_seq'), 'Silver Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', 30, 'wifi=5;chocolate=3', 0, 3, 2, 'Piwneuh', 'c4.1.jpg;c4.2.jpg;c4.3.jpg;');
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room, owner_username, images)
values (nextval('rentable_id_seq'), 'Black Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', 40, 'wifi=5;aircool=3', 0, 3, 2, 'Piwneuh', 'c5.1.jpg;c5.2.jpg;c5.3.jpg;');
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room, owner_username, images)
values (nextval('rentable_id_seq'), 'Gold Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', 20, 'wifi=5;', 0, 3, 2, 'Piwneuh', 'c6.1.jpg;c6.2.jpg;c6.3.jpg;');
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Ruby Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', 30, 'wifi=5;', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Platinium Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', 30, 'wifi=5;', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Obsidian Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', 20, 'wifi=5;', 0, 3, 2);
insert into cottage(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, rooms, beds_per_room)
values (nextval('rentable_id_seq'), 'Moon Mirror', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'See your reflection', 8, 'Be polite.', 50, 'wifi=5;', 0, 3, 2);

insert into adventure(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, instructor_bio, fishing_equipment, owner_username, images)
values (nextval('rentable_id_seq'), 'Tikvara - catfish fishing', 'Serbia', 'Srebrno Jezero', 'Random ulica', 'Lets find dark place on Tikvaras beach', 1, 'Only women', 50, 'snacks=15;', 0, 'I have 70 hobbies', 'A big hook', 'Fishdude', 'a11.1.jpg;a11.2.jpg');

insert into boat(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', 30, 'boots=10;', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', 40, 'boots=10;', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', 50, 'boots=10;', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', 60, 'boots=10;', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');
insert into boat(id, name, country, city, address, promo_description, capacity, rules, price, additional_services, cancellation_fee, boat_type, length, motors, power, max_speed, gps, radar, vhf_radio, fish_finder, fishing_equipment)
values (nextval('rentable_id_seq'), '380 Realm Boston Whaler', 'Serbia', 'Subotica', 'Suboticka 10', 'Best boat ever', 16, 'Be polite.', 70, 'boots=10;', 5, 'Boston Whaler', 11.89, 4, 1600, 250, true, true, false, true, 'Much equipment');

insert into free_term(id, type, entity_name, owner_username, start_time, end_time) values (nextval('free_term_id_seq'), 'COTTAGE', 'Silver Mirror', 'Piwneuh', '2022-04-07', '2022-04-27');
insert into free_term(id, type, entity_name, owner_username, start_time, end_time) values (nextval('free_term_id_seq'), 'BOAT', '380 Realm Boston Whaler', 'Popaye', '2022-04-07', '2022-04-09');
insert into free_term(id, type, entity_name, owner_username, start_time, end_time) values (nextval('free_term_id_seq'), 'ADVENTURE', 'Silver Mirror Tour', 'Fishdude', '2022-05-15', '2022-05-27');
insert into free_term(id, type, entity_name, owner_username, start_time, end_time) values (nextval('free_term_id_seq'), 'COTTAGE', 'Silver Mirror', 'Piwneuh', '2022-05-29', '2022-06-29');


insert into reservation(id, type, name, owner_username, customer_username, start_time, end_time, additional_services, guests, is_cancelled, is_deal, price, is_reviewed, complaint_exists, sale_percent)
values (nextval('reservation_id_seq'), 'COTTAGE', 'Silver Mirror', 'Piwneuh', 'imbiamba', '2022-01-08', '2022-01-21', 'Some services', 1, false, false, 10000, false, false, 0);

insert into reservation(id, type, name, owner_username, customer_username, start_time, end_time, additional_services, guests, is_cancelled, is_deal, price, is_reviewed, complaint_exists, sale_percent)
values (nextval('reservation_id_seq'), 'ADVENTURE', 'Silver Mirror Tour', 'Fishdude', 'imbiamba', '2022-05-08 08:00', '2022-05-08 09:00', 'Some services', 1, false, false, 10000, false, false, 0);

insert into reservation(id, type, name, owner_username, customer_username, start_time, end_time, additional_services, guests, is_cancelled, is_deal, price, is_reviewed, complaint_exists, sale_percent)
values (nextval('reservation_id_seq'), 'COTTAGE', 'Silver Mirror', 'Piwneuh', null, '2022-06-15', '2022-06-17', '', 1, false, true, 36, false, false, 10);

insert into penalty(id, customer_username, issued_at) values (nextval('penalty_id_seq'), 'imbiamba', '2022-04-08 10:00');
insert into penalty(id, customer_username, issued_at) values (nextval('penalty_id_seq'), 'imbiamba', '2022-04-15 15:00');
insert into penalty(id, customer_username, issued_at) values (nextval('penalty_id_seq'), 'imbiamba', '2022-05-08 12:00');
insert into penalty(id, customer_username, issued_at) values (nextval('penalty_id_seq'), 'imbiamba', '2022-05-20 17:00');

insert into points(id, customer_points, owner_points, system_tax, silver, gold) values (1, 15, 15, 7, 500, 800);
insert into user_points(username, points) values ('imbiamba', 0);
insert into user_points(username, points) values ('Piwneuh', 0);
insert into user_points(username, points) values ('Popeye', 0);
insert into user_points(username, points) values ('Fishdude', 0);


