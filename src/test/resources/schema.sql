drop table if exists account;
CREATE TABLE account (acct_id integer AUTO_INCREMENT, amt double NOT NULL, created_at timestamp NOT NULL, updated_at timestamp NOT NULL);
insert into account (acct_id, amt, created_at, updated_at) values (1, 5000, now(), now());
insert into account (acct_id, amt, created_at, updated_at) values (2, 15000, now(), now());