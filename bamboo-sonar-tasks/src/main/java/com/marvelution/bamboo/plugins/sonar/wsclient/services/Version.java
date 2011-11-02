/*
 * Licensed to Marvelution under one or more contributor license 
 * agreements.  See the NOTICE file distributed with this work 
 * for additional information regarding copyright ownership.
 * Marvelution licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.marvelution.bamboo.plugins.sonar.wsclient.services;

import java.util.Date;

import org.sonar.wsclient.services.Model;


/**
 * Version {@link Model} implemenation
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class Version extends Model {

	private Integer sid;
	private boolean last;
	private Date date;
	private String name;
	
	/**
	 * Getter for sid
	 * 
	 * @return the sid
	 */
	public Integer getSid() {
		return sid;
	}
	
	/**
	 * Setter for sid
	 * 
	 * @param sid the sid to set
	 */
	public Version setSid(Integer sid) {
		this.sid = sid;
		return this;
	}
	
	/**
	 * Setter for sid
	 * 
	 * @param sid the sid to set
	 */
	public Version setSid(String sid) {
		this.sid = Integer.parseInt(sid);
		return this;
	}
	
	/**
	 * Getter for last
	 * 
	 * @return the last
	 */
	public boolean isLast() {
		return last;
	}
	
	/**
	 * Setter for last
	 * 
	 * @param last the last to set
	 */
	public Version setLast(boolean last) {
		this.last = last;
		return this;
	}
	
	/**
	 * Getter for date
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Setter for date
	 * 
	 * @param date the date to set
	 */
	public Version setDate(Date date) {
		this.date = date;
		return this;
	}
	
	/**
	 * Getter for name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for name
	 * 
	 * @param name the name to set
	 */
	public Version setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[sid=" + this.sid + ",name=" + this.name + "]";
	}

}
