INSERT INTO contractor_type (contractortype_id, contractortype_name)
VALUES (1, 'właściciel'), (2, 'klient')
ON CONFLICT (contractortype_id) DO UPDATE
SET contractortype_name = excluded.contractortype_name;
