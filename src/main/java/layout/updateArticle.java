package layout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import IT6020003.objects.*;
import IT6020003.process.*;

import javax.swing.JButton;
import javax.swing.JLabel;

public class updateArticle extends JPanel {
	private JTextField txtTitle;
	private JTextField txtCreateDate;
	private ArticleObject selectArticle;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public updateArticle(Article a) {		
		// Đặt kích thước và màu nền cho giao diện
	    setBounds(10, 111, 789, 590);
	    setBackground(new Color(245, 247, 255));
	    setLayout(null);
	    
	    // Tạo nhãn cho tiêu đề
	    JLabel lblTitle = new JLabel("Title");
	    lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblTitle.setBounds(10, 61, 100, 30);
	    add(lblTitle);
	    
	    // Tạo nhãn cho nội dung
	    JLabel lblContent = new JLabel("Content");
	    lblContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblContent.setBounds(10, 110, 100, 30);
	    add(lblContent);
	    
	    // Tạo nhãn cho tóm tắt
	    JLabel lblSummary = new JLabel("Summary");
	    lblSummary.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblSummary.setBounds(10, 284, 100, 30);
	    add(lblSummary);
	    
	    // Tạo nhãn cho ngày tạo
	    JLabel lblCreateDate = new JLabel("Create date");
	    lblCreateDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblCreateDate.setBounds(10, 459, 100, 30);
	    add(lblCreateDate);
	    
	    // Tạo nhãn thông tin
	    JLabel lblInfo = new JLabel("");
	    lblInfo.setBounds(170, 498, 387, 25);
	    add(lblInfo);
	    
	    // Tạo ô văn bản cho tiêu đề
	    txtTitle = new JTextField();
	    txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    txtTitle.setBounds(170, 64, 580, 25);
	    add(txtTitle);
	    txtTitle.setColumns(10);
	    
	    // Tạo ô văn bản cho nội dung
	    JTextArea txtContent = new JTextArea();
	    JScrollPane scrollPaneContent = new JScrollPane(txtContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPaneContent.setBounds(170, 115, 580, 150);
	    add(scrollPaneContent);
	    
	    // Tạo ô văn bản cho tóm tắt
	    JTextArea txtSummary = new JTextArea();
	    JScrollPane scrollPane = new JScrollPane(txtSummary, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBounds(170, 289, 580, 150);
	    add(scrollPane);
	    
	    // Tạo ô văn bản cho ngày tạo
	    txtCreateDate = new JTextField();
	    txtCreateDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    txtCreateDate.setColumns(10);
	    txtCreateDate.setBounds(170, 462, 580, 25);
	    add(txtCreateDate);
	    
	    // Tạo nút cập nhật
	    JButton btnUpdate = new JButton("Update");
	    btnUpdate.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            // Lấy dữ liệu từ các ô văn bản
	            String title = txtTitle.getText();
	            String content = txtContent.getText();
	            String summary = txtSummary.getText();
	            String create_date = txtCreateDate.getText();
	            
	            // Kiểm tra xem các trường có được điền đầy đủ hay không
	            if (!"".equalsIgnoreCase(title) && !"".equalsIgnoreCase(content) && !"".equalsIgnoreCase(summary) && !"".equalsIgnoreCase(create_date)) {
	                // Lấy bài viết được chọn
	                ArticleObject update_article = selectArticle;
	                
	                // Cập nhật thông tin của bài viết
	                update_article.setArticle_title(title);
	                update_article.setArticle_content(content);
	                update_article.setArticle_summary(summary);
	                update_article.setArticle_created_date(create_date);
	                
	                // Gọi phương thức updateArticle từ đối tượng Article
	                if (a.updateArticle(update_article)) {
	                    lblInfo.setText("Cập nhật thành công");
	                } else {
	                    lblInfo.setText("Cập nhật không thành công");
	                }
	            }
	            
	            // Xóa dữ liệu trong các ô văn bản sau khi cập nhật
	            txtTitle.setText("");
	            txtContent.setText("");
	            txtSummary.setText("");
	            txtCreateDate.setText("");
	        }
	    });
	    btnUpdate.setBounds(170, 537, 110, 30);
	    add(btnUpdate);
	    
	    // Tạo nhãn "Update Article"
	    JLabel lblUpdate = new JLabel("Update Article");
	    lblUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
	    lblUpdate.setBounds(170, 11, 126, 39);
	    add(lblUpdate);
	}

	// Phương thức nhận dữ liệu của hàng được chọn từ giao diện chính
	public void getSelectRow (ArticleObject items) {
	    selectArticle = items;
	}
}
