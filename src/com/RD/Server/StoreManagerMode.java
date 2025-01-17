/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RD.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



/**
 *
 * @author Jordan
 */
public class StoreManagerMode extends javax.swing.JFrame {
    private String bundleTitle; //A Midi file
    private String bundleCoverArt; //A png file
    private String bundleMusic = "\\src\\com\\RD\\Server";  //Notes file generated by the MidiConverter
    private javax.swing.JButton jButtonBrowse_CoverArt;
    private javax.swing.JButton jButtonBrowse_Music;
    private javax.swing.JButton jButtonBrowse_title;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelCoverArt;
    private javax.swing.JLabel jLabelMusic;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelDetailsEntry;
    private javax.swing.JTextField jTextFieldCoverArt;
    private javax.swing.JTextField jTextFieldMusic;
    private javax.swing.JTextField jTextFieldTitle;


    /**
     * Creates new form StoreManagerMode
     */
    public StoreManagerMode() {
        //Setup window
        initComponents();
    }


    /**
     * Creates zip files of three files and saves to server folder "src/com/RD/Server/Bundles"
     * @param evt
     */
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {
        // Create Zip of three files and save to Bundles file
        //Check that text boxes are not empty
        if (jTextFieldTitle.getText() == null || jTextFieldCoverArt.getText() ==null ){
            JOptionPane.showMessageDialog(this, "Please enter a MIDI and PNG file files");
        } else {
            try {
                //Convert midi file to notes file, notes file is saved in the notes/file.txt and then add this to a zip
                MIDIConverter.generate(bundleTitle);

                addToZip(bundleTitle, bundleCoverArt, bundleMusic);
                jTextFieldTitle.setText("");
                jTextFieldCoverArt.setText("");
                jTextFieldMusic.setText("");
                JOptionPane.showMessageDialog(this, "Bundle saved!");
            } catch (IOException ex) {
                System.out.println(ex); System.exit(1);
            }
        }
    }

    /**
     * Selects a midi file
     * @param evt
     */
    private void jButtonBrowse_titleActionPerformed(java.awt.event.ActionEvent evt) {
        // Open file explorer to find MIDI files
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File chosenFile = fileChooser.getSelectedFile();
            //Check that it is a MIDI file
            if (chosenFile.toString().endsWith(".mid")){
                bundleTitle = chosenFile.toString();
                jTextFieldTitle.setText(bundleTitle.substring(chosenFile.toString().lastIndexOf("\\") + 1));

            } else {
                JOptionPane.showMessageDialog(this, "Please Choose a MIDI file");
            }
            System.out.println("bundleTitle = " + bundleTitle);

        }

    }


    /**
     * Selects a coverart image
     * @param evt
     */
    private void jButtonBrowse_CoverArtActionPerformed(java.awt.event.ActionEvent evt) {
        //File PNG files
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File chosenFile = fileChooser.getSelectedFile();
            //Check that it is a PNG file
            if (chosenFile.toString().endsWith(".png")){
                bundleCoverArt = chosenFile.toString();
                jTextFieldCoverArt.setText(bundleCoverArt.substring(chosenFile.toString().lastIndexOf("\\") + 1));
            } else {
                JOptionPane.showMessageDialog(this, "Please Choose a PNG file");
            }
            System.out.println("bundleCoverArt = " + bundleCoverArt);

        }
    }

    /**
     * Se
     * @param evt
     */
    private void jButtonBrowse_MusicActionPerformed(java.awt.event.ActionEvent evt) {

        //FInd Notes files saved in txt files
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File chosenFile = fileChooser.getSelectedFile();
            //Check that it is a MIDI file
            if (chosenFile.toString().endsWith(".txt")){
                bundleMusic = chosenFile.toString();
                jTextFieldMusic.setText(bundleMusic.substring(chosenFile.toString().lastIndexOf("\\") + 1));
            } else {
                JOptionPane.showMessageDialog(this, "Please Choose a txt file");
            }
            System.out.println("bundleMusic = " + bundleMusic);

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex); System.exit(1);
        } catch (InstantiationException ex) {
            System.out.println(ex); System.exit(1);
        } catch (IllegalAccessException ex) {
            System.out.println(ex); System.exit(1);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println(ex); System.exit(1);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StoreManagerMode().setVisible(true);
            }
        });
    }



    // End of variables declaration

    private void addToZip(String bundleTitle, String bundleCoverArt, String bundleMusic) throws FileNotFoundException, IOException{
        byte[] buffer = new byte[1024];
        //The archive bundle is saved as the title of the song.
        String BundlesPath = "src\\com\\RD\\Server\\Bundles\\";
        FileOutputStream fos = new FileOutputStream(BundlesPath + bundleTitle.substring(bundleTitle.lastIndexOf("\\") + 1, bundleTitle.lastIndexOf(".mid"))+".zip");
        ZipOutputStream zos = new ZipOutputStream(fos);


        ZipEntry title = new ZipEntry(jTextFieldTitle.getText());
        zos.putNextEntry(title);
        ZipEntry coverArt = new ZipEntry(jTextFieldCoverArt.getText());
        zos.putNextEntry(coverArt);
        ZipEntry music = new ZipEntry(jTextFieldMusic.getText());
        zos.putNextEntry(music);


        FileInputStream in = new FileInputStream(bundleTitle);
        FileInputStream in2 = new FileInputStream(bundleCoverArt);
        FileInputStream in3 = new FileInputStream(bundleMusic);

        int len;
        while ((len = in.read(buffer)) > 0){
            zos.write(buffer, 0, len);
        }

        in.close();
        in2.close();
        in3.close();
        zos.closeEntry();
        zos.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */

    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanelDetailsEntry = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelCoverArt = new javax.swing.JLabel();
        jLabelMusic = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jTextFieldCoverArt = new javax.swing.JTextField();
        jTextFieldMusic = new javax.swing.JTextField();
        jButtonBrowse_title = new javax.swing.JButton();
        jButtonBrowse_CoverArt = new javax.swing.JButton();
        jButtonBrowse_Music = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelDetailsEntry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jLabelTitle.setText("Midi File:");
        jLabelCoverArt.setText("Cover Art:");
        jLabelMusic.setText("Bundle Title:");
        jButtonBrowse_title.setText("Browse");
        jButtonBrowse_title.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowse_titleActionPerformed(evt);
            }
        });

        jButtonBrowse_CoverArt.setText("Browse");
        jButtonBrowse_CoverArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowse_CoverArtActionPerformed(evt);
            }
        });
        jButtonBrowse_Music.setText("Browse");
        jButtonBrowse_Music.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { jButtonBrowse_MusicActionPerformed(evt);}
        });


        /**
         * The following code sets up the group layout of the buttons and panels.
         */
        javax.swing.GroupLayout jPanelDetailsEntryLayout = new javax.swing.GroupLayout(jPanelDetailsEntry);
        jPanelDetailsEntry.setLayout(jPanelDetailsEntryLayout);
        jPanelDetailsEntryLayout.setHorizontalGroup(
                jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelDetailsEntryLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanelDetailsEntryLayout.createSequentialGroup()
                                                .addComponent(jLabelMusic)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextFieldMusic, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelDetailsEntryLayout.createSequentialGroup()
                                                .addComponent(jLabelCoverArt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextFieldCoverArt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelDetailsEntryLayout.createSequentialGroup()
                                                .addComponent(jLabelTitle)
                                                .addGap(55, 55, 55)
                                                .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButtonBrowse_title)
                                        .addComponent(jButtonBrowse_CoverArt)
                                        .addComponent(jButtonBrowse_Music))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDetailsEntryLayout.setVerticalGroup(
                jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelDetailsEntryLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelTitle)
                                        .addGroup(jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButtonBrowse_title)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelCoverArt)
                                        .addGroup(jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextFieldCoverArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButtonBrowse_CoverArt)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelDetailsEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabelMusic)
                                        .addComponent(jTextFieldMusic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonBrowse_Music))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanelDetailsEntryLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonBrowse_CoverArt, jButtonBrowse_Music, jButtonBrowse_title, jLabelCoverArt, jLabelMusic, jLabelTitle, jTextFieldCoverArt, jTextFieldMusic, jTextFieldTitle});

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(132, 132, 132)
                                .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanelDetailsEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelDetailsEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(9, 9, 9))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Store Manager ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }
}
