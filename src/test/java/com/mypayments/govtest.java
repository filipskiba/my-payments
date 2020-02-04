package com.mypayments;

import com.mypayments.domain.BankAccount;
import com.mypayments.domain.Contractor;
import com.mypayments.domain.Status;
import com.mypayments.exception.*;
import com.mypayments.gov.GovFacade;
import com.mypayments.nbp.NbpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class govtest {
    @Autowired
    GovFacade govFacade;

    @Autowired
    NbpClient nbpClient;

    @Test
    public void testGov() throws InvalidDataFormatException, EmptyDataException {
        Contractor contractor = new Contractor();
        contractor.setNipId("5550005695");
        contractor.getBankAccounts().add(new BankAccount(1L,"92-2490 0005 0000 4530 2277 9555",contractor));
        contractor.getBankAccounts().add(new BankAccount(1L,"25 1010 0071 2221 9105 0513 5920",contractor));

        Status test = govFacade.createStatus(contractor);
        System.out.println(test.toString());

        nbpClient.getExchangeRates().stream().forEach(n->n.getRatesDtoList().stream().forEach(c->System.out.println(c.getCurrency()+" kurs: "+c.getMid()+"zł")));

    }
}
