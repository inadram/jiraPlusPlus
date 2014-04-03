package jiraPlusPlus.electronicBoard.jiraService;

import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import javax.net.ssl.*;

import org.json.JSONObject;

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

        configureSSLFactory();
    }

    public String getCurrentStatus(String key) throws Exception {
        String statusUrl = jiraUrl + "issue/" + key;
        JSONObject result = performGet(statusUrl);
        String name = result.getJSONObject("fields").getJSONObject("status").getString("name");

        System.out.println("Current status name: " + name);

        String status;
        if (name.equalsIgnoreCase("Open") || name.equalsIgnoreCase("Reopened")) {
            status = "ToDo";
        }
        else if (name.equalsIgnoreCase("In Progress")) {
            status  = "InProgress";
        }
        else {
            status = "Done";
        }
        return status;
    }

    public void transition(String key, String transitionId) throws Exception {
        String transitionUrl = jiraUrl + "issue/" + key + "/transitions";
        String data = "{\"update\": {\"comment\": [{\"add\": {\"body\": \"Start progress\" }}]},\"transition\": {\"id\": \"" + transitionId + "\"}}";
        performPost(transitionUrl, data);
    }

    private JSONObject performPost(String postUrl, String data) throws Exception {
        return performHttpsRequest(postUrl, data, "POST");
    }

    private JSONObject performGet(String getUrl) throws Exception {
        return performHttpsRequest(getUrl, "", "GET");
    }

    private JSONObject performHttpsRequest(String postUrl, String data, String method) throws Exception {
        long startTime = System.currentTimeMillis();
        URL url = new URL(postUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        con.setRequestMethod(method);

        if (data.length() > 0) {
            con.setRequestProperty("Content-Type", "application/json; charset=utf8");
            con.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(con.getOutputStream());
            outputStream.writeBytes(data);
            outputStream.flush();
            outputStream.close();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        String inputLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            responseBuilder.append(inputLine);
        }
        in.close();

        String response = responseBuilder.toString();
        JSONObject resultObject;
        if (response.length() > 0) {
            resultObject = new JSONObject(response);
        }
        else {
            resultObject = new JSONObject();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("HTTPS " + method + " request time to " + postUrl + ": " + (endTime - startTime) + "ms");

        return resultObject;
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
