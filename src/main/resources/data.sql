ALTER TABLE trips
    ALTER COLUMN photo_url TYPE TEXT;

ALTER TABLE trips
    ALTER COLUMN description TYPE TEXT;

ALTER TABLE event
    ALTER COLUMN photo_url TYPE TEXT;

ALTER TABLE event
    ALTER COLUMN description TYPE TEXT;

INSERT INTO roles (name)
SELECT 'admin' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'admin');

INSERT INTO roles (name)
SELECT 'user' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'user');

INSERT INTO users (login, email, password)
SELECT 'admin', 'admin@admin.com', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'admin');

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'admin'
WHERE u.login = 'admin'
  AND NOT EXISTS (
    SELECT 1 FROM user_roles ur
    WHERE ur.user_id = u.id
      AND ur.role_id = r.id
);

INSERT INTO trips (title, photo_url, description, creation_time, update_time)
SELECT 'Expedition to Rysy Peak', 'https://marcinkarpinski.pl/img/relacja/kgp-rysy/1200-kgp-rysy-000.webp', 'Embark on an epic expedition to Rysy Peak, the crown jewel of the Tatra Mountains and the highest point in Poland. This guided tour promises an unforgettable adventure for outdoor enthusiasts seeking a challenge and breathtaking scenery. Prepare to be captivated by the rugged beauty of the Tatra landscape as you ascend towards the summit.', '2024-03-26 10:00:00', '2024-03-26 10:00:00'
WHERE NOT EXISTS (SELECT 1 FROM trips WHERE title = 'Expedition to Rysy Peak');

INSERT INTO trips (title, photo_url, description, creation_time, update_time)
SELECT 'Trekking Adventure to Giewont Peak', 'https://westerncamp.pl/wp-content/uploads/2023/10/giewont-scaled.jpg', 'Embark on an exhilarating trekking adventure to Giewont Peak, one of the most iconic mountains in the Tatra Mountains range of Poland. This guided tour offers an unforgettable experience for outdoor enthusiasts and nature lovers alike. As you ascend the rugged trails, you will be treated to breathtaking panoramic views of the surrounding valleys and peaks. Marvel at the pristine beauty of the alpine landscapes, dotted with lush forests, alpine meadows, and cascading streams.  Led by experienced guides, you will follow well-marked paths that wind their way through the picturesque terrain.', '2024-03-26 10:00:00', '2024-03-26 10:00:00'
WHERE NOT EXISTS (SELECT 1 FROM trips WHERE title = 'Trekking Adventure to Giewont Peak');

INSERT INTO event (name, start_time, end_time, photo_url, create_date, update_date, trip_id, description)
SELECT 'Morskie Oko', '2024-03-26 06:00:00', '2024-03-26 16:00:00', 'https://www.goral.pl/galeria/morskie-oko-szlak.png', '2024-03-26 09:00:00', '2024-03-26 09:00:00', 1, 'Morskie Oko, or the Eye of the Sea, is a stunning alpine lake nestled in the heart of the Tatra Mountains in Poland. Surrounded by towering peaks and lush forests, its crystal-clear waters reflect the breathtaking beauty of the surrounding landscape. Accessible via a scenic hike through picturesque trails, Morskie Oko enchants visitors with its serene ambiance and pristine natural setting.'
WHERE NOT EXISTS (SELECT 1 FROM event WHERE name = 'Morskie Oko');

INSERT INTO event (name, start_time, end_time, photo_url, create_date, update_date, trip_id, description)
SELECT 'Discover the Natural Splendor of Polana Strążyska', '2024-03-26 08:00:00', '2024-03-26 18:00:00', 'https://trasynawczasy.pl/659-large_default/dolina-strazyska-i-wodospad-siklawica.jpg', '2024-03-26 09:00:00', '2024-03-26 09:00:00', 2, 'Embark on a picturesque journey through the stunning Polana Strążyska, a hidden gem nestled within the Tatra Mountains of Poland. This guided tour offers an enchanting escape into the heart of nature, where lush greenery, babbling streams, and towering peaks create a scene straight out of a postcard.  As you traverse the well-marked trails of Polana Strążyska, immerse yourself in the tranquility of the surrounding forest. Listen to the soothing sounds of birds chirping overhead and the gentle rustle of leaves in the breeze.'
WHERE NOT EXISTS (SELECT 1 FROM event WHERE name = 'Discover the Natural Splendor of Polana Strążyska');
