insert into drgtypes values (s_drgtypes.nextval, 'type_1')

insert into drgtypes values (s_drgtypes.nextval, 'type_2')

insert into tchnlgs values (s_tchnlgs.nextval, 'drug_1', 'description_1')

insert into tchnlgs values (s_tchnlgs.nextval, 'drug_2', 'description_2')

insert into drugs values (s_drugs.nextval, 1, 1, 275.0, 25, 10)

insert into drugs values (s_drugs.nextval, 2, 2, 150.0, 10, 5)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_1', 100, 10.0)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_2', 75, 15.0)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_3', 125, 20.0)

insert into cmpnnts values (s_cmpnnts.nextval, 'component_4', 50, 25.0)

insert into drgscmps values (s_drgscmps.nextval, 1, 1, 10.0)

insert into drgscmps values (s_drgscmps.nextval, 1, 3, 5.0)

insert into drgscmps values (s_drgscmps.nextval, 2, 2, 3.0)

insert into drgscmps values (s_drgscmps.nextval, 2, 4, 1.0)

insert into cstmrs values (s_cstmrs.nextval, '18205_BELOGLAZOV_CUSTOMER', 'Beloglazov Daniil Alexandrovich', '+7-777-777-77-77', 'Troika, 34b')

insert into cstmrs values (s_cstmrs.nextval, 'mazhura', 'Mazhura Denis Denisovich', '+7-987-654-32-10', 'Troika, 34b')

insert into cstmrs values (s_cstmrs.nextval, 'bontivenko', 'Botvinenko Viktor Vitalievich', '+7-012-345-67-89', 'Troika, 34b')

insert into cstmrs values (s_cstmrs.nextval, 'kokunin', 'Kokunin Nikita Alekseevich', '+7-024-689-75-31', 'Semyorka, 18m')

insert into cstmrs values (s_cstmrs.nextval, 'leonteva', 'Leonteva Margarita Konstantinovna', '+7-975-310-24-58', 'Barnaul, Potok')

insert into orders values (s_orders.nextval, 1, 1, 1, 0)

insert into orders values (s_orders.nextval, 2, 1, 1, 0)

insert into orders values (s_orders.nextval, 3, 2, 1, 0)

insert into orders values (s_orders.nextval, 4, 2, 1, 0)

insert into inprcss values (s_inprcss.nextval, 1, timestamp '2021-05-15 19:00:00')

insert into inprcss values (s_inprcss.nextval, 2, timestamp '2021-05-20 12:00:00')

insert into inprcss values (s_inprcss.nextval, 3, timestamp '2021-05-25 18:00:00')

insert into inprcss values (s_inprcss.nextval, 4, timestamp '2021-05-31 15:00:00')