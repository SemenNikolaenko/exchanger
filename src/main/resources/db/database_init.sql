create table accounts
(
    id       serial primary key not null,
    username varchar,
    password varchar,
    balance  double precision
);
create table orders
(
    id              serial not null primary key,
    from_currency   varchar,
    to_currency     varchar,
    amount          double precision,
    exchange_rate   double precision,
    exchange_action varchar,
    account_id      int,
    foreign key (account_id) references accounts(id)
);
