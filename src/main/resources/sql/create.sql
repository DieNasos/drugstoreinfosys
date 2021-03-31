begin
    execute immediate 'drop table drgtypes';
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

create sequence s_drgtypes start with 1 increment by 1 nomaxvalue

create table drgtypes (
    id int,
    name varchar2(50) not null,
    primary key(id)
)

begin
    execute immediate 'drop table cmpnnts';
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

create sequence s_cmpnnts start with 1 increment by 1 nomaxvalue

create table cmpnnts (
    id int,
    name varchar2(50) not null,
    amount int not null,
    primary key(id)
)