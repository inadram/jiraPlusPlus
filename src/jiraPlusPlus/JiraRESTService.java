package jiraPlusPlus;

import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import javax.net.ssl.*;

public class JiraRESTService implements IJiraService {
    private final String jiraUrl;
    private final String certPath;
    private final String serverStorePath;
    private final String password;

    public JiraRESTService(String jiraUrl, String certPath, String serverStorePath, String password) throws Exception {
        this.jiraUrl = jiraUrl;
        this.certPath = certPath;
        this.password = password;
        this.serverStorePath = serverStorePath;
    }

    public int transition(String key, String transitionId) throws Exception {
        configureSSLFactory();

        URL url =  new URL(jiraUrl + "issue/" + key + "/transitions");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=utf8");

        String data = "{\"update\": {\"comment\": [{\"add\": {\"body\": \"Start progress\" }}]},\"transition\": {\"id\": \"" + transitionId + "\"}}";

        con.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(con.getOutputStream());
        outputStream.writeBytes(data);
        outputStream.flush();
        outputStream.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());


        return responseCode;
    }

    private void configureSSLFactory() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        clientStore.load(new FileInputStream(certPath), this.password.toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(clientStore, this.password.toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream(this.serverStorePath), "changeit".toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }
}
