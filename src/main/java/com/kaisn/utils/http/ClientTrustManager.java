package com.kaisn.utils.http;

import com.kaisn.utils.PropertiesUtil;
import com.kaisn.utils.StringUtils;
import com.kaisn.utils.encry.SymmetricEncoder;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class ClientTrustManager implements X509TrustManager
{

	private X509TrustManager x509TrustManager;

	public ClientTrustManager() throws Exception
	{
		Properties properties = PropertiesUtil.getProperties("/properties/config.properties", PropertiesUtil.defaultType);
		String keyStorePath=properties.getProperty("keystore.path");
		String keyStorePassword=properties.getProperty("keystore.password");
		String keyStoreType=properties.getProperty("keystore.type");
		if(StringUtils.isNotBlack(keyStorePath) && StringUtils.isNotBlack(keyStorePassword) && StringUtils.isNotBlack(keyStoreType))
		{
			//解密证书密码
			//keyStorePassword= AESKey.getInstance().decrypt(keyStorePassword);
			keyStorePassword=SymmetricEncoder.AESDncode(keyStorePassword);
			InputStream inputStream = ClientTrustManager.class.getResourceAsStream(keyStorePath);
			KeyStore ks = KeyStore.getInstance(keyStoreType);
			ks.load(inputStream, keyStorePassword.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);
			TrustManager[] tms = tmf.getTrustManagers();
			for (TrustManager trustManager : tms)
			{
				if(trustManager instanceof X509TrustManager)
				{
					x509TrustManager=(X509TrustManager) trustManager;
				}
			}
		}
	}

	public void checkClientTrusted(X509Certificate[] chain, String type) throws CertificateException
	{
		if(x509TrustManager!=null)
		{
			x509TrustManager.checkServerTrusted(chain, type);
		}
	}

	public void checkServerTrusted(X509Certificate[] chain, String type) throws CertificateException
	{
		if(x509TrustManager!=null)
		{
			x509TrustManager.checkClientTrusted(chain, type);
		}
	}

	public X509Certificate[] getAcceptedIssuers()
	{
		if(x509TrustManager!=null)
		{
			return x509TrustManager.getAcceptedIssuers();
		}
		return null;
	}
}