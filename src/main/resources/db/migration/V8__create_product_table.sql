CREATE TABLE  IF NOT EXISTS storedb.products (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	"name" varchar NULL,
	price numeric NULL,
	active bool NULL
);