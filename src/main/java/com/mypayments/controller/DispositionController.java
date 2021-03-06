package com.mypayments.controller;

import com.mypayments.domain.Dto.DispositionDto;
import com.mypayments.domain.Dto.DispositionsPackageDto;
import com.mypayments.domain.Dto.PaymentDto;
import com.mypayments.exception.*;
import com.mypayments.mapper.DispositionMapper;
import com.mypayments.mapper.PaymentMapper;
import com.mypayments.service.DispositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class DispositionController {

    @Autowired
    private DispositionService dispositionService;

    @Autowired
    private DispositionMapper dispositionMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/dispositions/{dispositionId}")
    public DispositionDto getDisposition(@PathVariable("dispositionId") Long dispositionId) throws DispositionNotFoundException {
        return dispositionMapper.mapToDispositionDto(dispositionService.getDispositionById(dispositionId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dispositions")
    public List<DispositionDto> getAllDispositions() {
        return dispositionMapper.mapToDispositionDtoList(dispositionService.getAllDispositions());
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/dispositions")
    public DispositionDto updateDisposition(@RequestBody DispositionDto dispositionDto) throws ContractorNotFoundException, SettlementNotFoundException, BankAccountNotFoundException, DispositionNotFoundException {
        return dispositionMapper.mapToDispositionDto(dispositionService.updateDisposition(dispositionMapper.mapToDisposition(dispositionDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/dispositions/{dispositionId}")
    public void deleteDisposition(@PathVariable("dispositionId") Long dispositionId) throws DispositionNotFoundException {
        dispositionService.deleteDispositionById(dispositionId);
    }

    @RequestMapping(value = "/dispositions/list", method = RequestMethod.POST)
    public void getDispositionsList(@RequestBody DispositionsPackageDto dispositionsPackageDto) throws SettlementNotFoundException {
        System.out.println(dispositionsPackageDto.getIdsList().size());
        dispositionService.createDispositionsListBySettlements(dispositionsPackageDto.getIdsList(), LocalDate.parse(dispositionsPackageDto.getDateOfExecution()));
    }

    @RequestMapping(value = "/dispositions/download", method = RequestMethod.GET)
    public @ResponseBody
    void downloadFile(@RequestParam("id") String[] itemIds, HttpServletResponse resp) throws DispositionNotFoundException, InvalidDataFormatException {
        List<Long> idList = new ArrayList<>();
        for (String itemId : itemIds) {
            idList.add(Long.parseLong(itemId));
        }
        String response = dispositionService.createDispositionFile(idList);

        String downloadFileName = "disposition.txt";
        String downloadStringContent = response; // implement this
        try {
            OutputStream out = resp.getOutputStream();
            resp.setContentType("text/plain; charset=Windows-1250");
            resp.addHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName + "\"");
            out.write(downloadStringContent.getBytes(Charset.forName("Windows-1250")));
            out.flush();
            out.close();

        } catch (IOException e) {
        }
    }


}
