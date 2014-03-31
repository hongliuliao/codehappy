/*
 * FileName:WebDesign.java, v1.0 2009-6-19 下午07:59:56 created by finallygo
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JTable;
import com.finallygo.model.Webmodel;
import com.finallygo.pojo.WebField;
import com.finallygo.tools.BuildJsp;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * TODO描述类的功能
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class WebDesign extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JComboBox cboWebType = null;
	
	private Webmodel webmodel;

	private JButton btnAdd = null;

	private JTextField txtChineseName = null;

	private JTextField txtFieldName = null;

	private JButton btnOK = null;

	private JButton btnEdit = null;

	private JButton btnSubmit = null;

	private JButton btnDelete = null;

	/**
	 * This is the default constructor
	 */
	public WebDesign(Webmodel webmodel) {
		super();
		this.webmodel=webmodel;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(499, 396);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		
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

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(getCboWebType(), null);
			jPanel.add(getBtnAdd(), null);
			jPanel.add(getTxtChineseName(), null);
			jPanel.add(getTxtFieldName(), null);
			jPanel.add(getBtnOK(), null);
			jPanel.add(getBtnEdit(), null);
			jPanel.add(getBtnSubmit(), null);
			jPanel.add(getBtnDelete(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(65, 31, 317, 211));
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setModel(webmodel);
		}
		return jTable;
	}

	/**
	 * This method initializes cboWebType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCboWebType() {
		if (cboWebType == null) {
			cboWebType = new JComboBox();
			cboWebType.addItem("text");
			cboWebType.addItem("password");
			cboWebType.addItem("submit");
			cboWebType.addItem("button");
			cboWebType.addItem("hidden");
			cboWebType.addItem("checkbox");
			cboWebType.addItem("radio");
			cboWebType.addItem("file");
			cboWebType.addItem("reset");
			cboWebType.setBounds(new Rectangle(301, 256, 94, 23));
		}
		return cboWebType;
	}

	/**
	 * This method initializes btnAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setBounds(new Rectangle(405, 62, 69, 23));
			btnAdd.setText("添加");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					webmodel.addWebField(new WebField(txtChineseName.getText(),txtFieldName.getText(),cboWebType.getSelectedItem().toString()));
					txtChineseName.setText("");
					txtFieldName.setText("");
					cboWebType.setSelectedItem("text");
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes txtChineseName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtChineseName() {
		if (txtChineseName == null) {
			txtChineseName = new JTextField();
			txtChineseName.setBounds(new Rectangle(72, 256, 94, 23));
		}
		return txtChineseName;
	}

	/**
	 * This method initializes txtFieldName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtFieldName() {
		if (txtFieldName == null) {
			txtFieldName = new JTextField();
			txtFieldName.setBounds(new Rectangle(187, 256, 94, 23));
		}
		return txtFieldName;
	}

	/**
	 * This method initializes btnOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(206, 327, 90, 22));
			btnOK.setText("提交");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BuildJsp.buildEditJsp(webmodel.webFields);
					JOptionPane.showMessageDialog(null, "恭喜,编辑页面已成功生成!");
					BuildJsp.buildShowJsp(webmodel.webFields);
					JOptionPane.showMessageDialog(null, "恭喜,显示页面已成功生成!");
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes btnEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(405, 112, 69, 23));
			btnEdit.setText("编辑");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int deleteIndex=jTable.getSelectedRow();
					if(deleteIndex==0){
						JOptionPane.showMessageDialog(null, "大哥,主键是不能编辑滴!");
						return;
					}else if(deleteIndex==-1){
						JOptionPane.showMessageDialog(null, "选都莫滴选,编辑啥子哩!");
						return;
					}
					txtChineseName.setText(webmodel.getValueAt(deleteIndex, 0).toString());
					txtFieldName.setText(webmodel.getValueAt(deleteIndex, 1).toString());
					cboWebType.setSelectedItem(webmodel.getValueAt(deleteIndex, 2).toString());
					btnSubmit.setEnabled(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSubmit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSubmit() {
		if (btnSubmit == null) {
			btnSubmit = new JButton();
			btnSubmit.setBounds(new Rectangle(405, 139, 69, 23));
			btnSubmit.setEnabled(false);
			btnSubmit.setText("确定");
			btnSubmit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnSubmit.setEnabled(false);
					WebField webField=new WebField(txtChineseName.getText(),txtFieldName.getText(),cboWebType.getSelectedItem().toString());
					webmodel.updateWebField(jTable.getSelectedRow(), webField);
					txtChineseName.setText("");
					txtFieldName.setText("");
					cboWebType.setSelectedItem("text");
				}
			});
		}
		return btnSubmit;
	}

	/**
	 * This method initializes btnDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(new Rectangle(405, 188, 67, 20));
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int deleteIndex=jTable.getSelectedRow();
					if(deleteIndex==0){
						JOptionPane.showMessageDialog(null, "大哥,主键是不能删除滴!");
						return;
					}else if(deleteIndex==-1){
						JOptionPane.showMessageDialog(null, "选都莫滴选,删啥子哩!");
						return;
					}
					webmodel.webFields.remove(deleteIndex);
					webmodel.fireTableRowsDeleted(deleteIndex, deleteIndex);
				}
			});
		}
		return btnDelete;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
