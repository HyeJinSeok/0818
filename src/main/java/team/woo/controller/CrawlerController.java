package team.woo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CrawlerController {

    @GetMapping("/fetch-info")
    public ResponseEntity<Map<String, String>> fetchInfo(@RequestParam String query) {
        Map<String, String> result = new HashMap<>();
        try {
            String url = "https://www.google.com/search?q=" + query;
            Document doc = Jsoup.connect(url).get();

            // 예시로 첫 번째 제목 추출
            Element resultTitle = doc.select("h3").first();
            String info = resultTitle != null ? resultTitle.text() : "정보를 찾을 수 없습니다.";

            // URL 생성 (이 예시에서는 Google 검색 URL 사용)
            String resultUrl = "https://www.google.com/search?q=" + query;

            result.put("info", info);
            result.put("url", resultUrl);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("info", "크롤링 중 오류가 발생했습니다.");
            result.put("url", "#");
        }
        return ResponseEntity.ok(result);
    }
}