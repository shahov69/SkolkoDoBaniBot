select setval('setting_sequence', (select max(id) from setting));
select setval('banya_sequence', (select max(id) from banya));
select setval('ilushizm_sequence', (select max(id) from ilushizm));
select setval('kirushizm_sequence', (select max(id) from kirushizm));