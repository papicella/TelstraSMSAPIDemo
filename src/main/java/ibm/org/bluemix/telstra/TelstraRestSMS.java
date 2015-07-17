package ibm.org.bluemix.telstra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by pas on 17/07/15.
 */
@RestController
public class TelstraRestSMS
{
    //private static String RECIPIENT_NUMBER="0411151350";
    private static String APP_KEY = "7OdTnmeVbBAEFZLRnti2DrLtg2PPgf4E";
    private static String APP_SECRET = "anaafk104rlzrZx7";

    private static final Logger log = LoggerFactory.getLogger(TelstraRestSMS.class);
    private static final JsonParser parser = JsonParserFactory.getJsonParser();

    @RequestMapping(value = "/telstrasms", method = RequestMethod.GET)
    public String sendSms(@RequestParam(value="to", required=true) String to)
    {
        RestTemplate restTemplate = new RestTemplate();

        String jsonResponse =
                restTemplate.getForObject
                        (String.format
                                ("https://api.telstra.com/v1/oauth/token?client_id=%s&client_secret=%s&grant_type=client_credentials&scope=SMS",
                                        APP_KEY,
                                        APP_SECRET), String.class);

        Map<String, Object> jsonMap = parser.parseMap(jsonResponse);

        String accessToken = (String) jsonMap.get("access_token");

        log.info("Access Token for SMS API is - " + accessToken);

        String url = "https://api.telstra.com/v1/sms/messages";
        String requestJson = String.format("{\"to\":\"%s\", \"body\":\"Hello, pas sent this message from telstra SMS api on IBM Bluemix!\"}", to);
        log.info(requestJson);

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", String.format("Bearer %s", accessToken));

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String answer = restTemplate.postForObject(url, entity, String.class);

        log.info("Sent SMS to phone number - " + to);

        return answer;
    }
}
