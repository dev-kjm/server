package com.motoo.server.domain.market.api;

import com.motoo.server.domain.market.application.StockService;
import com.motoo.server.domain.market.dto.StockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockApi {
    private final StockService stockService;

    @GetMapping("/stocks")
    public Page<StockDto> stocks(final Pageable pageable) {
        return stockService.findStocks(pageable);
    }

}
