package org.becs;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.processing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EnrollmentForm extends CaptureForm {

    private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();

    EnrollmentForm(Frame owner) {
        super(owner);
    }

    @Override
    protected void init() {
        super.init();
        this.setTitle("Fingerprint Enrollment");
        updateStatus();
    }

    @Override
    protected void process(DPFPSample sample) {
        super.process(sample);
        // Process the sample and create a feature set for the enrollment purpose.
        DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        // Check quality of the sample and add to enroller if it's good
        if (features != null) {
            try {
                makeReport("The fingerprint feature set was created.");
                System.out.println("+++++++++++++++++++++++++++++++++");
                enroller.addFeatures(features);
                writeText(Arrays.toString(features.serialize()));// Add feature set to template.
            } catch (DPFPImageQualityException ex) {
            } catch (IOException ex) {
                Logger.getLogger(EnrollmentForm.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                updateStatus();
// Check if template has been created.
                switch (enroller.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:	// report success and stop capturing
                        stop();
                        ((HomeForm) getOwner()).setTemplate(enroller.getTemplate());
                        System.out.println(Arrays.toString(enroller.getTemplate().serialize()));
                         {
                        }
                        setPrompt("Click Close, and then click Fingerprint Verification.");
                        break;

                    case TEMPLATE_STATUS_FAILED:	// report failure and restart capturing
                        enroller.clear();
                        stop();
                        updateStatus();
                        ((HomeForm) getOwner()).setTemplate(null);
                        JOptionPane.showMessageDialog(EnrollmentForm.this, "The fingerprint template is not valid. Repeat fingerprint enrollment.", "Fingerprint Enrollment", JOptionPane.ERROR_MESSAGE);
                        start();
                        break;
                }
            }
        }
    }

    private void updateStatus() {
        // Show number of samples needed.
        setStatus(String.format("Fingerprint samples needed: %1$s", enroller.getFeaturesNeeded()));
    }
void writeText(String s) throws IOException{
        BufferedWriter writeval = new BufferedWriter(new FileWriter("fingerdata.txt"));
            writeval.write(s);
            writeval.close();
}}
