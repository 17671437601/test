

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;

public class TestTimeStamp {

	private Service service;
	private String url;

	public TestTimeStamp() {
		this.service = new Service();
	}

	public TestTimeStamp(String url) {
		this.service = new Service();
		this.url = url;
	}

	public String invoke(String method, String request)
			throws ServiceException, RemoteException {
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(url);
		call.setOperationName(method);
		Object[] o = new Object[] { request };
		return call.invoke(o).toString();
	}

	/**
	 * @param args
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException, ServiceException {

		String url = "http://103.36.136.173:7845/services/TimeStampServices";
		TestTimeStamp test = new TestTimeStamp(url);

		String inData = "thisistest";
		String hashData = "yiLkflLG6DU/PqpgkG/EqQLurCY=";

		// 签发时间戳 by hashData
		String stRequestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+ "<Root><AppCode>ywxt</AppCode><AppPWD>12345678</AppPWD><Request><HashData>"
				+ hashData
				+ "</HashData>"
				+ "<HashAlg>SHA1</HashAlg><CertReq>false</CertReq></Request></Root>";
		String stResponseXml = test.invoke("generateTimestampByHashData", stRequestXml);
		System.out.println("时间戳签发返回11：" + stResponseXml);

		// 签发时间戳 by inData
		stRequestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+ "<Root><AppCode>ywxt</AppCode><AppPWD>12345678</AppPWD><Request><InData>"
				+ new String(Base64.encode(inData.getBytes()))
				+ "</InData>"
				+ "<HashAlg>SHA1</HashAlg><CertReq>false</CertReq></Request></Root>";
		stResponseXml = test.invoke("generateTimestampByInData", stRequestXml);
		System.out.println("时间戳签发返回22：" + stResponseXml);

		String timeStamp = "MIICrjADAgEAMIICpQYJKoZIhvcNAQcCoIICljCCApICAQMxCzAJBgUrDgMCGgUAMIGQBgsqhkiG9w0BCRABBKCBgAR+MHwCAQEGASowITAJBgUrDgMCGgUABBTKIuR+UsboNT8+qmCQb8SpAu6sJgIHG0IP6QZeRxgPMjAxNDEyMTcwODA0NDRaAgQEYzygoDGkLzAtMQswCQYDVQQGEwJDTjEeMBwGA1UEAwwV5pe26Ze05oiz5rWL6K+V6K+B5LmmMYIB6zCCAecCAQEwNTAtMQswCQYDVQQGEwJDTjEeMBwGA1UEAwwV5pe26Ze05oiz5rWL6K+V6K+B5LmmAgQGn2vHMAkGBSsOAwIaBQCggYwwGgYJKoZIhvcNAQkDMQ0GCyqGSIb3DQEJEAEEMBwGCSqGSIb3DQEJBTEPFw0xNDEyMTcwODA0NDRaMCMGCSqGSIb3DQEJBDEWBBTpIziPKClKGBoGfmKQ/+fdEOW8XTArBgsqhkiG9w0BCRACDDEcMBowGDAWBBSDORUNiZaNyhn5ciTrV8GqdPa/HTANBgkqhkiG9w0BAQEFAASCAQBIXIKIl87gi/YfjyWabqw4VKpeizcOqnmyROksgqat9P969ZbD6JlfsYetsD/5mZeRDcn0+xmzQq2+X1NOXlzl6uNIYlcIU59PU37VZnDa/nnWOQVFbaGjNXYyxqHJ8gYa/m4wBmQ+uFZ3tt/QWeA7JqnEtrX20ThDyXSUuyt1SD4TZr+v4Bk/w2PwtfFmEtzwnz+npj5hMjNkbgmIcToVJHJ2hLLxXk8cU4k5vBLtdp098QQD+bEO4u24D3jAJC8xIySZmjF0Tk86KJHri4AsuLWxOOuGWfHDqnGtwBGMv4TYwjXcYmwdAMDhkZgcv6sIH8LhK6gFFDuWf+OQ0ZBQ";
		// 验证时间戳
		stRequestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+ "<Root><AppCode>ywxt</AppCode><AppPWD>12345678</AppPWD><Request><TimeStamp>"
				+ timeStamp
				+ "</TimeStamp>"
				+ "<Cert></Cert></Request></Root>";
		stResponseXml = test.invoke("verifyTimeStamp", stRequestXml);
		System.out.println("时间戳验证返回：" + stResponseXml);

		// 解析时间戳
		stRequestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+ "<Root><AppCode>ywxt</AppCode><AppPWD>12345678</AppPWD><Request><TimeStamp>"
				+ timeStamp
				+ "</TimeStamp></Request></Root>";
		stResponseXml = test.invoke("getTimeStampInfo", stRequestXml);
		System.out.println("时间戳解析返回：" + stResponseXml);
	}
}
