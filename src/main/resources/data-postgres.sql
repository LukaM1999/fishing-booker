insert into cottage(id, name, country, city, address, promo_description, capacity, rules, pricing, free_cancellation, rooms, beds_per_room)
    values (nextval('rentable_id_seq'), 'VikendicaPrvenstvoSrbije', 'Serbia', 'Petrovaradin', 'Strazilovska 18', 'Mnogo jaka!', 8, 'Pravila nema', 'Puno kosta', true, 2, 2);

insert into role(id, role_name) values (nextval('role_id_seq'), 'COTTAGE_OWNER');
insert into registered_user(username, password, name, surname, email, address, city, country, phone, role_id, enabled) values ('Piwneuh', 'jabuka123', 'Filip', 'Pinjuh', 'pecurka@gmail.com', 'Strazilovska 27', 'Novi Sad', 'Serbia', '333555', 1, true);
insert into cottage_owner(username, letter_of_intent, is_activated) values ('Piwneuh','Nesto', true);

