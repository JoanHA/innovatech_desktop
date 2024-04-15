/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.Button;

/**
 *
 * @author fofom
 */
public class mdlPQRS {

	private String id;
	private String user_id;
	private String param_type;
	private String body;
	private String param_state;
	private String created_at;
	private String updated_at;
	private String first_name;
	private String last_name;
	private String phone;
	private String email;
	private Button ver;

	public mdlPQRS(String id, String user_id, String param_type, String body, String param_state, String created_at, String updated_at, String first_name, String last_name, String phone, String email, Button ver) {
		this.id = id;
		this.user_id = user_id;
		this.param_type = param_type;
		this.body = body;
		this.param_state = param_state;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone = phone;
		this.email = email;
		this.ver = ver;
	}

	public Button getVer() {
		return ver;
	}

	public void setVer(Button ver) {
		this.ver = ver;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getParam_type() {
		return param_type;
	}

	public void setParam_type(String param_type) {
		this.param_type = param_type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getParam_state() {
		return param_state;
	}

	public void setParam_state(String param_state) {
		this.param_state = param_state;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public mdlPQRS() {

	}

}
