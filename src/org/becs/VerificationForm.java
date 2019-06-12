package org.becs;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.verification.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class VerificationForm extends CaptureForm {

    private DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
    Map<byte[], String> map = new HashMap<>();
    int i = 0;

    VerificationForm(Frame owner) {
        super(owner);
        //JOptionPane.showMessageDialog(null,"Workimg");
    }

    @Override
    protected void init() {
        super.init();
        rec();
        this.setTitle("Fingerprint Verification");
        updateStatus(0);
    }

    protected static byte[] imagedata;
    protected static String firstname, lastname, matricno, department, faculty;

    @Override
    protected void process(DPFPSample sample) {
        super.process(sample);

        // Process the sample and create a feature set for the enrollment purpose.
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);//DATA_PURPOSE_VERIFICATION);

        // Check quality of the sample and start verification if it's good
        if (features != null) {
            //JOptionPane.showMessageDialog(null,"Work****");
            for (Map.Entry<byte[], String> entry : map.entrySet()) {
                //System.out.println(entry.getKey() + " = " + entry.getValue());
                DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
                t.deserialize(entry.getKey());
                //setTemplate(t);

                // Compare the feature set with our template
                DPFPVerificationResult result = verificator.verify(features, t);//((MainForm)getOwner()).getTemplate());
                System.out.println(i++);
                updateStatus(result.getFalseAcceptRate());
                if (result.isVerified()) {
                    makeReport("The fingerprint was VERIFIED.");
                    checkRec(entry.getValue());
                    System.out.println(entry.getValue());
                    //this.dispose();
                    break;

                } else {
                    makeReport("The fingerprint was NOT VERIFIED.");
                }
            }
        }
    }

    private void updateStatus(int FAR) {
        // Show "False accept rate" value
        setStatus(String.format("False Accept Rate (FAR) = %1$s", FAR));
    }

    private void checkRec(String search) {
        PreparedStatement ps;

        try {
            String sql = "SELECT * FROM FingerPrints WHERE MatricNo = ?";
            Connection conn = DBase.conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, search);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                firstname = rs.getString("FirstName");
                lastname = rs.getString("LastName");
                matricno = rs.getString("MatricNo");
                department = rs.getString("Department");
                faculty = rs.getString("Faculty");
                imagedata = rs.getBytes("Passport");
                System.out.println(firstname + " " + lastname + " " + matricno + " " + department + " " + faculty);
                ((HomeForm) getOwner()).setDBValues(firstname, lastname, matricno, department, faculty, imagedata);

            } else {
                System.out.println("Record not found");
            }

        } catch (Exception ex) {
            Logger.getLogger(VerificationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rec() {
        PreparedStatement ps;

        try {
            String sql = "SELECT * FROM FingerPrints";
            Connection conn = DBase.conn();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getBytes("Finger"), rs.getString("MatricNo"));

            }

        } catch (Exception ex) {
            Logger.getLogger(VerificationForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
