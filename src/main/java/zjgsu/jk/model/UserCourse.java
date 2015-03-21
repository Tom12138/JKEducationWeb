/**
 * 
 */
package zjgsu.jk.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author zkj
 *
 */
@Entity
public class UserCourse extends AuditableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7017115946846638694L;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
}
