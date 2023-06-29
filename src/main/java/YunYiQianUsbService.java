
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 云医签UsbKey相关类
 */
public class YunYiQianUsbService {
    private static String progId = "SecCtrl.CACtrlCom.1";

    private static Dispatch XTXAppCOM = null;
    private static String OLD = "1.2.156.10197.1.501";

    public static Variant call(String name, Object... attributes) {
        if (XTXAppCOM == null) {
            XTXAppCOM = ActiveXComponent.createNewInstance(progId);
        }
        return Dispatch.call(XTXAppCOM, name, attributes);
    }

    public static void main(String[] args) {
        int flag = call("KS_SetProv","SKF_SHECA&EsecuKey_KGM_API_sheca.dll",3,"").getInt();
        String signData = call("KS_SignData", "AUTO",0).getString();
        String certBase64 = call("KS_GetCert", 2).getString();

        System.out.println(flag);
        System.out.println(signData);
        System.out.println(certBase64);

        //String bstrInData = YunYiQianService.GetSHA256FormString("待验签数据，大XML");

        String bstrInData = "待验签数据，一个大XML";

        Variant ret = call("KS_VerifySignData", bstrInData,signData,certBase64,0);

        System.out.println(ret);

    }
}
