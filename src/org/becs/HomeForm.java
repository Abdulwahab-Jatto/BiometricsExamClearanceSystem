/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.becs;

import com.digitalpersona.onetouch.DPFPTemplate;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author User 2
 */
public class HomeForm extends javax.swing.JFrame {

    /**
     * Creates new form HomeForm
     */
    public HomeForm() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/pics/logo.png")).getImage());
        backgrd();
        homPanel.setVisible(true);
        regPanel.setVisible(false);
        verPanel.setVisible(false);
        faqPanel.setVisible(false);
        aboPanel.setVisible(false);
        reBtn.setEnabled(false);
        vfntxt.setEditable(false);
        vlntxt.setEditable(false);
        vmatxt.setEditable(false);
        vdetxt.setEditable(false);
        vfatxt.setEditable(false);
        this.addPropertyChangeListener(TEMPLATE_PROPERTY, (PropertyChangeEvent evt) -> {
            if (evt.getNewValue() == evt.getOldValue()) return;
            if (template != null){
                JOptionPane.showMessageDialog(HomeForm.this, "The fingerprint template is ready for fingerprint verification.", "Fingerprint Enrollment", JOptionPane.INFORMATION_MESSAGE);
                drawPicture(CaptureForm.img);
                reBtn.setEnabled(true);
            }
              
            
        });
        
        this.addPropertyChangeListener(VERIFY, (PropertyChangeEvent evt) -> {
            if (evt.getNewValue() == evt.getOldValue()) return;
            if(firstname != null){
                 vfntxt.setText(firstname);
                 vlntxt.setText(lastname);
                 vmatxt.setText(matricno);
                 vdetxt.setText(department);
                 vfatxt.setText(faculty);
                 ImageIcon format = new ImageIcon(imagedata);
              Rectangle rec = vpassLbl.getBounds();
            Image scaledimage = format.getImage().getScaledInstance(rec.width,rec.height,Image.SCALE_DEFAULT);
            format = new ImageIcon(scaledimage); 
            vpassLbl.setIcon(format);
            drawPicture1(CaptureForm.img);
             }  
        });
    }

    protected static byte[] imagedata;
            protected static String firstname,lastname,matricno,department,faculty;
    
    int i = 0;
    String passFile;
    public void sliderToRight(){
        Rectangle from = new Rectangle(-120, 62, 120, 440);
            Rectangle to = new Rectangle(0, 124 / 2, 120, 440);

            Animate animate = new Animate(sliderPanel,to,from);
            animate.start();
    }
    
    public void sliderToLeft(){
        Rectangle from = new Rectangle(0, 62, 120, 440);
            Rectangle to = new Rectangle(-120, 124 / 2, 120, 440);

            Animate animate = new Animate(sliderPanel,to,from);
            animate.start();
    }
    
    public static class Animate {

        public static final int RUN_TIME = 500;

        private JPanel panel;
        private Rectangle from;
        private Rectangle to;

        private long startTime;

        public Animate(JPanel panel, Rectangle from, Rectangle to) {
            this.panel = panel;
            this.from = from;
            this.to = to;
        }

        public void start() {
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long duration = System.currentTimeMillis() - startTime;
                    double progress = (double)duration / (double)RUN_TIME;
                    if (progress > 1f) {
                        progress = 1f;
                        ((Timer)e.getSource()).stop();
                    }
                    Rectangle target = calculateProgress(to, from, progress);
                    panel.setBounds(target);
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            startTime = System.currentTimeMillis();
            timer.start();
        }

    }

    public static Rectangle calculateProgress(Rectangle startBounds, Rectangle targetBounds, double progress) {

        Rectangle bounds = new Rectangle();

        if (startBounds != null && targetBounds != null) {

            bounds.setLocation(calculateProgress(startBounds.getLocation(), targetBounds.getLocation(), progress));
            bounds.setSize(calculateProgress(startBounds.getSize(), targetBounds.getSize(), progress));

        }

        return bounds;

    }

    public static Point calculateProgress(Point startPoint, Point targetPoint, double progress) {

        Point point = new Point();

        if (startPoint != null && targetPoint != null) {

            point.x = calculateProgress(startPoint.x, targetPoint.x, progress);
            point.y = calculateProgress(startPoint.y, targetPoint.y, progress);

        }

        return point;

    }

    public static int calculateProgress(int startValue, int endValue, double fraction) {

        int value = 0;
        int distance = endValue - startValue;
        value = (int)Math.round((double)distance * fraction);
        value += startValue;

        return value;

    }

    public static Dimension calculateProgress(Dimension startSize, Dimension targetSize, double progress) {

        Dimension size = new Dimension();

        if (startSize != null && targetSize != null) {

            size.width = calculateProgress(startSize.width, targetSize.width, progress);
            size.height = calculateProgress(startSize.height, targetSize.height, progress);

        }

        return size;

    }
    
    public void defaultColour(){
        homBtn.setIcon(new ImageIcon(getClass().getResource("/pics/homC.png")));
        regBtn.setIcon(new ImageIcon(getClass().getResource("/pics/regC.png")));
        verBtn.setIcon(new ImageIcon(getClass().getResource("/pics/verC.png")));
        faqBtn.setIcon(new ImageIcon(getClass().getResource("/pics/faqC.png")));
        aboBtn.setIcon(new ImageIcon(getClass().getResource("/pics/aboC.png")));
    }
    
    public void changeBtn(String str){
        ImageIcon image = new ImageIcon(getClass().getResource(str));
       Rectangle rec = jButton5.getBounds();
       Image scaledimage = image.getImage().getScaledInstance(rec.width,rec.height,Image.SCALE_DEFAULT);
       image = new ImageIcon(scaledimage);
       jButton5.setIcon(image);
    }
    public void backgrd(){
        ImageIcon image = new ImageIcon(getClass().getResource("/pics/bg2.jpg"));
       Rectangle rec = bgLbl.getBounds();
       Image scaledimage = image.getImage().getScaledInstance(rec.width,rec.height,Image.SCALE_DEFAULT);
       image = new ImageIcon(scaledimage);
       bgLbl.setIcon(image);
    }
    
    private java.io.File showFileDialog(boolean open) {
    JFileChooser fc = new JFileChooser("Open an image");
    javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() {
       public boolean accept(java.io.File f) {
          String name = f.getName().toLowerCase();
          return f.isDirectory() || name.endsWith(".png") || name.endsWith(".bmp")|| name.endsWith(".jpg")|| name.endsWith(".jpeg");
          }
       public String getDescription() {
          return "Image (*.png, *.bmp,*.jpg,*.jpeg)";
          }
       };
    fc.setAcceptAllFileFilterUsed(false);
    fc.addChoosableFileFilter(ff);

    java.io.File f = null;
   if(open && fc.showOpenDialog(this) == fc.APPROVE_OPTION)
       f = fc.getSelectedFile();
    else if(!open && fc.showSaveDialog(this) == fc.APPROVE_OPTION)
       f = fc.getSelectedFile();
    return f;
    }

 private void openImage() {
    java.io.File f = showFileDialog(true);
    try {  
       passFile = f.getAbsolutePath();
       ImageIcon image = new ImageIcon(passFile);
       Rectangle rec = passLbl.getBounds();
       Image scaledimage = image.getImage().getScaledInstance(rec.width,rec.height,Image.SCALE_DEFAULT);
       image = new ImageIcon(scaledimage);
       passLbl.setIcon(image);
       this.validate();
       } catch(Exception ex) { System.out.println(""); }
    }

    void insertRec(){
        try {
            InputStream fis = new FileInputStream(new File(passFile));
            PreparedStatement ps;
            Connection conn = DBase.conn();
            String sql = "INSERT INTO FingerPrints (FirstName,LastName,MatricNo,Department,Faculty,Gender,Passport,Finger) VALUES (?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, fntxt.getText());
            ps.setString(2, lntxt.getText());
            ps.setString(3, matxt.getText());
            ps.setString(4, detxt.getText());
            ps.setString(5, fatxt.getText());
            ps.setString(6, gencom.getSelectedItem().toString());
            ps.setBlob(7,fis);
            ps.setBytes(8, template.serialize());
            
            int InsertRec = ps.executeUpdate();
           
            if (InsertRec > 0) {
                System.out.println("DBDBDBDBDDBDBDBDBDBDBBDDBDBBDBDBDBDDB");
                JOptionPane.showMessageDialog(null, "Inserted into the Database");
            } else {
                JOptionPane.showMessageDialog(null, "Inserted not into  the Database");
            }
        } catch (Exception ex) {}
            
    }
    public DPFPTemplate getTemplate() {
		return template;
	}
	
public void setTemplate(DPFPTemplate template) {
		DPFPTemplate old = this.template;
		this.template = template;
		firePropertyChange(TEMPLATE_PROPERTY, old, template);
	}

public void setDBValues(String DBfn,String DBln,String DBma,String DBde,String DBfa,byte[] DBpas ) {
            this.firstname = DBfn;
            this.lastname=DBln;
            this.matricno = DBma;
            this.department = DBde;
            this.faculty = DBfa;
            this.imagedata = DBpas;
    firePropertyChange(VERIFY, DBfn, DBln);
	}
   public static String TEMPLATE_PROPERTY = "template";
   public static String VERIFY="verify";
   private DPFPTemplate template; 
    
   public void drawPicture(Image image) {
		pic.setIcon(new ImageIcon(image.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_DEFAULT)));
	}
   public void drawPicture1(Image image) {
		finpic.setIcon(new ImageIcon(image.getScaledInstance(finpic.getWidth(), finpic.getHeight(), Image.SCALE_DEFAULT)));
	}
   
    public boolean validateMatNum(String id) throws ClassNotFoundException{

       PreparedStatement ps;
            
            
       try{    
           Connection conn = DBase.conn();
       String sql = "SELECT MatricNo FROM FingerPrints";
            ps=conn.prepareStatement (sql); 
            ResultSet rs = ps.executeQuery();
            if( rs.next()){
               
            return id.equalsIgnoreCase(rs.getString("MatricNo"));
               
            } }
        catch (Exception ex) {
            Logger.getLogger(HomeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
       return false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sliderPanel = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        homBtn = new javax.swing.JButton();
        regBtn = new javax.swing.JButton();
        verBtn = new javax.swing.JButton();
        faqBtn = new javax.swing.JButton();
        aboBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        aboPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        verPanel = new javax.swing.JPanel();
        vpassLbl = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        finpic = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        vfntxt = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        vlntxt = new javax.swing.JTextField();
        jSeparator15 = new javax.swing.JSeparator();
        vmatxt = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        vdetxt = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        vfatxt = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        faqPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel7 = new javax.swing.JLabel();
        regPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        fntxt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        matxt = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        fatxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lntxt = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        detxt = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        gencom = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        pic = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        passLbl = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        reBtn = new javax.swing.JButton();
        enBtn = new javax.swing.JButton();
        homPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        bgLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biometric Examination Clearance System(BECS))");
        setMinimumSize(new java.awt.Dimension(625, 500));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(null);

        sliderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSeparator3MouseMoved(evt);
            }
        });
        sliderPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 120, -1));
        sliderPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 182, 120, -1));
        sliderPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 243, 120, -1));

        jSeparator6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSeparator6MouseMoved(evt);
            }
        });
        sliderPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 121, 120, -1));
        sliderPanel.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 305, 120, -1));

        homBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/homC.png"))); // NOI18N
        homBtn.setToolTipText("");
        homBtn.setBorder(null);
        homBtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                homBtnMouseMoved(evt);
            }
        });
        homBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homBtnActionPerformed(evt);
            }
        });
        sliderPanel.add(homBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 60));

        regBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/regC.png"))); // NOI18N
        regBtn.setBorder(null);
        regBtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                regBtnMouseMoved(evt);
            }
        });
        regBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regBtnActionPerformed(evt);
            }
        });
        sliderPanel.add(regBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 61, 120, 60));

        verBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/verC.png"))); // NOI18N
        verBtn.setBorder(null);
        verBtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                verBtnMouseMoved(evt);
            }
        });
        verBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBtnActionPerformed(evt);
            }
        });
        sliderPanel.add(verBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 122, 120, 60));

        faqBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/faqC.png"))); // NOI18N
        faqBtn.setBorder(null);
        faqBtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                faqBtnMouseMoved(evt);
            }
        });
        faqBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faqBtnActionPerformed(evt);
            }
        });
        sliderPanel.add(faqBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 183, 120, 60));

        aboBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/aboC.png"))); // NOI18N
        aboBtn.setBorder(null);
        aboBtn.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                aboBtnMouseMoved(evt);
            }
        });
        aboBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboBtnActionPerformed(evt);
            }
        });
        sliderPanel.add(aboBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 244, 120, 60));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/sliderbar.png"))); // NOI18N
        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel3MouseMoved(evt);
            }
        });
        sliderPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.add(sliderPanel);
        sliderPanel.setBounds(-120, 62, 120, 440);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/menu.png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(20, 10, 30, 40);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/logoR.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(70, 5, 66, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/topBar.png"))); // NOI18N
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel1MouseMoved(evt);
            }
        });
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 625, 60);
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(0, 60, 625, 2);

        aboPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel8.setText("<html><center>Biometrics Examination Clearance System (BECS) uses Digitalpersona<br>Fingerprint Scanner/Reader to scan/read fingerprint into the<br> system for enrollment and recognition for <br>students examination clearance processes.</center>");
        aboPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 150, -1, -1));

        jPanel1.add(aboPanel);
        aboPanel.setBounds(0, 60, 625, 440);

        verPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vpassLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/pass.png"))); // NOI18N
        verPanel.add(vpassLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 60, 120, 130));

        jButton4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(96, 120, 249));
        jButton4.setText("Verify");
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        verPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 120, 40));

        finpic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/scan.png"))); // NOI18N
        verPanel.add(finpic, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 120, 130));

        jSeparator13.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        verPanel.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 115, 150, 2));

        vfntxt.setBackground(new java.awt.Color(214, 217, 223));
        vfntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        vfntxt.setBorder(null);
        verPanel.add(vfntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 85, 150, 30));

        jLabel23.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(96, 120, 249));
        jLabel23.setText("First Name");
        verPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 65, -1, -1));

        jLabel24.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(96, 120, 249));
        jLabel24.setText("Last Name");
        verPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 65, -1, -1));

        jSeparator14.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        verPanel.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 115, 150, 2));

        vlntxt.setBackground(new java.awt.Color(214, 217, 223));
        vlntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        vlntxt.setBorder(null);
        verPanel.add(vlntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 85, 150, 30));

        jSeparator15.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        verPanel.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 185, 150, 2));

        vmatxt.setBackground(new java.awt.Color(214, 217, 223));
        vmatxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        vmatxt.setBorder(null);
        verPanel.add(vmatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 155, 150, 30));

        jSeparator16.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        verPanel.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 185, 150, 2));

        vdetxt.setBackground(new java.awt.Color(214, 217, 223));
        vdetxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        vdetxt.setBorder(null);
        verPanel.add(vdetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 155, 150, 30));

        jLabel26.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(96, 120, 249));
        jLabel26.setText("Department");
        verPanel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 135, -1, -1));

        jLabel27.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(96, 120, 249));
        jLabel27.setText("Faculty");
        verPanel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 205, -1, -1));

        jSeparator17.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        verPanel.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 255, 150, 2));

        vfatxt.setBackground(new java.awt.Color(214, 217, 223));
        vfatxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        vfatxt.setBorder(null);
        verPanel.add(vfatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 225, 150, 30));

        jLabel28.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(96, 120, 249));
        jLabel28.setText("Matriculation Number");
        verPanel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 135, -1, -1));

        jPanel1.add(verPanel);
        verPanel.setBounds(0, 60, 625, 440);

        faqPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setText("<html> <body style=\"background-color:#ffffff;width:625px;height:440px;margin-left:15px\"> <left><p> <font style=\"color:#D70918;justify-content:center\">Why is DigitalPersona fingerprint scanner/reader not connecting to the system?</font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> This is possible as a result of damaged USB port. Insert or plug the scanner/reader to another USB port.</font> </p><p> <font style=\"color:#D70918;justify-content:center\">Why is DigitalPersona fingerprint scanner/reader not detected by the system?</font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> Simple install the scanner/reader drivers which comes with BECS software installation.</font> </p><p> <font style=\"color:#D70918;justify-content:center\">Scanner/reader unable to reader my finger?</font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> Simply clean the surface of the scanner/reader with a clean and dry clothing material.</font> </p><p> <font style=\"color:#D70918;justify-content:center\">What about cuts, other injuries?</font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> Unless the injury resulted in a severe disfigurement to the finger, then a biometric solution will have no problem identifying the user. Alternatively, an alternate finger can be registered for identification.  Also, As long as the epidermic layer (the outer layer) is not badly damaged, the wound will return to its original shape once the wound recovers. If otherwise, the left scar will be treated as unique characteristic of your finger. </font> </p><p> <font style=\"color:#D70918;justify-content:center\">What about hygiene when everyone is touching the same area with their fingers and then eating?</font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> Antibacterial gel should be readily available at the examination entrances and students are encouraged to use them. The scanners will be part of a daily cleaning routine and will be regularly sanitized.</font> </p><p> <font style=\"color:#D70918;justify-content:center\">Is fingerprinting technology harmful to your health?</font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> There is no evidence to support that fingerprinting technology can impose a negative effect to your health. The methods of capturing fingerprint templates are safe, and non-intrusive to the users.</font> </p><p> <font style=\"color:#D70918;justify-content:center\">Can fingerprint image be duplicated?</font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> No. The fingerprint image is not stored using the graphic format, instead the minutiea is captured and calculated using the algorithm concept before the mathematical data is stored as uniquely yours. The fingerprint obtained from other source will not be recognized as life finger is needed for verification.</font> </p><p> <font style=\"color:#D70918;justify-content:center\">Can a detached finger be used in digitalpersona fingerprint scanner/reader? </font><br> <font style=\"color:#000000;justify-content:center\"><b>Answer:</b> No!  digitalpersona fingerprint scanner/reader only detects live finger, as the sensor detects the moisture and the temperature from a live finger for verification. Chopped or detached finger loses its heat and moist in a very short period of time, therefore cannot be used in digitalpersona fingerprint scanner/reader.</font> </p> </left> </body> </html>");
        jScrollPane1.setViewportView(jLabel7);

        faqPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 625, 440));

        jPanel1.add(faqPanel);
        faqPanel.setBounds(0, 60, 625, 440);

        regPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(96, 120, 249));
        jLabel14.setText("First Name");
        regPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jSeparator8.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        regPanel.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 65, 150, 2));

        fntxt.setBackground(new java.awt.Color(214, 217, 223));
        fntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        fntxt.setBorder(null);
        regPanel.add(fntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 150, 30));

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(96, 120, 249));
        jLabel15.setText("Matriculation Number");
        regPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jSeparator9.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        regPanel.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 150, 2));

        matxt.setBackground(new java.awt.Color(214, 217, 223));
        matxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        matxt.setBorder(null);
        matxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                matxtFocusLost(evt);
            }
        });
        regPanel.add(matxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 150, 30));

        jSeparator10.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        regPanel.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 150, 2));

        fatxt.setBackground(new java.awt.Color(214, 217, 223));
        fatxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        fatxt.setBorder(null);
        regPanel.add(fatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 150, 30));

        jLabel16.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(96, 120, 249));
        jLabel16.setText("Faculty");
        regPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel17.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(96, 120, 249));
        jLabel17.setText("<html><left>Info:<br>Please place index finger in the fingerprint<br>scanner</left>");
        regPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, -1, -1));

        lntxt.setBackground(new java.awt.Color(214, 217, 223));
        lntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lntxt.setBorder(null);
        regPanel.add(lntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 35, 150, 30));

        jSeparator11.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        regPanel.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 65, 150, 2));

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(96, 120, 249));
        jLabel18.setText("Department");
        regPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, -1));

        detxt.setBackground(new java.awt.Color(214, 217, 223));
        detxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        detxt.setBorder(null);
        regPanel.add(detxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 150, 30));

        jSeparator12.setForeground(new java.awt.Color(96, 120, 249));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        regPanel.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 150, 2));

        jLabel19.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(96, 120, 249));
        jLabel19.setText("Gender");
        regPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, -1, -1));

        gencom.setBackground(new java.awt.Color(96, 120, 249));
        gencom.setForeground(new java.awt.Color(96, 120, 249));
        gencom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        gencom.setBorder(null);
        regPanel.add(gencom, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 166, 160, 40));

        jLabel20.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(96, 120, 249));
        jLabel20.setText("Last Name");
        regPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/scan.png"))); // NOI18N
        regPanel.add(pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 120, 130));

        jButton1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(96, 120, 249));
        jButton1.setText("Browse");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        regPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 166, 120, 40));
        regPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 625, -1));

        passLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/pass.png"))); // NOI18N
        regPanel.add(passLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 35, 120, 130));

        jLabel21.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(96, 120, 249));
        jLabel21.setText("Passport");
        regPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        jLabel22.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(96, 120, 249));
        jLabel22.setText("Scan Finger");
        regPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        reBtn.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        reBtn.setForeground(new java.awt.Color(96, 120, 249));
        reBtn.setText("Register");
        reBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reBtnActionPerformed(evt);
            }
        });
        regPanel.add(reBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, 140, 50));

        enBtn.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        enBtn.setForeground(new java.awt.Color(96, 120, 249));
        enBtn.setText("Scan Finger");
        enBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enBtnActionPerformed(evt);
            }
        });
        regPanel.add(enBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 140, 50));

        jPanel1.add(regPanel);
        regPanel.setBounds(0, 60, 625, 440);

        homPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/homLogo.png"))); // NOI18N
        homPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/h1.png"))); // NOI18N
        homPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 472, 41));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/h2.png"))); // NOI18N
        homPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 290, -1, -1));
        homPanel.add(bgLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 625, 440));

        jPanel1.add(homPanel);
        homPanel.setBounds(0, 60, 625, 440);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 625, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(i==0){
        sliderToRight();
        changeBtn("/pics/can.png");
        i++;
        }
        else{
           sliderToLeft();
           changeBtn("/pics/menu.png");
          
           i=0;
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void homBtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homBtnMouseMoved
        homBtn.setIcon(new ImageIcon(getClass().getResource("/pics/homW.png")));
        regBtn.setIcon(new ImageIcon(getClass().getResource("/pics/regC.png")));
        verBtn.setIcon(new ImageIcon(getClass().getResource("/pics/verC.png")));
        faqBtn.setIcon(new ImageIcon(getClass().getResource("/pics/faqC.png")));
        aboBtn.setIcon(new ImageIcon(getClass().getResource("/pics/aboC.png")));
    }//GEN-LAST:event_homBtnMouseMoved

    private void regBtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regBtnMouseMoved
        regBtn.setIcon(new ImageIcon(getClass().getResource("/pics/regW.png")));
        homBtn.setIcon(new ImageIcon(getClass().getResource("/pics/homC.png")));
        verBtn.setIcon(new ImageIcon(getClass().getResource("/pics/verC.png")));
        faqBtn.setIcon(new ImageIcon(getClass().getResource("/pics/faqC.png")));
        aboBtn.setIcon(new ImageIcon(getClass().getResource("/pics/aboC.png")));
    }//GEN-LAST:event_regBtnMouseMoved

    private void verBtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verBtnMouseMoved
        verBtn.setIcon(new ImageIcon(getClass().getResource("/pics/verW.png")));
        regBtn.setIcon(new ImageIcon(getClass().getResource("/pics/regC.png")));
        homBtn.setIcon(new ImageIcon(getClass().getResource("/pics/homC.png")));
        faqBtn.setIcon(new ImageIcon(getClass().getResource("/pics/faqC.png")));
        aboBtn.setIcon(new ImageIcon(getClass().getResource("/pics/aboC.png")));
    }//GEN-LAST:event_verBtnMouseMoved

    private void faqBtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faqBtnMouseMoved
        faqBtn.setIcon(new ImageIcon(getClass().getResource("/pics/faqW.png")));
        regBtn.setIcon(new ImageIcon(getClass().getResource("/pics/regC.png")));
        verBtn.setIcon(new ImageIcon(getClass().getResource("/pics/verC.png")));
        homBtn.setIcon(new ImageIcon(getClass().getResource("/pics/homC.png")));
        aboBtn.setIcon(new ImageIcon(getClass().getResource("/pics/aboC.png")));
    }//GEN-LAST:event_faqBtnMouseMoved

    private void aboBtnMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboBtnMouseMoved
        aboBtn.setIcon(new ImageIcon(getClass().getResource("/pics/aboW.png")));
        regBtn.setIcon(new ImageIcon(getClass().getResource("/pics/regC.png")));
        verBtn.setIcon(new ImageIcon(getClass().getResource("/pics/verC.png")));
        faqBtn.setIcon(new ImageIcon(getClass().getResource("/pics/faqC.png")));
        homBtn.setIcon(new ImageIcon(getClass().getResource("/pics/homC.png")));
    }//GEN-LAST:event_aboBtnMouseMoved

    private void jSeparator3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSeparator3MouseMoved
        
        
    }//GEN-LAST:event_jSeparator3MouseMoved

    private void jSeparator6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSeparator6MouseMoved
        
    }//GEN-LAST:event_jSeparator6MouseMoved

    private void jLabel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseMoved
        defaultColour();
    }//GEN-LAST:event_jLabel1MouseMoved

    private void jLabel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseMoved
        defaultColour();
    }//GEN-LAST:event_jLabel3MouseMoved

    private void homBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homBtnActionPerformed
        sliderToLeft();
        changeBtn("/pics/menu.png");
        i=0;
        homPanel.setVisible(true);
        regPanel.setVisible(false);
        verPanel.setVisible(false);
        faqPanel.setVisible(false);
        aboPanel.setVisible(false);
    }//GEN-LAST:event_homBtnActionPerformed

    private void regBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regBtnActionPerformed
        sliderToLeft();
        changeBtn("/pics/menu.png");
        i=0;
        homPanel.setVisible(!true);
        regPanel.setVisible(!false);
        verPanel.setVisible(false);
        faqPanel.setVisible(false);
        aboPanel.setVisible(false);
    }//GEN-LAST:event_regBtnActionPerformed

    private void verBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBtnActionPerformed
        sliderToLeft();
        changeBtn("/pics/menu.png");
        i=0;
        homPanel.setVisible(false);
        regPanel.setVisible(false);
        verPanel.setVisible(true);
        faqPanel.setVisible(false);
        aboPanel.setVisible(false);
    }//GEN-LAST:event_verBtnActionPerformed

    private void faqBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faqBtnActionPerformed
        sliderToLeft();
        changeBtn("/pics/menu.png");
        i=0;
        homPanel.setVisible(false);
        regPanel.setVisible(false);
        verPanel.setVisible(false);
        faqPanel.setVisible(true);
        aboPanel.setVisible(false);
    }//GEN-LAST:event_faqBtnActionPerformed

    private void aboBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboBtnActionPerformed
        sliderToLeft();
        changeBtn("/pics/menu.png");
        i=0;
        homPanel.setVisible(false);
        regPanel.setVisible(false);
        verPanel.setVisible(false);
        faqPanel.setVisible(false);
        aboPanel.setVisible(true);
    }//GEN-LAST:event_aboBtnActionPerformed

    private void enBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enBtnActionPerformed
        new EnrollmentForm(this).setVisible(true);
    }//GEN-LAST:event_enBtnActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new VerificationForm(this).setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        openImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void reBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reBtnActionPerformed
        try {
            if (!(fntxt.getText().isEmpty() ||validateMatNum(matxt.getText())|| lntxt.getText().isEmpty() || matxt.getText().isEmpty() || detxt.getText().isEmpty() || fatxt.getText().isEmpty() || passFile.isEmpty())){
                insertRec();}
            else
                JOptionPane.showMessageDialog(null, "<html><left><font>Error in Inputs, which maybe caused be the following<ul><li>Empty Fields</li><li>Same Matriculation Number</li>"
                        + "<li>Passport not selected</li></ul></font></center>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_reBtnActionPerformed

    private void matxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_matxtFocusLost
        try {
            if(!matxt.getText().isEmpty()){
            boolean mat = validateMatNum(matxt.getText());
            if (mat) {
                JOptionPane.showMessageDialog(null, "Matric No. already exist");
                matxt.setText("");
            }}

        } catch (ClassNotFoundException ex) {

        }

    }//GEN-LAST:event_matxtFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboBtn;
    private javax.swing.JPanel aboPanel;
    private javax.swing.JLabel bgLbl;
    private javax.swing.JTextField detxt;
    private javax.swing.JButton enBtn;
    private javax.swing.JButton faqBtn;
    private javax.swing.JPanel faqPanel;
    private javax.swing.JTextField fatxt;
    private javax.swing.JLabel finpic;
    private javax.swing.JTextField fntxt;
    private javax.swing.JComboBox<String> gencom;
    private javax.swing.JButton homBtn;
    private javax.swing.JPanel homPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField lntxt;
    private javax.swing.JTextField matxt;
    private javax.swing.JLabel passLbl;
    private javax.swing.JLabel pic;
    private javax.swing.JButton reBtn;
    private javax.swing.JButton regBtn;
    private javax.swing.JPanel regPanel;
    private javax.swing.JPanel sliderPanel;
    private javax.swing.JTextField vdetxt;
    private javax.swing.JButton verBtn;
    private javax.swing.JPanel verPanel;
    private javax.swing.JTextField vfatxt;
    private javax.swing.JTextField vfntxt;
    private javax.swing.JTextField vlntxt;
    private javax.swing.JTextField vmatxt;
    private javax.swing.JLabel vpassLbl;
    // End of variables declaration//GEN-END:variables
}
