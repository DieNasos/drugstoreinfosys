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

-- given orders

begin
    execute immediate 'drop table given cascade constraints';
exception
    when others then
        if sqlcode != -942 then
            raise;
        end if;
end;

begin
    execute immediate 'drop sequence s_given';
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

-- CREATE

-- drug types

create sequence s_drgtypes start with 1 increment by 1 nomaxvalue

create table drgtypes (
    id int,
    name varchar2(50) not null,
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

-- components

create sequence s_tchnlgs start with 1 increment by 1 nomaxvalue

create table tchnlgs (
    id int,
    drug_name varchar2(50) not null,
    description varchar2(50) not null,
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

-- all orders

create sequence s_orders start with 1 increment by 1 nomaxvalue

create table orders (
    id int,
    customer_name varchar2(50) not null,
    phone_number varchar2(20) not null,
    address varchar2(50) not null,
    drug_id int not null,
    amount int not null,
    primary key(id),
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

-- given orders

create sequence s_given start with 1 increment by 1 nomaxvalue

create table given (
    id int,
    order_id int not null,
    primary key(id),
    foreign key (order_id) references orders(id)
)