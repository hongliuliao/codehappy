/*
 * FileName:DbDesign.java, v1.0 2009-5-16 下午05:59:46 created by Administrator
 *
 * Copyright (c) 2009 Wance 
 * All Rights Reserved.
 * Confidential and for internal user only.
 */

package com.finallygo.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.finallygo.common.CommonData;
import com.finallygo.model.*;
import com.finallygo.pojo.Field;
import com.finallygo.pojo.WebField;
import com.finallygo.tools.BuildPojo;
import com.finallygo.tools.BuildSQL;
import com.finallygo.tools.BuildSomeMethod;

import java.awt.Dimension;
import javax.swing.JCheckBox;

/**
 * TODO描述类的功能
 *
 * @author finallygo
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class DbDesign extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btnOK = null;

	private JTextField txtColName = null;

	private JComboBox cboDataType = null;

	private JTextField txtLength = null;

	private JCheckBox chkIsNull = null;

	private JButton btnAdd = null;
	
	private Dbmodel model=new Dbmodel();
	
	private boolean isSelect=false;

	private JButton jButton = null;

	private JTextField txtChineseName = null;

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
			jPanel.add(getBtnOK(), null);
			jPanel.add(getTxtColName(), null);
			jPanel.add(getCboDataType(), null);
			jPanel.add(getTxtLength(), null);
			jPanel.add(getChkIsNull(), null);
			jPanel.add(getBtnAdd(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getTxtChineseName(), null);
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
			jScrollPane.setBounds(new Rectangle(18, 14, 503, 250));
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
			jTable.setModel(model);
			jTable.setShowGrid(true);
			jTable.setColumnSelectionAllowed(true);
//			JTextField text = new JTextField();
		}
		return jTable;
	}

	/**
	 * This method initializes btnOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(224, 365, 100, 25));
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BuildSQL.buildSQL(model.fields, MainFrame.txtPojoName.getText());
					JOptionPane.showMessageDialog(null, "恭喜,该表sql脚本导出成功!");
					
					BuildPojo.buildPojo(model.fields, MainFrame.txtPojoName.getText());
					JOptionPane.showMessageDialog(null, "恭喜,pojo成功生成!");
					
					BuildSomeMethod.buildSaveAndUpdate(model.fields, MainFrame.txtPojoName.getText());
					JOptionPane.showMessageDialog(null, "恭喜,添加与更新SQL语句成功生成!");
					//将当前的字段传递到下一个model
					Webmodel webmodel=new Webmodel();
					WebDesign webDesign=new WebDesign(webmodel);
					for (int i=0;i<model.fields.size();i++) {
						if(i==0){
							WebField webField=(WebField) model.fields.get(i);
							webField.setWebType("hidden");
							webmodel.addWebField(webField);
							continue;
						}
						WebField webField=(WebField) model.fields.get(i);
						webField.setWebType("text");
						webmodel.addWebField(webField);
					}
					webDesign.setVisible(true);
					DbDesign.this.setVisible(false);
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes txtColName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtColName() {
		if (txtColName == null) {
			txtColName = new JTextField();
			txtColName.setBounds(new Rectangle(18, 292, 82, 23));
			txtColName.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
						btnAdd.doClick();
					}
				}
			});
			txtColName.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					txtChineseName.setText(CommonData.getSmartName(txtColName.getText()));
				}
			});
		}
		return txtColName;
	}

	/**
	 * This method initializes cboDataType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCboDataType() {
		if (cboDataType == null) {
			cboDataType = new JComboBox();
			cboDataType.setBounds(new Rectangle(220, 292, 82, 23));
			cboDataType.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!isSelect){
						isSelect=true;
						return;
					}
					switch(cboDataType.getSelectedIndex()){
						case 0:
							txtLength.setText("20");
							txtLength.setEditable(true);
							break;
						case 1:
							txtLength.setText("4");
							txtLength.setEditable(false);
							break;
						case 2:
							txtLength.setText("8");
							txtLength.setEditable(false);
							break;
						default:
							txtLength.setEditable(false);
							break;
					}
				}
			});
			cboDataType.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
						btnAdd.doClick();
					}
				}
			});
			cboDataType.addItem("varchar");
			cboDataType.addItem("int");
			cboDataType.addItem("datetime");
		}
		return cboDataType;
	}

	/**
	 * This method initializes txtLength	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtLength() {
		if (txtLength == null) {
			txtLength = new JTextField();
			txtLength.setBounds(new Rectangle(324, 292, 82, 23));
			txtLength.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
						btnAdd.doClick();
					}
				}
			});
			
		}
		return txtLength;
	}

	/**
	 * This method initializes chkIsNull	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getChkIsNull() {
		if (chkIsNull == null) {
			chkIsNull = new JCheckBox();
			chkIsNull.setBounds(new Rectangle(421, 292, 82, 23));
			chkIsNull.setSelected(true);
			chkIsNull.setText("允许为空");
		}
		return chkIsNull;
	}

	/**
	 * This method initializes btnAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setBounds(new Rectangle(504, 290, 89, 23));
			btnAdd.setText("确认添加");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Field field=new WebField(txtColName.getText(),cboDataType.getSelectedItem().toString(),Integer.parseInt(txtLength.getText()),chkIsNull.isSelected(),txtChineseName.getText());
					model.addField(field);
					txtColName.setText(MainFrame.txtPojoName.getText()+"_");
					txtChineseName.setText("");
					txtColName.requestFocusInWindow();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(528, 97, 62, 23));
			jButton.setText("删除");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int deleteIndex=jTable.getSelectedRow();
					if(deleteIndex==0){
						JOptionPane.showMessageDialog(null, "大哥,主键是不能删除滴!");
						return;
					}else if(deleteIndex==-1){
						JOptionPane.showMessageDialog(null, "选都莫滴选,删啥子哩!");
						return;
					}
					model.fields.remove(deleteIndex);
					model.fireTableRowsDeleted(deleteIndex, deleteIndex);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes txtChineseName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtChineseName() {
		if (txtChineseName == null) {
			txtChineseName = new JTextField();
			txtChineseName.setBounds(new Rectangle(123, 292, 82, 23));
			txtChineseName.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
						btnAdd.doClick();
					}
				}
			});
		}
		return txtChineseName;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DbDesign thisClass = new DbDesign();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
				thisClass.txtColName.requestFocusInWindow();
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public DbDesign() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(605, 446);
		this.setContentPane(getJContentPane());
		this.setTitle("数据库设计");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				txtColName.requestFocus();
			}
		});
		
		int w = this.getSize().width; 
		int h =this.getSize().height; 
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		int x = (dim.width-w)/2; 
		int y = (dim.height-h)/2; 
		this.setLocation(x,y);
		
		txtColName.setText(MainFrame.txtPojoName.getText()+"_");

		txtLength.setText("20");
		
		//生成第一行
		Field field=new WebField(MainFrame.txtPojoName.getText()+"_id","int",4,false,"标识");
		model.addField(field);

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

}  //  @jve:decl-index=0:visual-constraint="10,10"
