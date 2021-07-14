package com.mine;

import com.sun.xml.internal.ws.policy.PolicyException;
import com.sun.xml.internal.ws.policy.sourcemodel.PolicyModelUnmarshaller;
import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 程序入口
 * GUI界面
 * 文件选择，获取基本路径，调用相应的处理方法
 * @author
 *
 */
public class ButtonFrameNew extends JFrame{
	private static JFrame frame;

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame = new ButtonFrameNew();
				frame.setTitle("数据处理");
				frame.setLayout(new FlowLayout());
				frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	private String basePath = null;
	private JFileChooser fic;
	private ButtonPanel buttonPanel;
	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT = 400;

	public ButtonFrameNew(){
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setLocationByPlatform(true);

		Button b1 = new Button("按钮的");
		b1.setSize(30,40);
		TextField t1 = new TextField(60);
		Container container1 = new Container();
		container1.setLayout(new FlowLayout());
		container1.add(b1);
		container1.add(t1);

		Button b2 = new Button("突突突");
		b2.setSize(30,40);
		TextField t2 = new TextField(60);
		Container container2 = new Container();
		container2.setLayout(new FlowLayout());
		container2.add(b2);
		container2.add(t2);

		add(container1);
		add(container2);


		b1.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				t1.setText("填充数据=========222222222==========");
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});

//		t1.setSize(300,20);
//		b1.setVisible(true);

//		fic = new JFileChooser("请选择word模板文件");
//		buttonPanel.add(fic);
//		fic.addActionListener(choose);
//		FileChoose choose = new FileChoose();
//		buttonPanel = new ButtonPanel();
//		add(buttonPanel);
	}

	private class FileChoose implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fic.disable();
			String filePath = fic.getSelectedFile().getName();
			String suffix = filePath.substring(filePath.lastIndexOf("."));
			if(".docx".equals(suffix)){
				basePath =fic.getSelectedFile().getPath();
				if(basePath != null && !"".equals(basePath)){
					try {
						Long st = System.currentTimeMillis();
						MainDeal.getBasePath(basePath);
						Long ed = System.currentTimeMillis();
						System.out.println("用时： "+(ed - st) +"毫秒");
						JOptionPane.showMessageDialog(buttonPanel, "已完成，请关闭窗口", "警告",JOptionPane.WARNING_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(buttonPanel, "执行中发现异常", "警告",JOptionPane.WARNING_MESSAGE);
						ex.printStackTrace();
					}
				}
			}else {
				JOptionPane.showMessageDialog(buttonPanel, "该文件不是word模板文件，请重新选择后缀为.docx的文件", "警告",JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	class ButtonPanel extends JPanel{
		private static final int DEFAULT_WIDTH = 300;
		private static final int DEFAULT_HEIGHT = 200;

		@Override
		protected void paintComponent(Graphics g) {
			g.create();
			super.paintComponent(g);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		}
	}


}