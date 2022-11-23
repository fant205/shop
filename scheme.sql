create table products (
	id serial primary key,
	title text not null,
	cost int not null
)

create table users (
	id serial primary key,
	name text not null
);

create table users_products(
	user_id int references users(id),
	product_id int references products(id)
)

