//import com.cdxt.client.core.service.YunYiQianUsbService;
import com.jacob.com.Variant;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class YunYiQianWebService {

    public static String verfiySign(String appCode,String appPwd,String cert,String inData,String signData) throws Exception {
        //定义wsdl路径
        String endpoint = "http://103.36.136.173:7845/services/CipherServices";
        Service service = new Service();
        Call call = (Call) service.createCall();
        //wsdl路径
        call.setTargetEndpointAddress(new java.net.URL(endpoint));

        call.setOperationName("verifySignData");

        String request = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Root><AppCode>%s</AppCode><AppPWD>%s</AppPWD><Request><Cert>%s</Cert><SignAlg>SM3withSM2</SignAlg><InData>%s</InData><SignData>%s</SignData></Request></Root>"
                ,appCode,appPwd,cert,inData,signData);

        /*request = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "    <Root>\n" +
                "\t<AppCode>ywxt</AppCode>\n" +
                "\t<AppPWD>12345678</AppPWD>\n" +
                "\t<Request>\n" +
                "\t\t<Cert>MIIB9TCCAZigAwIBAgIOAV53M9Vp5AY8j5DL1EUwDAYIKoEcz1UBg3UFADA+MQswCQYDVQQGEwJDTjEUMBIGA1UEAwwLU00yIFJPT1QgQ0ExCzAJBgNVBAgMAlNIMQwwCgYDVQQKDANoYWgwHhcNMjMwMzA5MDY1MzQyWhcNMjUwMzA5MDY1MzQyWjBZMQswCQYDVQQGEwJDTjELMAkGA1UECAwCU0gxCzAJBgNVBAcMAlNIMQ0wCwYDVQQKDARzaWduMQ0wCwYDVQQLDARzaWduMRIwEAYDVQQDDAlzaWduX3Rlc3QwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAT9dIroxCUB5oZvxl2Irq3OoAtbWvqa5tNvw854VGmgJxfuuTfgBtcVdEHkbXsSvRNdj9Uq2ld2XFjoaIyHaC1Co10wWzAMBgNVHRMBAf8EAjAAMB8GA1UdIwQYMBaAFCE77Si3YBk/u4qjZMQAJNHBvWKUMB0GA1UdDgQWBBSjsmDHccu255pXS76XpNE3LL7f7DALBgNVHQ8EBAMCB4AwDAYIKoEcz1UBg3UFAANJADBGAiEAkECq7DAZiONAwDblXKigoyGkmjePKsS2L4noqg/wQuwCIQCGnRJITQ+ErYg/l+YNrAWgY1UtoQnsM8A4XIsUynzu7Q==</Cert>\n" +
                "\t\t<SignAlg>SM3withSM2</SignAlg>\n" +
                "\t\t<InData>待验签数据，一个大XML</InData>\n" +
                "\t\t<SignData>MEQCIHlGEOQVj9x7fJUw4/eapYfzK2Z+vJmPlOKsRl+9jS3qAiBWfsg6aAHjhNR6ScOLgnJ1f0UCZEfSWQFqZErv0ArcCg==</SignData>\n" +
                "\t</Request>\n" +
                "</Root>";*/

        System.out.println("request="+request);

        Object[] o = new Object[] { request };
        String res = call.invoke(o).toString();
        System.out.println(res);
        return res;
    }

    public static void main(String[] args) throws Exception {

        int flag = YunYiQianUsbService.call("KS_SetProv","SKF_SHECA&EsecuKey_KGM_API_sheca.dll",3,"").getInt();
        String signData = YunYiQianUsbService.call("KS_SignData", "AUTO",0).getString();
        String certBase64 = YunYiQianUsbService.call("KS_GetCert", 2).getString();

        System.out.println("flag="+flag);
        System.out.println("signData="+signData);
        System.out.println("certBase64"+certBase64);

        //String bstrInData = YunYiQianService.GetSHA256FormString("待验签数据，大XML");

        String bstrInData = "待验签数据，一个大XML";

        String res = YunYiQianWebService.verfiySign("ywxt","12345678",certBase64,bstrInData,signData);
        System.out.println(res);
    }
}
