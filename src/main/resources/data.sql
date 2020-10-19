INSERT INTO contractor_type (contractortype_id, contractortype_name)
VALUES (1, 'właściciel'), (2, 'klient')
ON CONFLICT (contractortype_id) DO UPDATE
SET contractortype_name = excluded.contractortype_name;

INSERT INTO settlement_type (settlement_type_id, settlement_type_name, settlement_type_code, split_payment)
VALUES (1, 'STANDARD_TRANSFER', 51,0), (2, 'SPLIT_PAYMENT_TRANSFER',51,1 ), (3,'TAX_TRANSFER',71,0)
ON CONFLICT (settlement_type_id) DO UPDATE
    SET settlement_type_name = excluded.settlement_type_name,
        settlement_type_code = excluded.settlement_type_code,
        split_payment = excluded.split_payment;