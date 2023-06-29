package UkeySignature;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
//import com.test.config.Config;
import java.io.File;
import java.util.UUID;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.cn;

/**
 * 2.1.2 UKey签章接口demo
 * @author zhouxq
 * @date 2021/6/3
 */
public class UKeySignTest {

    /**
     * 定位类型
     * position：坐标定位
     * keyword：文字定位
     */
    private static final String TYPE = "position";
    public static String URL="http://127.0.0.1:9002/";
    /**
     * PDF路径
     */
    private static final String PDF_PATH = "D:\\pdf\\sanye.pdf";

    public static void main(String[] args) throws Exception {
        // 生成文档编号
        String documentNo = UUID.randomUUID().toString();
        // 组织请求数据
        JSONObject root = new JSONObject();
        root.putOpt("api_key", "sc");
        root.putOpt("api_secret", "123456");

        JSONObject seal = new JSONObject();
        seal.putOpt("document_no", documentNo);
        seal.putOpt("seal_strategy_id", "ukey");
        seal.putOpt("pdf", Base64.encode(new File(PDF_PATH)));
        // 重定向地址（/seal/getPdf/为测试调试地址）
        seal.putOpt("redirect_url", URL + "/seal/getPdf/" + documentNo);
        seal.putOpt("type", TYPE);

        JSONObject position = new JSONObject();
        position.putOpt("page", "1");
        position.putOpt("x", "25000");
        position.putOpt("y", "25000");
        seal.putOpt("position", position);

        root.putOpt("seal", seal);
        String request = root.toString();
        String res = HttpUtil.post(URL + "seal/ukeySign", request);
        // 打印响应报文
        System.out.println("res:" + res);
    }
}
