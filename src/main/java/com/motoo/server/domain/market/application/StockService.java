package com.motoo.server.domain.market.application;

import com.motoo.server.domain.market.domain.Kosdaq;
import com.motoo.server.domain.market.dto.StockDto;
import com.motoo.server.domain.market.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StockService {

//    private final StockRepository stockRepository;


    public Page<StockDto> findStocks(Pageable pageable) {
//        Page<Kosdaq> stocks = stockRepository.findAll(pageable);
//        Stockinfo[0] = StockInfo.getNamedItem("JongName").getNodeValue();		//종목명
//        Stockinfo[1] = StockInfo.getNamedItem("CurJuka").getNodeValue();		//현재가
//        Stockinfo[2] = StockInfo.getNamedItem("DungRak").getNodeValue();		//전일대비코드
//        Stockinfo[3] = StockInfo.getNamedItem("Debi").getNodeValue();			//전일대비
//        Stockinfo[4] = StockInfo.getNamedItem("PrevJuka").getNodeValue();		//전일종가
//        Stockinfo[5] = StockInfo.getNamedItem("Volume").getNodeValue();			//거래량
//        Stockinfo[6] = StockInfo.getNamedItem("Money").getNodeValue();			//거래대금
//        Stockinfo[7] = StockInfo.getNamedItem("StartJuka").getNodeValue();		//시가
//        Stockinfo[8] = StockInfo.getNamedItem("HighJuka").getNodeValue();		//고가
//        Stockinfo[9] = StockInfo.getNamedItem("LowJuka").getNodeValue();		//저가
//        Stockinfo[10] = StockInfo.getNamedItem("High52").getNodeValue();		//52주 최고
//        Stockinfo[11] = StockInfo.getNamedItem("Low52").getNodeValue();			//52주 최저
//        Stockinfo[12] = StockInfo.getNamedItem("UpJuka").getNodeValue();		//상한가
//        Stockinfo[13] = StockInfo.getNamedItem("DownJuka").getNodeValue();		//하한가
//        Stockinfo[14] = StockInfo.getNamedItem("Per").getNodeValue();			//PER
//        Stockinfo[15] = StockInfo.getNamedItem("Amount").getNodeValue();		//상장주식수
//        Stockinfo[16] = StockInfo.getNamedItem("FaceJuka").getNodeValue();		//액면가
//        Page<StockDto> stockDtos = stocks.map(kosdaq -> {
//            Document doc = null;
//            try {
//                doc = Jsoup.connect("http://asp1.krx.co.kr/servlet/krx.asp.XMLSise?code="+ kosdaq.getStockCode()).get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            Elements datas = doc.select("TBL_StockInfo");
//            log.info("Debi" + datas.get(0).attr("Debi") );
//            return StockDto.builder()
//                    .companyName(kosdaq.getCompanyName())
//                    .curJuka(datas.get(0).attr("CurJuka"))
//                    .StockCode(kosdaq.getStockCode())
//                    .build();
//        });

        return null;
    }
}
