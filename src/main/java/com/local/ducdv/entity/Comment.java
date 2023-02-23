package com.local.ducdv.entity;

import java.util.Date;

public class Comment {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column comments.id
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column comments.post_id
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	private Integer postId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column comments.content
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column comments.date
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	private Date date;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column comments.id
	 * @return  the value of comments.id
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column comments.id
	 * @param id  the value for comments.id
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column comments.post_id
	 * @return  the value of comments.post_id
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public Integer getPostId() {
		return postId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column comments.post_id
	 * @param postId  the value for comments.post_id
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column comments.content
	 * @return  the value of comments.content
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column comments.content
	 * @param content  the value for comments.content
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column comments.date
	 * @return  the value of comments.date
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column comments.date
	 * @param date  the value for comments.date
	 * @mbg.generated  Thu Feb 23 15:44:42 ICT 2023
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}