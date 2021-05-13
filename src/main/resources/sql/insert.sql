insert into drgtypes values (s_drgtypes.nextval, 'type_1')

insert into drgtypes values (s_drgtypes.nextval, 'type_2')

insert into tchnlgs values (s_tchnlgs.nextval, 'drug_1', 'description_1')

insert into tchnlgs values (s_tchnlgs.nextval, 'drug_2', 'description_2')

insert into drugs values (s_drugs.nextval, 1, 1, 275.0, 50, 10)

insert into drugs values (s_drugs.nextval, 2, 2, 150.0, 30, 5)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_1', 100, 10.0)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_2', 75, 15.0)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_3', 125, 20.0)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_4', 50, 25.0)

insert into drgscmps values (s_drgscmps.nextval, 1, 1, 10.0)

insert into drgscmps values (s_drgscmps.nextval, 1, 3, 5.0)

insert into drgscmps values (s_drgscmps.nextval, 2, 2, 3.0)

insert into drgscmps values (s_drgscmps.nextval, 2, 4, 1.0)

insert into roles values (s_roles.nextval, 'admin')

insert into roles values (s_roles.nextval, 'store_worker')

insert into roles values (s_roles.nextval, 'customer')

insert into users values (s_users.nextval, '18205_BELOGLAZOV', 1)

insert into users values (s_users.nextval, '18205_BELOGLAZOV_WORKER', 2)

insert into users values (s_users.nextval, '18205_BELOGLAZOV_CUSTOMER', 3)

insert into users values (s_users.nextval, 'mazhura', 3)

insert into users values (s_users.nextval, 'botvinenko', 3)

insert into users values (s_users.nextval, 'kokunin', 3)

insert into users values (s_users.nextval, 'leonteva', 3)

insert into cstmrs values (s_cstmrs.nextval, 3, 'Beloglazov Daniil Alexandrovich', '+7-777-777-77-77', 'Troika, 34b')

insert into cstmrs values (s_cstmrs.nextval, 4, 'Mazhura Denis Denisovich', '+7-987-654-32-10', 'Troika, 34b')

insert into cstmrs values (s_cstmrs.nextval, 5, 'Botvinenko Viktor Vitalievich', '+7-012-345-67-89', 'Troika, 34b')

insert into cstmrs values (s_cstmrs.nextval, 6, 'Kokunin Nikita Alekseevich', '+7-024-689-75-31', 'Semyorka, 18m')

insert into cstmrs values (s_cstmrs.nextval, 7, 'Leonteva Margarita Konstantinovna', '+7-975-310-24-58', 'Barnaul, Potok')

insert into orders values (s_orders.nextval, 1, 1, 1, 0)

insert into orders values (s_orders.nextval, 2, 1, 1, 0)

insert into orders values (s_orders.nextval, 3, 2, 1, 0)

insert into orders values (s_orders.nextval, 4, 2, 1, 0)

insert into inprcss values (s_inprcss.nextval, 1, timestamp '2021-05-05 19:00:00')

insert into inprcss values (s_inprcss.nextval, 2, timestamp '2021-05-10 12:00:00')

insert into inprcss values (s_inprcss.nextval, 3, timestamp '2021-05-15 18:00:00')

insert into inprcss values (s_inprcss.nextval, 4, timestamp '2021-05-20 15:00:00')