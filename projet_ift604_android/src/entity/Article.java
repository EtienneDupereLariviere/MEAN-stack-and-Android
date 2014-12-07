package entity;

import java.io.Serializable;

public class Article implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    String created;
	String title;
	String content;
	User user;
	String _id;
	
	public Article() {
		super();
	}
	
	public Article(String _id, String content, String title, String created, User user)
	{
	    this._id = _id;
	    this.content = content;
	    this.title = title;
	    this.created = created;
	    this.user = user;
	}
	
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
