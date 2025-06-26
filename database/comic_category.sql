CREATE TABLE comic_category(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    comic_id INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_comic_category_comics FOREIGN KEY(comic_id) REFERENCES comics(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_comic_category_categories FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE
);