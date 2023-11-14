package IT6020003.process;

import java.util.*;
import java.sql.*;
import IT6020003.ConnectionPool;
import IT6020003.ConnectionPoolImpl;
import IT6020003.objects.*;

public class Article {
	// kết nối để làm việc vs csdl
	private Connection con;

	// bộ quản lý kết nối của riêng section
	private ConnectionPool cp;

	public Article() {
		// xác định bộ quản lý kết nối
		this.cp = new ConnectionPoolImpl();

		// Xin kết nối để làm việc
		try {
			this.con = this.cp.getConnection("Article");

			// kiểm tra chế độ thực thi của kết nối
			if (this.con.getAutoCommit()) {
				this.con.setAutoCommit(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Phương thức trả về danh sách tất cả các đối tượng ArticleObject từ cơ sở dữ liệu
	public ArrayList<ArticleObject> getAllArticleObjects(ArticleObject similar) {
	    // Khởi tạo một ArrayList để lưu trữ các đối tượng ArticleObject
	    ArrayList<ArticleObject> items = new ArrayList<>();
	    ArticleObject item;

	    // Xây dựng câu truy vấn SQL để lấy tất cả dữ liệu từ bảng tblarticle
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT * FROM tblarticle");

	    try {
	        // Tạo đối tượng PreparedStatement để thực hiện câu truy vấn
	        PreparedStatement pre = this.con.prepareStatement(sql.toString());

	        // Thực hiện truy vấn và lấy kết quả
	        ResultSet rs = pre.executeQuery();
	        
	        // Kiểm tra xem ResultSet có giá trị không
	        if (rs != null) {
	            // Duyệt qua tất cả các dòng kết quả
	            while (rs.next()) {
	                // Tạo đối tượng ArticleObject để lưu trữ thông tin từ ResultSet
	                item = new ArticleObject();
	                
	                // Đọc dữ liệu từ ResultSet và set giá trị cho đối tượng ArticleObject
	                item.setArticle_id(rs.getInt("article_id"));
	                item.setArticle_title(rs.getString("article_title"));
	                item.setArticle_summary(rs.getString("article_summary"));
	                item.setArticle_content(rs.getString("article_content"));
	                item.setArticle_created_date(rs.getString("article_created_date"));
	                
	                // Thêm đối tượng ArticleObject vào danh sách
	                items.add(item);
	            }
	        }

	    } catch (SQLException e) {
	        // Xử lý ngoại lệ và in thông báo lỗi
	        e.printStackTrace();

	        try {
	            // Rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    // Trả về danh sách các đối tượng ArticleObject
	    return items;
	}

	// Phương thức trả về danh sách các đối tượng ArticleObject từ cơ sở dữ liệu với số lượng giới hạn
	public ArrayList<ArticleObject> getArticleObjects(ArticleObject similar, byte total) {
	    // Khởi tạo một ArrayList để lưu trữ các đối tượng ArticleObject
	    ArrayList<ArticleObject> items = new ArrayList<>();
	    ArticleObject item;

	    // Xây dựng câu truy vấn SQL để lấy tất cả dữ liệu từ bảng tblarticle với số lượng giới hạn
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT * FROM tblarticle ");
	    sql.append("ORDER BY article_title ASC ");
	    sql.append("LIMIT ?");

	    try {
	        // Tạo đối tượng PreparedStatement để thực hiện câu truy vấn
	        PreparedStatement pre = this.con.prepareStatement(sql.toString());
	        
	        // Truyền giá trị cho tham số, tổng số bản ghi cần lấy
	        pre.setByte(1, total);

	        // Thực hiện truy vấn và lấy kết quả
	        ResultSet rs = pre.executeQuery();

	        // Kiểm tra xem ResultSet có giá trị không
	        if (rs != null) {
	            // Duyệt qua tất cả các dòng kết quả
	            while (rs.next()) {
	                // Tạo đối tượng ArticleObject để lưu trữ thông tin từ ResultSet
	                item = new ArticleObject();
	                
	                // Đọc dữ liệu từ ResultSet và set giá trị cho đối tượng ArticleObject
	                item.setArticle_id(rs.getInt("article_id"));
	                item.setArticle_title(rs.getString("article_title"));
	                item.setArticle_summary(rs.getString("article_summary"));
	                item.setArticle_content(rs.getString("article_content"));
	                item.setArticle_created_date(rs.getString("article_created_date"));

	                // Đưa đối tượng ArticleObject vào tập hợp
	                items.add(item);
	            }
	        }

	    } catch (SQLException e) {
	        // Xử lý ngoại lệ và in thông báo lỗi
	        e.printStackTrace();

	        try {
	            // Rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    // Trả về danh sách các đối tượng ArticleObject
	    return items;
	}

	// Phương thức trả về một danh sách các đối tượng ArticleObject dựa trên ID từ cơ sở dữ liệu
	public ArrayList<ArticleObject> getArticleObjectsById(ArticleObject similar, int id) {
	    // Khởi tạo một ArrayList để lưu trữ các đối tượng ArticleObject
	    ArrayList<ArticleObject> items = new ArrayList<>();
	    ArticleObject item;

	    // Xây dựng câu truy vấn SQL để lấy dữ liệu từ bảng tblarticle với điều kiện là article_id = ?
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT * FROM tblarticle WHERE article_id = ?");

	    try {
	        // Tạo đối tượng PreparedStatement để thực hiện câu truy vấn
	        PreparedStatement pre = this.con.prepareStatement(sql.toString());
	        
	        // Truyền giá trị cho tham số, ID của bản ghi cần lấy
	        pre.setInt(1, id);

	        // Thực hiện truy vấn và lấy kết quả
	        ResultSet rs = pre.executeQuery();

	        // Kiểm tra xem ResultSet có giá trị không
	        if (rs != null) {
	            // Duyệt qua tất cả các dòng kết quả
	            while (rs.next()) {
	                // Tạo đối tượng ArticleObject để lưu trữ thông tin từ ResultSet
	                item = new ArticleObject();
	                
	                // Đọc dữ liệu từ ResultSet và set giá trị cho đối tượng ArticleObject
	                item.setArticle_id(rs.getInt("article_id"));
	                item.setArticle_title(rs.getString("article_title"));
	                item.setArticle_summary(rs.getString("article_summary"));
	                item.setArticle_content(rs.getString("article_content"));
	                item.setArticle_created_date(rs.getString("article_created_date"));

	                // Đưa đối tượng ArticleObject vào tập hợp
	                items.add(item);
	            }
	        }

	    } catch (SQLException e) {
	        // Xử lý ngoại lệ và in thông báo lỗi
	        e.printStackTrace();

	        try {
	            // Rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    // Trả về danh sách các đối tượng ArticleObject
	    return items;
	}

	// Phương thức thêm một bài viết mới vào cơ sở dữ liệu
	public boolean addArticle(ArticleObject item) {
	    // Xây dựng câu truy vấn SQL để chèn dữ liệu vào bảng tblarticle
	    StringBuilder sql = new StringBuilder();
	    sql.append("INSERT INTO tblarticle(");
	    sql.append("article_title, article_summary, article_content, ");
	    sql.append("article_created_date, article_last_modified, article_image, ");
	    sql.append("article_category_id, article_section_id, article_visited, ");
	    sql.append("article_author_name, article_enable, article_url_link, ");
	    sql.append("article_tag, article_title_en, article_summary_en, ");
	    sql.append("article_content_en, article_tag_en, article_fee, ");
	    sql.append("article_isfee, article_delete, article_deleted_date, ");
	    sql.append("article_restored_date, article_modified_author_name, article_author_permission, ");
	    sql.append("article_source, article_language, article_focus, ");
	    sql.append("article_type, article_forhome) ");
	    sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

	    try {
	        // Tạo đối tượng PreparedStatement để thực hiện câu truy vấn
	        PreparedStatement pre = this.con.prepareStatement(sql.toString());
	        
	        // Truyền giá trị cho các tham số trong câu truy vấn từ đối tượng ArticleObject
	        pre.setString(1, item.getArticle_title());
	        pre.setString(2, item.getArticle_summary());
	        pre.setString(3, item.getArticle_content());
	        pre.setString(4, item.getArticle_created_date());
	        pre.setString(5, item.getArticle_last_modified());
	        pre.setString(6, item.getArticle_image());
	        pre.setShort(7, item.getArticle_category_id());
	        pre.setShort(8, item.getArticle_section_id());
	        pre.setShort(9, item.getArticle_visited());
	        pre.setString(10, item.getArticle_author_name());
	        pre.setShort(11, item.getArticle_enable());
	        pre.setString(12, item.getArticle_url_link());
	        pre.setString(13, item.getArticle_tag());
	        pre.setString(14, item.getArticle_title_en());
	        pre.setString(15, item.getArticle_summary_en());
	        pre.setString(16, item.getArticle_content_en());
	        pre.setString(17, item.getArticle_tag_en());
	        pre.setInt(18, item.getArticle_fee());
	        pre.setShort(19, item.getArticle_isfee());
	        pre.setShort(20, item.getArticle_delete());
	        pre.setString(21, item.getArticle_deleted_date());
	        pre.setString(22, item.getArticle_restored_date());
	        pre.setString(23, item.getArticle_modified_author_name());
	        pre.setShort(24, item.getArticle_author_permission());
	        pre.setString(25, item.getArticle_source());
	        pre.setShort(26, item.getArticle_language());
	        pre.setShort(27, item.getArticle_focus());
	        pre.setShort(28, item.getArticle_type());
	        pre.setShort(29, item.getArticle_forhome());

	        // Thực hiện câu truy vấn chèn dữ liệu
	        int result = pre.executeUpdate();
	        
	        // Kiểm tra kết quả thực hiện câu truy vấn
	        if (result == 0) {
	            // Nếu không thành công, rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	            return false;
	        }
	        
	        // Ghi nhận thực thi sau cùng
	        this.con.commit();
	        return true;
	    } catch (SQLException e) {
	        // Xử lý ngoại lệ và in thông báo lỗi
	        e.printStackTrace();

	        try {
	            // Rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    // Trả về false nếu có lỗi xảy ra
	    return false;
	}

	// Phương thức cập nhật thông tin một bài viết trong cơ sở dữ liệu
	public boolean updateArticle(ArticleObject item) {
	    // Xây dựng câu truy vấn SQL để cập nhật dữ liệu trong bảng tblarticle
	    StringBuilder sql = new StringBuilder();
	    sql.append("UPDATE tblarticle SET ");
	    sql.append("article_title = ?, ");
	    sql.append("article_summary = ?, ");
	    sql.append("article_content = ?, ");
	    sql.append("article_created_date = ?, ");
	    sql.append("article_last_modified = ?, ");
	    sql.append("article_image = ?, ");
	    sql.append("article_category_id = ?, ");
	    sql.append("article_section_id = ?, ");
	    sql.append("article_visited = ?, ");
	    sql.append("article_author_name = ?, ");
	    sql.append("article_enable = ?, ");
	    sql.append("article_url_link = ?, ");
	    sql.append("article_tag = ?, ");
	    sql.append("article_title_en = ?, ");
	    sql.append("article_summary_en = ?, ");
	    sql.append("article_content_en = ?, ");
	    sql.append("article_tag_en = ?, ");
	    sql.append("article_fee = ?, ");
	    sql.append("article_isfee = ?, ");
	    sql.append("article_delete = ?, ");
	    sql.append("article_deleted_date = ?, ");
	    sql.append("article_restored_date = ?, ");
	    sql.append("article_modified_author_name = ?, ");
	    sql.append("article_author_permission = ?, ");
	    sql.append("article_source = ?, ");
	    sql.append("article_language = ?, ");
	    sql.append("article_focus = ?, ");
	    sql.append("article_type = ?, ");
	    sql.append("article_forhome = ? ");
	    sql.append("WHERE article_id = ?");

	    try {
	        // Tạo đối tượng PreparedStatement để thực hiện câu truy vấn
	        PreparedStatement pre = this.con.prepareStatement(sql.toString());
	        
	        // Truyền giá trị cho các tham số trong câu truy vấn từ đối tượng ArticleObject
	        pre.setString(1, item.getArticle_title());
	        pre.setString(2, item.getArticle_summary());
	        pre.setString(3, item.getArticle_content());
	        pre.setString(4, item.getArticle_created_date());
	        pre.setString(5, item.getArticle_last_modified());
	        pre.setString(6, item.getArticle_image());
	        pre.setShort(7, item.getArticle_category_id());
	        pre.setShort(8, item.getArticle_section_id());
	        pre.setShort(9, item.getArticle_visited());
	        pre.setString(10, item.getArticle_author_name());
	        pre.setShort(11, item.getArticle_enable());
	        pre.setString(12, item.getArticle_url_link());
	        pre.setString(13, item.getArticle_tag());
	        pre.setString(14, item.getArticle_title_en());
	        pre.setString(15, item.getArticle_summary_en());
	        pre.setString(16, item.getArticle_content_en());
	        pre.setString(17, item.getArticle_tag_en());
	        pre.setInt(18, item.getArticle_fee());
	        pre.setShort(19, item.getArticle_isfee());
	        pre.setShort(20, item.getArticle_delete());
	        pre.setString(21, item.getArticle_deleted_date());
	        pre.setString(22, item.getArticle_restored_date());
	        pre.setString(23, item.getArticle_modified_author_name());
	        pre.setShort(24, item.getArticle_author_permission());
	        pre.setString(25, item.getArticle_source());
	        pre.setShort(26, item.getArticle_language());
	        pre.setShort(27, item.getArticle_focus());
	        pre.setShort(28, item.getArticle_type());
	        pre.setShort(29, item.getArticle_forhome());
	        
	        // Đặt giá trị cho tham số WHERE (article_id)
	        pre.setInt(30, item.getArticle_id());

	        // Thực hiện câu truy vấn cập nhật dữ liệu
	        int result = pre.executeUpdate();
	        
	        // Kiểm tra kết quả thực hiện câu truy vấn
	        if (result == 0) {
	            // Nếu không thành công, rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	            return false;
	        }

	        // Ghi nhận thực thi sau cùng
	        this.con.commit();
	        return true;

	    } catch (SQLException e) {
	        // Xử lý ngoại lệ và in thông báo lỗi
	        e.printStackTrace();

	        try {
	            // Rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    // Trả về false nếu có lỗi xảy ra
	    return false;
	}

	// Phương thức xóa một bài viết từ cơ sở dữ liệu theo ID
	public boolean deleteArticleById(ArticleObject item) {
	    // Câu truy vấn SQL để xóa bản ghi từ bảng tblarticle dựa trên article_id
	    String sql = "DELETE FROM tblarticle WHERE article_id = ?";

	    try {
	        // Tạo đối tượng PreparedStatement để thực hiện câu truy vấn
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        
	        // Truyền giá trị cho tham số trong câu truy vấn từ đối tượng ArticleObject
	        pre.setInt(1, item.getArticle_id());

	        // Thực hiện câu truy vấn xóa bản ghi
	        int result = pre.executeUpdate();
	        
	        // Kiểm tra kết quả thực hiện câu truy vấn
	        if (result == 0) {
	            // Nếu không thành công, rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	            return false;
	        }

	        // Ghi nhận thực thi sau cùng
	        this.con.commit();
	        return true;
	    } catch (SQLException e) {
	        // Xử lý ngoại lệ và in thông báo lỗi
	        e.printStackTrace();

	        try {
	            // Rollback lại trạng thái trước khi xảy ra lỗi
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    // Trả về false nếu có lỗi xảy ra
	    return false;
	}


	public static void main(String[] args) {
		// tạo đối tượng làm việc với Article
		Article a = new Article();

		// tạo đối tượng chuyên mục mới
		ArticleObject nart = new ArticleObject();
		nart.setArticle_title("Article_title - new");
		nart.setArticle_author_name("new");

		if (!a.addArticle(nart)) {
			System.out.println("--------Không thành công");
		}

		ArrayList<ArticleObject> items = a.getAllArticleObjects(null);

		// In ra màn hình
		items.forEach(item -> {
			System.out.println(item);
		});

		// Chọn một phần tử để cập nhật

		// Cập nhật thông tin
//	    if (a.deleteArticleById(221)) {
//	        System.out.println("Cập nhật thành công");
//	    } else {
//	        System.out.println("Cập nhật không thành công");
//	    }
	}
}
