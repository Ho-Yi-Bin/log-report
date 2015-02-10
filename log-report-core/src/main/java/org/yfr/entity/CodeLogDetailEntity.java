
package org.yfr.entity;

import java.io.Serializable;

/**
 * code log detail entity .
 * 
 * <p>setup time <b>Nov 21, 2014 11:47:27 AM .</b></p>
 *
 * @author Vincent Huang
 */
public class CodeLogDetailEntity implements Serializable {

	private static final long serialVersionUID = 6818304975267279968L;

	/** author . */
	String author;

	/** date . */
	String date;

	/** log . */
	String log;

	/** changed . */
	String changed;

	/** revision number . */
	Integer revisionNumber;

	/** generate flag . */
	String generateFlag;

	/** module name . */
	String moduleName;

	/** tag name . */
	String tagName;

	/**
	 * getter of author .
	 *
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * getter of date .
	 *
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * getter of log .
	 *
	 * @return log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * getter of changed .
	 *
	 * @return changed
	 */
	public String getChanged() {
		return changed;
	}

	/**
	 * getter of revisionNumber .
	 *
	 * @return revisionNumber
	 */
	public Integer getRevisionNumber() {
		return revisionNumber;
	}

	/**
	 * getter of generateFlag .
	 *
	 * @return generateFlag
	 */
	public String getGenerateFlag() {
		return generateFlag;
	}

	/**
	 * getter of moduleName .
	 *
	 * @return moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * getter of tagName .
	 *
	 * @return tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * setter of author .
	 *
	 * @param author - the author <b>String</b> to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * setter of date .
	 *
	 * @param date - the date <b>String</b> to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * setter of log .
	 *
	 * @param log - the log <b>String</b> to set
	 */
	public void setLog(String log) {
		this.log = log;
	}

	/**
	 * setter of changed .
	 *
	 * @param changed - the changed <b>String</b> to set
	 */
	public void setChanged(String changed) {
		this.changed = changed;
	}

	/**
	 * setter of revisionNumber .
	 *
	 * @param revisionNumber - the revisionNumber <b>Integer</b> to set
	 */
	public void setRevisionNumber(Integer revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	/**
	 * setter of generateFlag .
	 *
	 * @param generateFlag - the generateFlag <b>String</b> to set
	 */
	public void setGenerateFlag(String generateFlag) {
		this.generateFlag = generateFlag;
	}

	/**
	 * setter of moduleName .
	 *
	 * @param moduleName - the moduleName <b>String</b> to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * setter of tagName .
	 *
	 * @param tagName - the tagName <b>String</b> to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeLogDetailEntity {\n\t");
		if (author != null) {
			builder.append("author=");
			builder.append(author);
			builder.append(", \n\t");
		}
		if (date != null) {
			builder.append("date=");
			builder.append(date);
			builder.append(", \n\t");
		}
		if (log != null) {
			builder.append("log=");
			builder.append(log);
			builder.append(", \n\t");
		}
		if (changed != null) {
			builder.append("changed=");
			builder.append(changed);
			builder.append(", \n\t");
		}
		if (revisionNumber != null) {
			builder.append("revisionNumber=");
			builder.append(revisionNumber);
			builder.append(", \n\t");
		}
		if (generateFlag != null) {
			builder.append("generateFlag=");
			builder.append(generateFlag);
			builder.append(", \n\t");
		}
		if (moduleName != null) {
			builder.append("moduleName=");
			builder.append(moduleName);
			builder.append(", \n\t");
		}
		if (tagName != null) {
			builder.append("tagName=");
			builder.append(tagName);
		}
		builder.append("\n}");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((changed == null) ? 0 : changed.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((generateFlag == null) ? 0 : generateFlag.hashCode());
		result = prime * result + ((log == null) ? 0 : log.hashCode());
		result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
		result = prime * result + ((revisionNumber == null) ? 0 : revisionNumber.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CodeLogDetailEntity)) {
			return false;
		}
		CodeLogDetailEntity other = (CodeLogDetailEntity) obj;
		if (author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!author.equals(other.author)) {
			return false;
		}
		if (changed == null) {
			if (other.changed != null) {
				return false;
			}
		} else if (!changed.equals(other.changed)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (generateFlag == null) {
			if (other.generateFlag != null) {
				return false;
			}
		} else if (!generateFlag.equals(other.generateFlag)) {
			return false;
		}
		if (log == null) {
			if (other.log != null) {
				return false;
			}
		} else if (!log.equals(other.log)) {
			return false;
		}
		if (moduleName == null) {
			if (other.moduleName != null) {
				return false;
			}
		} else if (!moduleName.equals(other.moduleName)) {
			return false;
		}
		if (revisionNumber == null) {
			if (other.revisionNumber != null) {
				return false;
			}
		} else if (!revisionNumber.equals(other.revisionNumber)) {
			return false;
		}
		if (tagName == null) {
			if (other.tagName != null) {
				return false;
			}
		} else if (!tagName.equals(other.tagName)) {
			return false;
		}
		return true;
	}

}