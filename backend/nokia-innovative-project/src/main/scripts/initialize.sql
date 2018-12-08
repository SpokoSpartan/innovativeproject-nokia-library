INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png'
    , '2018-02-02'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '56756756784'
    , 'The World of Abstract Art');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://www.creativindie.com/wp-content/uploads/2013/10/Enchantment-Book-Cover-Best-Seller1.jpg'
    , '1999-01-02'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '12312312314'
    , 'Enchantment');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://cdn-images-1.medium.com/max/1200/1*cNAPNAIDIHH0G-toMJhjxg.png'
    , '1996-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '456347569012'
    , 'Learning The vi');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://i.pinimg.com/236x/ee/15/e2/ee15e271f8e2467560ecf0d12ffb8ca5--o-reilly-programming.jpg'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '23423423411'
    , 'Banishing The Microsoft');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://i.pinimg.com/474x/f2/39/34/f239346d5830f9382eee8400e990959c--software-development-computer-science.jpg'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '76576565444'
    , 'Useless Git Commit Messages');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://i.pinimg.com/564x/70/bd/5f/70bd5f632826d5d0c6ee035e6ce97ddf.jpg'
    , '2010-02-02'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '76576576543'
    , 'Solving Imaginary Scaling Issues');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://d3ansictanv2wj.cloudfront.net/expect-2-d45f737c6b69f2c3ff53c765456ca541.jpg'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '76576576576'
    , 'Exploring Expect');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://bit.ly/2DJuhzi'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '23523523525'
    , 'The Lives Inside Your Head');

INSERT INTO book_category (book_category_name) VALUES ('Guide');
INSERT INTO book_category (book_category_name) VALUES ('Programming');
INSERT INTO book_category (book_category_name) VALUES ('Novel');

INSERT INTO book_details_categories VALUES (1, 1);
INSERT INTO book_details_categories VALUES (2, 3);
INSERT INTO book_details_categories VALUES (3, 2);
INSERT INTO book_details_categories VALUES (3, 1);
INSERT INTO book_details_categories VALUES (4, 2);
INSERT INTO book_details_categories VALUES (4, 1);
INSERT INTO book_details_categories VALUES (5, 2);
INSERT INTO book_details_categories VALUES (5, 1);
INSERT INTO book_details_categories VALUES (8, 2);
INSERT INTO book_details_categories VALUES (8, 1);

INSERT INTO author (author_full_name) VALUES ('Edgar Jander');
INSERT INTO author (author_full_name) VALUES ('Lucas Newell');
INSERT INTO author (author_full_name) VALUES ('Robbie Torok');
INSERT INTO author (author_full_name) VALUES ('Raymond Kertis');
INSERT INTO author (author_full_name) VALUES ('Emmet Brenig');

INSERT INTO book_details_authors VALUES (1, 1);
INSERT INTO book_details_authors VALUES (2, 1);
INSERT INTO book_details_authors VALUES (3, 1);
INSERT INTO book_details_authors VALUES (4, 2);
INSERT INTO book_details_authors VALUES (5, 2);
INSERT INTO book_details_authors VALUES (6, 3);
INSERT INTO book_details_authors VALUES (7, 4);
INSERT INTO book_details_authors VALUES (7, 5);

INSERT INTO "user" (id, email, first_name, last_name) VALUES (1, 'wojtekspoton@gmail.com', 'Wojtek', 'Wojtek');
INSERT INTO "role" (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO "role" (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO "user_roles" (user_id, roles_id) VALUES (1, 1);

