package org.yfr.excel.vo;

public class OtherSVNVo extends SVNVo{
	private String sType = "";
	private String sContext = "";
	
	public String getType() {
		return sType;
	}
	public void setType(String sType) {
		this.sType = sType;
	}
	
	public String getContext() {
		return sContext;
	}
	public void setContext(String sContext) {
		this.sContext = sContext;
	}
	
	public OtherSVNVo() {}
	
	public OtherSVNVo (String sType, String sContext) {
		this.sType 		= sType;
		this.sContext	= sContext;
	}
	
	public OtherSVNVo copyOf() {
		OtherSVNVo vo = new OtherSVNVo();

		vo.setType(sType);
		vo.setContext(sContext);
		return vo;
	}
	
	public void copyFields(OtherSVNVo v) {
		OtherSVNVo vo = (OtherSVNVo) v;

		sType = vo.getType();
		sContext = vo.getContext();
	}

	public String getVoDataString() {
		StringBuilder sb = new StringBuilder();
		sb.append(sType);
		sb.append(';');
		sb.append(sContext);
		sb.append(';');
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type=");
		sb.append(sType);
		sb.append(';');
		sb.append("Context=");
		sb.append(sContext);

		return sb.toString();
	}
	
	@Override
	public String getDefaultKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(sType);
		return sb.toString();
	}
}
