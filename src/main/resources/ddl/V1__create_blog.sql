CREATE TABLE IF NOT EXISTS `blog` (
  `id`          INT UNSIGNED AUTO_INCREMENT,
  `title`       VARCHAR(300)   NOT NULL,
  `content`     VARCHAR(10000) NOT NULL,
  `author`      VARCHAR(40)    NOT NULL,
  `create_time` DATETIME,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO blog (id, title, content, author, create_time) VALUES
  (1, 'first blog', 'Hello, World, This is my first blog', 'koly', NOW());
INSERT INTO blog (id, title, content, author, create_time) VALUES
  (2, 'second blog', 'Today is a beautiful day, and the sunshine is fine.', 'koly', NOW());