/*
 * FileName:MainFrame.java, v1.0 2009-5-15 下午07:41:02 created by Administrator
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.ui;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JButton;

import com.finallygo.tools.FinTools;

/**
 * TODO描述类的功能
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	public static JTextField txtPojoName = null;

	private JLabel jLabel = null;

	private JButton btnBuild = null;

	private JLabel lblHello = null;

	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("config");  //  @jve:decl-index=0:
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lblHello = new JLabel();
			lblHello.setBounds(new Rectangle(41, 15, 246, 30));
			lblHello.setText("欢迎使用编程无忧v1.2");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(39, 83, 130, 28));
			jLabel.setText("请输入pojo名称(小写)");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getTxtPojoName(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getBtnBuild(), null);
			jPanel.add(lblHello, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes txtPojoName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtPojoName() {
		if (txtPojoName == null) {
			txtPojoName = new JTextField();
			txtPojoName.setBounds(new Rectangle(183, 83, 130, 28));
			txtPojoName.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
						btnBuild.doClick();
					}
				}
			});
		}
		return txtPojoName;
	}

	/**
	 * This method initializes btnBuild	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBuild() {
		if (btnBuild == null) {
			btnBuild = new JButton();
			btnBuild.setBounds(new Rectangle(116, 150, 94, 28));
			btnBuild.setText("生成");
			btnBuild.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean buildState=FinTools.build(txtPojoName.getText());
					if(buildState){
						String path=BUNDLE.getString("buildPath");
						JOptionPane.showMessageDialog(null, "恭喜,一些文件生成好了\n放到了"+path+"下");
						new DbDesign().setVisible(true);
//						MainFrame.this.setVisible(false);
						return;
					}
					JOptionPane.showMessageDialog(null, "不知道为什么失败了?");

				}
			});
		}
		return btnBuild;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame thisClass = new MainFrame();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(348, 244);
		this.setContentPane(getJContentPane());
		this.setTitle("编程无忧");
		//居中
		int w = this.getSize().width; 
		int h =this.getSize().height; 
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		int x = (dim.width-w)/2; 
		int y = (dim.height-h)/2; 
		this.setLocation(x,y);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="209,54"
