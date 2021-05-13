-- DROP

-- orders in process

begin
    execute immediate 'drop table inprcss cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_inprcss';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- all orders

begin
    execute immediate 'drop table orders cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_orders';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- customers

begin
    execute immediate 'drop table cstmrs cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_cstmrs';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- users

begin
    execute immediate 'drop table users cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_users';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- roles

begin
    execute immediate 'drop table roles cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_roles';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- drugs' components

begin
    execute immediate 'drop table drgscmps cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_drgscmps';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- drugs

begin
    execute immediate 'drop table drugs cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_drugs';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- drug types

begin
    execute immediate 'drop table drgtypes cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_drgtypes';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- components

begin
    execute immediate 'drop table cmpnnts cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_cmpnnts';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

-- technologies

begin
    execute immediate 'drop table tchnlgs cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_tchnlgs';
exception
    when others then
        if sqlcode != -2289 then
            raise;
        end if;
end;

--
--
--

-- CREATE

-- technologies

create sequence s_tchnlgs start with 1 increment by 1 nomaxvalue

create table tchnlgs (
    id int,
    drug_name varchar2(50) not null,
    description varchar2(50) not null,
    primary key(id)
)

-- components

create sequence s_cmpnnts start with 1 increment by 1 nomaxvalue

create table cmpnnts (
    id int,
    name varchar2(50) not null,
    amount int not null,
    cost_per_gram float not null,
    primary key(id)
)

-- drug types

create sequence s_drgtypes start with 1 increment by 1 nomaxvalue

create table drgtypes (
    id int,
    name varchar2(50) not null,
    primary key(id)
)

-- drugs

create sequence s_drugs start with 1 increment by 1 nomaxvalue

create table drugs (
    id int,
    type_id int not null,
    technology_id int not null,
    price float not null,
    amount int not null,
    crit_norma int not null,
    primary key(id),
    foreign key (type_id) references drgtypes(id),
    foreign key (technology_id) references tchnlgs(id)
)

-- drugs' components

create sequence s_drgscmps start with 1 increment by 1 nomaxvalue

create table drgscmps (
    id int,
    drug_id int not null,
    component_id int not null,
    grams_of_component float not null,
    primary key(id),
    foreign key (drug_id) references drugs(id),
    foreign key (component_id) references cmpnnts(id)
)

-- roles

create sequence s_roles start with 1 increment by 1 nomaxvalue

create table roles (
    id int,
    name varchar2(50) not null,
    primary key(id)
)

-- users

create sequence s_users start with 1 increment by 1 nomaxvalue

create table users (
    id int,
    login varchar2(50) not null,
    role_id int not null,
    primary key(id),
    foreign key (role_id) references roles(id)
)

-- customers

create sequence s_cstmrs start with 1 increment by 1 nomaxvalue

create table cstmrs (
    id int,
    user_id int null,
    name varchar2(50) not null,
    phone_number varchar2(50) not null,
    address varchar2(50) not null,
    primary key(id),
    foreign key (user_id) references users(id)
)

-- all orders

create sequence s_orders start with 1 increment by 1 nomaxvalue

create table orders (
    id int,
    customer_id int not null,
    drug_id int not null,
    amount int not null,
    given int not null,
    primary key(id),
    foreign key (customer_id) references cstmrs(id),
    foreign key (drug_id) references drugs(id)
)

-- orders in process

create sequence s_inprcss start with 1 increment by 1 nomaxvalue

create table inprcss (
    id int,
    order_id int not null,
    ready_time timestamp not null,
    primary key(id),
    foreign key (order_id) references orders(id)
)

grant select, insert, update on inprcss to "18205_BELOGLAZOV_WORKER"

grant select, insert, update on orders to "18205_BELOGLAZOV_WORKER"

grant select, insert, update on cstmrs to "18205_BELOGLAZOV_WORKER"

grant select on users to "18205_BELOGLAZOV_WORKER"

grant select on roles to "18205_BELOGLAZOV_WORKER"

grant select, insert, update on drgscmps to "18205_BELOGLAZOV_WORKER"

grant select, insert, update on drugs to "18205_BELOGLAZOV_WORKER"

grant select, insert, update on drgtypes to "18205_BELOGLAZOV_WORKER"

grant select, insert, update on cmpnnts to "18205_BELOGLAZOV_WORKER"

grant select, insert, update on tchnlgs to "18205_BELOGLAZOV_WORKER"

grant select on inprcss to "18205_BELOGLAZOV_CUSTOMER"

grant select, insert on orders to "18205_BELOGLAZOV_CUSTOMER"

grant select on cstmrs to "18205_BELOGLAZOV_CUSTOMER"

grant select on users to "18205_BELOGLAZOV_CUSTOMER"

grant select on roles to "18205_BELOGLAZOV_CUSTOMER"

grant select on drgscmps to "18205_BELOGLAZOV_CUSTOMER"

grant select on drugs to "18205_BELOGLAZOV_CUSTOMER"

grant select on drgtypes to "18205_BELOGLAZOV_CUSTOMER"

grant select on cmpnnts to "18205_BELOGLAZOV_CUSTOMER"

grant select on tchnlgs to "18205_BELOGLAZOV_CUSTOMER"