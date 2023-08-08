package app.quizbee.service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.*;
import javax.naming.directory.*;

@SuppressWarnings("unchecked")
public class MailService {

    public Object[] sendMail(String toEmail, String code) {
        Object[] o = new Object[2];
        
        if (!isAddressValid(toEmail)) {
            o[0] = false;
            o[1] = "Email don't exists!";
            return o;
        }

        String from = MailConfig.APP_EMAIL;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", MailConfig.HOST_NAME);
        prop.put("mail.smtp.port", MailConfig.TSL_PORT);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfig.APP_EMAIL,
                        MailConfig.APP_PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));
            message.setSubject("Final step to resgister Bee Quiz Account");
            message.setText("Your verify code is: " + code);
            Transport.send(message);
            o[0] = true;
            o[1] = "Send mail Successfully!";
        } catch (MessagingException e) {
            o[0] = false;
            o[1] = "Exception Error";
        }

        return o;
    }

    private int hear(BufferedReader in) throws IOException {
        String line;
        int res = 0;
        while ((line = in.readLine()) != null) {
            String pfx = line.substring(0, 3);
            try {
                res = Integer.parseInt(pfx);
            } catch (NumberFormatException ex) {
                res = -1;
            }
            if (line.charAt(3) != '-') {
                break;
            }
        }
        return res;
    }

    private void say(BufferedWriter wr, String text)
            throws IOException {
        wr.write(text + "\r\n");
        wr.flush();
    }

    private static ArrayList getMX(String hostName)
            throws NamingException {
        // Perform a DNS lookup for MX records in the domain
        final Hashtable env = new Hashtable();

        env.put("java.naming.factory.initial",
                "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext(env);
        Attributes attrs = ictx.getAttributes(hostName, new String[]{"MX"});
        Attribute attr = attrs.get("MX");
        if ((attr == null) || (attr.size() == 0)) {
            attrs = ictx.getAttributes(hostName, new String[]{"A"});
            attr = attrs.get("A");
            if (attr == null) {
                throw new NamingException("No match for name '" + hostName + "'");
            }
        }
        ArrayList res = new ArrayList();
        NamingEnumeration en = attr.getAll();
        while (en.hasMore()) {
            String x = (String) en.next();
            if (x.contains(" ")) {
                String f[] = x.split(" ");
                if (f[1].endsWith(".")) {
                    f[1] = f[1].substring(0, (f[1].length() - 1));
                }
                res.add(f[1]);
            }
        }
        return res;
    }

    public boolean isAddressValid(String address) {
        int pos = address.indexOf('@');
        if (pos == -1) {
            return false;
        }
        String domain = address.substring(++pos);
        ArrayList mxList;
        try {
            mxList = getMX(domain);
        } catch (NamingException ex) {
            return false;
        }
        if (mxList.isEmpty()) {
            return false;
        }
        boolean valid = false;

        for (int mx = 0; mx < mxList.size(); mx++) {
            int res;
            try ( Socket skt = new Socket((String) mxList.get(mx), 25);  BufferedReader rdr = new BufferedReader(new InputStreamReader(skt.getInputStream()));  BufferedWriter wtr = new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()))) {
                res = hear(rdr);
                if (res != 220) {
                    throw new Exception("Invalid header");
                }
                say(wtr, "EHLO orbaker.com");
                res = hear(rdr);
                if (res != 250) {
                    throw new Exception("Not ESMTP");
                }
                // validate the sender address
                say(wtr, "MAIL FROM: <tim@orbaker.com>");
                res = hear(rdr);
                if (res != 250) {
                    throw new Exception("Sender rejected");
                }
                say(wtr, "RCPT TO: <" + address + ">");
                res = hear(rdr);
                // be polite
                say(wtr, "RESET");
                hear(rdr);
                say(wtr, "QUIT");
                hear(rdr);
                if (res != 250) {
                    throw new Exception("Address is not valid!");
                }
                valid = true;
                return valid;
            } catch (IOException ex) {
                return valid;
            } catch (Exception ex) {
                return valid;
            }
        }
        return valid;
    }

    protected class MailConfig {

        public static final String HOST_NAME = "smtp.gmail.com";

        public static final int SSL_PORT = 465;

        public static final int TSL_PORT = 587;

        public static final String APP_EMAIL = "tuanthps23577@fpt.edu.vn";

        public static final String APP_PASSWORD = "mwejzigpwvlzsmlp";
    }
}
