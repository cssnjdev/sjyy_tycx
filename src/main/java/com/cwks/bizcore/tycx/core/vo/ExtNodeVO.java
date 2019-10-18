package com.cwks.bizcore.tycx.core.vo;

/**
 * 
 *
 * @project 金税三期工程核心征管及应用总集成项目
 * @package com.css.sword.qs.vo.model
 * @file ExtTreeVO.java 创建时间:2015-5-12下午04:14:12
 * @title EXT树型结构VO
 * @description 描述（简要描述类的职责、实现方式、使用注意事项等）
 * @copyright Copyright (c) 2015 中国软件与技术服务股份有限公司
 * @company 中国软件与技术服务股份有限公司
 * @module 模块: 模块名称
 * @author   jinln
 * @reviewer 审核人
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 *
 */
public class ExtNodeVO {

    /**
     * @description 字段功能描述
     * @value value:serialVersionUID
     */
    private static final long serialVersionUID = 3691922184192886069L;
    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String text;
    
    private Boolean checked; 
    private String name;
    private Boolean isParent;
    private String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2015-5-12下午04:12:53
     *@return id
     *@author   作者
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public String getId() {
        return id;
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2015-5-12下午04:12:58
     *@param id 代码
     *@author   作者
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public void setId(String id) {
        this.id = id;
    }


	/**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2015-5-12下午04:13:08
     *@return 名称
     *@author   作者
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public String getText() {
        return text;
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2015-5-12下午04:13:11
     *@param text 名称
     *@author   作者
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public void setText(String text) {
        this.text = text;
    }

	public void setChecked(Boolean checked) {
		this.checked = checked;
		
	}

	public Boolean getChecked() {
		return checked;
	}

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("{id:'").append((this.id!=null)?this.id:"").append("',text:'").append((this.text!=null)?this.text:"").append("',checked:").append((this.checked!=null)?this.checked:"").append(",pid:'").append((this.pid!=null)?this.pid:"").append("}");
        return buffer.toString();
    }

}
