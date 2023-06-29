package VerifySignature;

import com.custle.sdk.common.CertificateInfo;
import com.custle.sdk.local.svs.SvsUtil;
import org.bouncycastle.util.encoders.Base64;

//验证签名示例
public class Test {

    public static void main(String args[]) {
        // 原文
        String inData = "abc";
        // 签名证书
        String cert = "MIIC5DCCAoigAwIBAgIQfHgMVn77TISBN196VIlM6DAMBggqgRzPVQGDdQUAMDQxCzAJBgNVBAYTAkNOMREwDwYDVQQKDAhVbmlUcnVzdDESMBAGA1UEAwwJU0hFQ0EgU00yMB4XDTIzMDUxNjA2MDAzNVoXDTI0MDUxNjE1NTk1OVowHDELMAkGA1UEBhMCQ04xDTALBgNVBAMMBHRlc3QwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAgTYzR0FpuRiecoMFqe1WdgsebBzmIGx5EFLb8n+ZaZpU1X5GkokjoaIBlrE4P7mNMRrRLpQSSX/1FXzRKUmysvUo4IBkDCCAYwwIgYDVR0jAQH/BBgwFoAUiTEEkXtDqqqav4Qdm4bu8LhwmaAwKQYIKoEchu86gRQEHTEwMUA1MDA4U0YwNTAwMjQzMTk5ODA4MDgwMjUxMCAGA1UdDgEB/wQWBBQ14wxzm0VYFsw8roLSBxcrBxpY2zAOBgNVHQ8BAf8EBAMCB4AwJAYFKlYVAQEEGzFANTAwOFNGMDUwMDI0MzE5OTgwODA4MDI1MTCB4gYDVR0fBIHaMIHXMIGaoIGXoIGUhoGRbGRhcDovL2xkYXAyLnNoZWNhLmNvbTozODkvY249Q1JMMTQyNi5jcmwsb3U9UkEyMDE2MTAxMixvdT1DQTkxLG91PWNybCxvPVVuaVRydXN0P2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RDbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDA4oDagNIYyaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9DQTkxL1JBMjAxNjEwMTIvQ1JMMTQyNi5jcmwwDAYIKoEcz1UBg3UFAANIADBFAiEA/4+bj9xKoXcn3a8VX/BKoG6jPNwCw8wQYmxidHAyIB4CIALiuEf6AjtwdqQrPgGio0YVPZdmMzph1iIqrx+jcF6d";
        // 签名值
        String signData = "MEUCIGDS9bbCZ+J3GtkX4w9cEAn7Ec1V7oRWcTQuwp1tCv6/AiEAoLzfW4EuQidSuuufQHHPaVTpqpfgPskb8z+rW+OScVM=";
        // 签名算法
        String signAlg = "SM3withSM2";

        try {
            // 数字签名验证
            boolean b = SvsUtil.verify(cert, signAlg, inData.getBytes(), SvsUtil.base64Decode(signData));
            System.out.println("验证签名结果：" + b);
            // 解析证书内容
			CertificateInfo certificateInfo = new CertificateInfo(Base64.decode(cert));
            CertificateInfo certInfo = SvsUtil.getCertInfo(cert);

            System.out.println("证书序列号：" + certInfo.getSerialNumber());
            System.out.println("证书使用者DN：" + certInfo.getSubject());
            System.out.println("证书使用者CN：" + certInfo.getCommonName());
            System.out.println("有效期开始时间：" + certInfo.getNotBefore());
            System.out.println("有效期结束时间：" + certInfo.getNotAfter());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
