package net.java.sip.communicator.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import net.java.sip.communicator.SipCommunicator;
import net.java.sip.communicator.sip.CommunicationsException;
import net.java.sip.communicator.sip.RequestProcessing;

public class ForwardSplash extends JApplet {
    private static JFrame frame;
    private static String userUFWD;
    static JComboBox<String> cb;

    public static void FwdS(int width, int height) throws CommunicationsException, ParseException{
        RequestProcessing requestProcessing = new RequestProcessing();

        requestProcessing.processInfo("FORWARD", null);

        while(SipCommunicator.globalChoices == null){
            System.out.println("DEBUG: in the loop!");
            try {
                Thread.sleep(500);
            } catch (Exception exc) {
                System.out.println("DEBUG: exception!");
            }
        }

        System.out.println("DEBUG: out of the loop!");

        JComboBox<String> localcb = new JComboBox<String>(SipCommunicator.globalChoices);
        JFrame localframe = new JFrame();
        cb = localcb;
        frame = localframe;
        JPanel panel = new JPanel();
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocation(200, 100);
        frame.add(panel);

        JLabel lbl = new JLabel("Select one of the possible users and click Apply");
        lbl.setVisible(true);

        panel.add(lbl);
        applyButton.setEnabled(true);
        cancelButton.setEnabled(true);

        ActionListener applyListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                applyFWDButton_actionPerformed(evt);
            }
        };
        applyButton.addActionListener(applyListener);

        ActionListener cancelListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                cancelFWDButton_actionPerformed(evt);
            }
        };
        cancelButton.addActionListener(cancelListener);

        cb.setVisible(true);
        panel.add(cb);
        panel.add(applyButton);
        panel.add(cancelButton);

    }

    public static void UFwdS(int width, int height){
        RequestProcessing requestProcessing = new RequestProcessing();

        try {
        	requestProcessing.processInfo("RFORWARD", null);
        } catch (Exception exc) {
        	System.out.println("Looks like we'll never get another call...");
        	return;
        }

        while(SipCommunicator.globalChoices == null){
            System.out.println("DEBUG: in the loop!");
            try {
                Thread.sleep(500);
            } catch (Exception exc) {
                System.out.println("DEBUG: exception!");
            }
        }

        System.out.println("DEBUG: out of the loop!");

        userUFWD = SipCommunicator.globalChoices[0];
        JPanel panel = new JPanel();
        JButton user = new JButton(userUFWD);
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        JFrame localframe = new JFrame();
        frame = localframe;
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocation(200, 100);
        frame.add(panel);

        JLabel lbl = new JLabel("Would you like to cancel the forwarding to this user ?");
        lbl.setVisible(true);

        panel.add(lbl);
        applyButton.setEnabled(true);
        cancelButton.setEnabled(true);

        ActionListener applyListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                applyUFWDButton_actionPerformed(evt);
            }
        };
        applyButton.addActionListener(applyListener);

        ActionListener cancelListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                cancelUFWDButton_actionPerformed(evt);
            }
        };
        cancelButton.addActionListener(cancelListener);
        panel.add(user);
        panel.add(applyButton);
        panel.add(cancelButton);

    }

    static void applyFWDButton_actionPerformed(EventObject evt) {
        String user = (String) cb.getSelectedItem();
        System.out.println("I CHOSE " + user);

        RequestProcessing requestProcessing = new RequestProcessing();

        try {
            requestProcessing.processInfo("FORWARD", user);
        } catch (Exception exc) {
            return;
        }

        SipCommunicator.globalChoices = null;
        frame.dispose();
    }

    static void cancelFWDButton_actionPerformed(EventObject evt){
        SipCommunicator.globalChoices = null;
        frame.dispose();
    }

    static void applyUFWDButton_actionPerformed(EventObject evt){
    	String user = new String("");
        System.out.println("I CHOSE " +userUFWD);
        RequestProcessing requestProcessing = new RequestProcessing();

        try {
            requestProcessing.processInfo("RFORWARD", user);
        } catch (Exception exc) {
            return;
        }

        SipCommunicator.globalChoices = null;

        frame.dispose();
    }

    static void cancelUFWDButton_actionPerformed(EventObject evt){
        SipCommunicator.globalChoices = null;
        frame.dispose();
    }
}

