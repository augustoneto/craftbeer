DROP TABLE beer IF EXISTS;

CREATE TABLE beer (
  id         		INTEGER IDENTITY PRIMARY KEY,
  name			VARCHAR(100),
  ingredients   	VARCHAR(100),
  alcohol_content	VARCHAR(100),
  price			DECIMAL(20, 2),
  category		VARCHAR(100)
);