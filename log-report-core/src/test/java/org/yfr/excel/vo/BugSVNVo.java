package org.yfr.excel.vo;

public class BugSVNVo extends SVNVo {
	private String sType = "";
	private String sId = "0";
	private String sContext = "";
	private String sHandler = "";
	
	public String getType() {
		return sType;
	}
	public void setType(String sType) {
		this.sType = sType;
	}
	public String getId() {
		return sId;
	}
	public void setId(String sId) {
		this.sId = sId;
	}
	public String getContext() {
		return sContext;
	}
	public void setContext(String sContext) {
		this.sContext = sContext;
	}
	public String getHandler() {
		return sHandler;
	}
	public void setHandler(String sHandler) {
		this.sHandler = sHandler;
	}
	
	public BugSVNVo() {}
	
	public BugSVNVo (String sType, String sId, String sContext, String sHandler) {
		this.sType 		= sType;
		this.sId		= sId;
		this.sContext	= sContext;
		this.sHandler	= sHandler;
	}
	
	public BugSVNVo copyOf() {
		BugSVNVo vo = new BugSVNVo();

		vo.setType(sType);
		vo.setId(sId);
		vo.setContext(sContext);
		vo.setHandler(sHandler);
		return vo;
	}
	
	public void copyFields(BugSVNVo v) {
		BugSVNVo vo = (BugSVNVo) v;

		sType = vo.getType();
		sId = vo.getId();
		sContext = vo.getContext();
		sHandler = vo.getHandler();
	}

	public String getVoDataString() {
		StringBuilder sb = new StringBuilder();
		sb.append(sType);
		sb.append(';');
		sb.append(sId);
		sb.append(';');
		sb.append(sContext);
		sb.append(';');
		sb.append(sHandler);
		sb.append(';');
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type=");
		sb.append(sType);
		sb.append(';');
		sb.append("Id=");
		sb.append(sId);
		sb.append(';');
		sb.append("Context=");
		sb.append(sContext);
		sb.append("Handler=");
		sb.append(sHandler);
		return sb.toString();
	}
	
	@Override
	public String getDefaultKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(sType);
		sb.append("-");
		sb.append(sId);
		return sb.toString();
	}
}
